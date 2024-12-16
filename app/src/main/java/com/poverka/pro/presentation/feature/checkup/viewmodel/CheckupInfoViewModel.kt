package com.poverka.pro.presentation.feature.checkup.viewmodel

import androidx.lifecycle.viewModelScope
import com.poverka.domain.util.Mock
import com.poverka.pro.presentation.feature.base.BaseViewModel
import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckupInfoViewModel @Inject constructor(
    private val snackbarHolder: SnackbarHolder
) : BaseViewModel<CheckupInfoContract.Event, CheckupInfoContract.State, Nothing>() {

    override fun setInitialState(): CheckupInfoContract.State = CheckupInfoContract.State.Loading

    override fun handleEvent(event: CheckupInfoContract.Event) {
        if (event is CheckupInfoContract.Event.LoadCheckupData) {
            loadCheckupData(event.id)
        }
    }

    private fun loadCheckupData(id: String) {
        viewModelScope.launch {
            delay(1400)
            setState(CheckupInfoContract.State.Loaded(Mock.demoCheckup))
        }
    }

}