package com.upstox.updatedHoldingsAssignment

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.upstox.updatedHoldingsAssignment.persentation.main_screen.MainScreen
import com.upstox.updatedHoldingsAssignment.ui.theme.UpdatedHoldingsAssignmentTheme
import com.upstox.updatedHoldingsAssignment.utilities.CONNECTIVITY_INTENT_ACTION
import com.upstox.updatedHoldingsAssignment.utilities.networkChangeReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         *  Registering the networkChangeReceiver.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                networkChangeReceiver,
                IntentFilter().apply {
                    addAction(CONNECTIVITY_INTENT_ACTION)
                },
                Context.RECEIVER_EXPORTED
            )
        } else {
            registerReceiver(
                networkChangeReceiver,
                IntentFilter().apply {
                    addAction(CONNECTIVITY_INTENT_ACTION)
                }
            )
        }

        setContent {
            UpdatedHoldingsAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(mainScreenViewModel = hiltViewModel())
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkChangeReceiver)
    }
}