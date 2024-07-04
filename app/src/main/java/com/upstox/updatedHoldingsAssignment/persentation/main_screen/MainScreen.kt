package com.upstox.updatedHoldingsAssignment.persentation.main_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.upstox.updatedHoldingsAssignment.AppClass
import com.upstox.updatedHoldingsAssignment.R
import com.upstox.updatedHoldingsAssignment.domain.model.HoldingInfo
import com.upstox.updatedHoldingsAssignment.ui.theme.LossRed
import com.upstox.updatedHoldingsAssignment.ui.theme.Poppins
import com.upstox.updatedHoldingsAssignment.ui.theme.UpdatedHoldingsAssignmentTheme
import com.upstox.updatedHoldingsAssignment.utilities.AppTopBar
import com.upstox.updatedHoldingsAssignment.utilities.ConnectionState
import com.upstox.updatedHoldingsAssignment.utilities.HoldingListItem
import com.upstox.updatedHoldingsAssignment.utilities.HoldingScreenBottomBar
import com.upstox.updatedHoldingsAssignment.utilities.calculateCurrentValue
import com.upstox.updatedHoldingsAssignment.utilities.calculatePercentage
import com.upstox.updatedHoldingsAssignment.utilities.calculateTodayProfitAndLoss
import com.upstox.updatedHoldingsAssignment.utilities.calculateTotalInvestment
import com.upstox.updatedHoldingsAssignment.utilities.getCurrentConnectivityState

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var holdingInfoList by remember { mutableStateOf(listOf<HoldingInfo>()) }
    var connectionState by remember { mutableStateOf(getCurrentConnectivityState()) }
    
    LaunchedEffect(key1 = Unit) {
        /**
         *  Observer of network connectivity state to trigger retry when internet
         *  connection is restored.
         */
        AppClass.connectivityState.observe(lifecycleOwner) {
            connectionState = it
            if (mainScreenViewModel.shouldRetry(it)) {
                mainScreenViewModel.getHoldingDataFromRemote()
            }
        }

        /**
         *  Observer of holding data from DB.
         */
        mainScreenViewModel.holdings.observe(lifecycleOwner) {
            holdingInfoList = it

            val currentValue = calculateCurrentValue(it)
            val totalInvestment = calculateTotalInvestment(it)
            val totalProfitAndLoss = currentValue - totalInvestment

            mainScreenViewModel.state = mainScreenViewModel.state.copy(
                currentValue = currentValue,
                totalInvestment = totalInvestment,
                todayProfitAndLoss = calculateTodayProfitAndLoss(it),
                totalProfitAndLoss = totalProfitAndLoss,
                profitAndLossPercentage = calculatePercentage(totalProfitAndLoss, totalInvestment)
            )
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                headingText = stringResource(id = R.string.portfolio),
                searchIconClicked = {}
            )
        },
        bottomBar = {
            mainScreenViewModel.state.let {
                HoldingScreenBottomBar(
                    currentValue = it.currentValue,
                    totalInvestment = it.totalInvestment,
                    todayProfitAndLoss = it.todayProfitAndLoss,
                    totalProfitAndLoss = it.totalProfitAndLoss,
                    profitAndLossPercentage = it.profitAndLossPercentage
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            /**
             *  "HOLDINGS" heading text.
             */
            Text(
                modifier = Modifier
                    .padding(all = UpdatedHoldingsAssignmentTheme.dimens.dp16)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.holdings),
                style = TextStyle(
                    fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp16,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins,
                    textAlign = TextAlign.Center
                )
            )

            /**
             *  "No internet" message will be displayed when user's device is not
             *  connected to internet.
             */
            if (connectionState is ConnectionState.Unavailable) {
                Text(
                    modifier = Modifier
                        .padding(
                            start = UpdatedHoldingsAssignmentTheme.dimens.dp16,
                            end = UpdatedHoldingsAssignmentTheme.dimens.dp16,
                            bottom = UpdatedHoldingsAssignmentTheme.dimens.dp16
                        )
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.no_internet_message),
                    style = TextStyle(
                        fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp16,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Poppins,
                        textAlign = TextAlign.Center,
                        color = LossRed
                    )
                )
            }

            /**
             *  If holding list is empty, a circular loader will be displayed till
             *  the API is being called.
             */
            if (holdingInfoList.isEmpty() && mainScreenViewModel.state.loading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(UpdatedHoldingsAssignmentTheme.dimens.dp22)
                    )
                }
            } else {
                /**
                 *  List of all the holdings.
                 */
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = UpdatedHoldingsAssignmentTheme.dimens.dp16),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items = holdingInfoList) { item ->
                        HoldingListItem(
                            modifier = Modifier.fillMaxWidth(),
                            holdingInfo = item,
                            showBottomBorder = item != holdingInfoList.last()
                        )
                    }
                }
            }
        }
    }
}