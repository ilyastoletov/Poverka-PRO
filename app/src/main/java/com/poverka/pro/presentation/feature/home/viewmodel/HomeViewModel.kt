package com.poverka.pro.presentation.feature.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.poverka.domain.feature.checkup.model.Checkup
import com.poverka.domain.util.Mock
import com.poverka.pro.presentation.feature.base.BaseViewModel
import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val snackbarHolder: SnackbarHolder
) : BaseViewModel<HomeContract.Event, HomeContract.State, Nothing>() {

    private val _enableVerificationToolsButton = MutableStateFlow(false)
    val enableVerificationToolsButton = _enableVerificationToolsButton.asStateFlow()

    private val _enableEnvironmentDataButton = MutableStateFlow(false)
    val enableEnvironmentDataButton = _enableEnvironmentDataButton.asStateFlow()

    private val _checkups = MutableStateFlow<List<Checkup>>(emptyList())
    val checkups = _checkups.asStateFlow()

    override fun setInitialState(): HomeContract.State = HomeContract.State.Loading

    init {
        loadIfExtraMenuItemsEnabled()
        loadCheckupsList()
    }

    override fun handleEvent(event: HomeContract.Event) {
        if (event is HomeContract.Event.RefreshData) {
            loadCheckupsList()
        }
    }

    private fun loadIfExtraMenuItemsEnabled() {
        viewModelScope.launch {
            delay(1500)
            _enableEnvironmentDataButton.emit(true)
            _enableVerificationToolsButton.emit(true)
        }
    }

    private fun loadCheckupsList() {
        viewModelScope.launch {
            setState(HomeContract.State.Loading)
            delay(1500)
            _checkups.emit(Mock.demoCheckups)
            setState(HomeContract.State.Loaded)
        }
    }

}