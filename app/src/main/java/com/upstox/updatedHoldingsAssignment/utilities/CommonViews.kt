package com.upstox.updatedHoldingsAssignment.utilities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.upstox.updatedHoldingsAssignment.R
import com.upstox.updatedHoldingsAssignment.domain.model.HoldingInfo
import com.upstox.updatedHoldingsAssignment.ui.theme.Geyser
import com.upstox.updatedHoldingsAssignment.ui.theme.LossRed
import com.upstox.updatedHoldingsAssignment.ui.theme.OsloGray
import com.upstox.updatedHoldingsAssignment.ui.theme.Poppins
import com.upstox.updatedHoldingsAssignment.ui.theme.ProfitGreen
import com.upstox.updatedHoldingsAssignment.ui.theme.Roboto
import com.upstox.updatedHoldingsAssignment.ui.theme.UpdatedHoldingsAssignmentTheme

@Composable
fun HoldingListItem(
    modifier: Modifier = Modifier,
    holdingInfo: HoldingInfo,
    showBottomBorder: Boolean
) {
    Column {
        Row(
            modifier = modifier.then(
                Modifier.padding(top = UpdatedHoldingsAssignmentTheme.dimens.dp16)
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            holdingInfo.symbol?.let {
                Text(
                    text = holdingInfo.symbol,
                    style = TextStyle(
                        fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp20,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Poppins
                    )
                )
            }

            holdingInfo.ltp?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.padding(top = UpdatedHoldingsAssignmentTheme.dimens.dp20),
                        text = stringResource(id = R.string.ltp) + " ",
                        style = TextStyle(
                            fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp12,
                            color = Color.Black.copy(alpha = 0.6f),
                            fontFamily = Roboto
                        )
                    )

                    Text(
                        modifier = Modifier.padding(top = UpdatedHoldingsAssignmentTheme.dimens.dp20),
                        text = holdingInfo.ltp.displayPrice(),
                        style = TextStyle(
                            fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp16,
                            fontFamily = Roboto
                        )
                    )
                }
            }
        }

        Row(
            modifier = modifier.then(
                Modifier.padding(bottom = UpdatedHoldingsAssignmentTheme.dimens.dp16)
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            holdingInfo.quantity?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.padding(top = UpdatedHoldingsAssignmentTheme.dimens.dp20),
                        text = stringResource(id = R.string.net_qty) + " ",
                        style = TextStyle(
                            fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp12,
                            color = Color.Black.copy(alpha = 0.6f),
                            fontFamily = Roboto
                        )
                    )

                    Text(
                        modifier = Modifier.padding(top = UpdatedHoldingsAssignmentTheme.dimens.dp20),
                        text = holdingInfo.quantity.toString(),
                        style = TextStyle(
                            fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp16,
                            fontFamily = Roboto
                        )
                    )
                }
            }

            holdingInfo.netProfitOrLoss?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.padding(top = UpdatedHoldingsAssignmentTheme.dimens.dp20),
                        text = stringResource(id = R.string.p_and_l) + " ",
                        style = TextStyle(
                            fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp12,
                            color = Color.Black.copy(alpha = 0.6f),
                            fontFamily = Roboto
                        )
                    )

                    Text(
                        modifier = Modifier.padding(top = UpdatedHoldingsAssignmentTheme.dimens.dp20),
                        text = holdingInfo.netProfitOrLoss.displayPrice(),
                        style = TextStyle(
                            fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp16,
                            fontFamily = Roboto,
                            color = if (holdingInfo.netProfitOrLoss.toDouble() > 0) ProfitGreen else LossRed
                        )
                    )
                }
            }
        }

        if (showBottomBorder) {
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun AppTopBar(
    headingText: String,
    searchIconClicked: () -> Unit
) {
    Row(
        Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                horizontal = UpdatedHoldingsAssignmentTheme.dimens.dp16,
                vertical = UpdatedHoldingsAssignmentTheme.dimens.dp8
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = ContentDescription.USER_PROFILE_ICON
        )

        Text(
            modifier = Modifier.padding(start = UpdatedHoldingsAssignmentTheme.dimens.dp16),
            text = headingText,
            style = TextStyle(
                fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp20,
                fontFamily = Poppins,
                color = MaterialTheme.colorScheme.background
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            modifier = Modifier.padding(end = UpdatedHoldingsAssignmentTheme.dimens.dp8),
            painter = painterResource(id = R.drawable.ic_up_down_arrow),
            tint = MaterialTheme.colorScheme.background,
            contentDescription = ContentDescription.UP_DOWN_ARROW_ICON
        )

        VerticalDivider(modifier = Modifier.height(UpdatedHoldingsAssignmentTheme.dimens.dp22))

        IconButton(onClick = searchIconClicked) {
            Icon(
                modifier = Modifier.size(UpdatedHoldingsAssignmentTheme.dimens.dp22),
                painter = painterResource(id = R.drawable.ic_search),
                tint = MaterialTheme.colorScheme.background,
                contentDescription = ContentDescription.SEARCH_ICON
            )
        }
    }
}

@Composable
fun HoldingScreenBottomBar(
    currentValue: Double,
    totalInvestment: Double,
    todayProfitAndLoss: Double,
    totalProfitAndLoss: Double,
    profitAndLossPercentage: Double
) {
    val borderShape = RoundedCornerShape(
        topStart = UpdatedHoldingsAssignmentTheme.dimens.dp16,
        topEnd = UpdatedHoldingsAssignmentTheme.dimens.dp16
    )

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .clip(borderShape)
            .fillMaxWidth()
            .border(
                width = UpdatedHoldingsAssignmentTheme.dimens.dp1,
                color = Geyser,
                shape = borderShape
            )
            .padding(horizontal = UpdatedHoldingsAssignmentTheme.dimens.dp16)
    ) {
        AnimatedVisibility(visible = expanded) {
            Column {
                HoldingScreenBottomBarRowItem(
                    modifier = Modifier.padding(top = UpdatedHoldingsAssignmentTheme.dimens.dp16),
                    keyText = stringResource(id = R.string.current_value),
                    valueText = currentValue.displayPrice()
                )

                HoldingScreenBottomBarRowItem(
                    modifier = Modifier.padding(top = UpdatedHoldingsAssignmentTheme.dimens.dp16),
                    keyText = stringResource(id = R.string.total_investment),
                    valueText = totalInvestment.displayPrice()
                )

                HoldingScreenBottomBarRowItem(
                    modifier = Modifier.padding(top = UpdatedHoldingsAssignmentTheme.dimens.dp16),
                    keyText = stringResource(id = R.string.today_profit_and_loss),
                    valueText = todayProfitAndLoss.displayPrice(),
                    valueTextColor = if (todayProfitAndLoss > 0) ProfitGreen else LossRed
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = UpdatedHoldingsAssignmentTheme.dimens.dp8))
            }
        }

        HoldingScreenBottomBarRowItem(
            keyText = stringResource(id = R.string.profit_and_loss),
            valueText = "${totalProfitAndLoss.displayPrice()} (${profitAndLossPercentage.round()}%)",
            valueTextColor = if (totalProfitAndLoss > 0) ProfitGreen else LossRed,
            additionalComposable = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        modifier = Modifier.rotate(if (expanded) 0f else 180f),
                        painter = painterResource(id = R.drawable.ic_down_arrow),
                        tint = OsloGray,
                        contentDescription = ContentDescription.EXPAND_ARROW_ICON
                    )
                }
            }
        )
    }
}

@Composable
fun HoldingScreenBottomBarRowItem(
    modifier: Modifier = Modifier,
    keyText: String,
    valueText: String,
    valueTextColor: Color? = null,
    additionalComposable: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = keyText,
                style = TextStyle(
                    fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp16,
                    fontFamily = Roboto,
                    color = OsloGray
                )
            )

            additionalComposable?.invoke()
        }

        Text(
            text = valueText,
            style = TextStyle(
                fontSize = UpdatedHoldingsAssignmentTheme.fontSizes.sp16,
                fontFamily = Roboto,
                color = valueTextColor ?: MaterialTheme.colorScheme.onBackground
            )
        )
    }
}