package com.upstox.updatedHoldingsAssignment.persentation.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.upstox.updatedHoldingsAssignment.domain.repository.HoldingRepository
import com.upstox.updatedHoldingsAssignment.utilities.ConnectionState
import com.upstox.updatedHoldingsAssignment.utilities.Resource
import com.upstox.updatedHoldingsAssignment.utilities.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val holdingRepository: HoldingRepository
) : ViewModel() {
    var state by mutableStateOf(MainScreenState())

    /**
     *  Person data live data from local DB.
     */
    val holdings = holdingRepository.getHoldingDataFromLocal().asLiveData()

    init {
        getHoldingDataFromRemote()
    }

    /**
     *  To fetch data from remote server.
     */
    fun getHoldingDataFromRemote() {
        viewModelScope.launch {
            holdingRepository
                .getHoldingDataFromRemote()
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            state = state.copy(loading = result.isLoading)
                        }
                        is Resource.Success -> {
                            state = state.copy(dataSyncedFromApi = true)
                        }
                        is Resource.Error -> {
                            result.message?.let {
                                showToast(result.message)
                            }
                        }
                    }
                }
        }
    }

    /**
     *  To decide if API should be re-called or not, when network
     *  connection is restored.
     */
    fun shouldRetry(connectionState: ConnectionState): Boolean {
        return connectionState == ConnectionState.Available &&
                !state.dataSyncedFromApi &&
                !state.loading
    }
}