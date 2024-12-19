package com.poverka.pro.presentation.feature.verifier.viewmodel

import androidx.lifecycle.viewModelScope
import com.poverka.domain.feature.verifier.VerifierData
import com.poverka.pro.presentation.core.feature.BaseViewModel
import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifierDataViewModel @Inject constructor(
    private val snackbarHolder: SnackbarHolder
) : BaseViewModel<VerifierDataContract.Event, VerifierDataContract.State, VerifierDataContract.Effect>() {

    private val _overlayLoading = MutableStateFlow(false)
    val overlayLoading = _overlayLoading.asStateFlow()

    private val _existingVerifierData = MutableStateFlow<VerifierData?>(null)
    val existingVerifierData = _existingVerifierData.asStateFlow()

    init {
        loadExistingData()
    }

    private fun loadExistingData() {
        // TODO: Make load logic
    }

    override fun setInitialState(): VerifierDataContract.State = VerifierDataContract.State.Idle

    override fun handleEvent(event: VerifierDataContract.Event) {
        if (event is VerifierDataContract.Event.Update) {
            updateVerifierData(event.verifierData)
        }
    }

    private fun updateVerifierData(data: VerifierData) {
        viewModelScope.launch(dispatcher) {
            _overlayLoading.emit(true)
            delay(1400)
            snackbarHolder.setSnackbar("Данные поверителя обновлены")
            setEffect(VerifierDataContract.Effect.NavigateBack)
        }
    }

}