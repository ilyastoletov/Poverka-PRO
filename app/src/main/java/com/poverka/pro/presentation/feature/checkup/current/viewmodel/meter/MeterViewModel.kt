package com.poverka.pro.presentation.feature.checkup.current.viewmodel.meter

import androidx.lifecycle.viewModelScope
import com.poverka.domain.feature.checkup.model.meter.MeterInfo
import com.poverka.pro.presentation.core.feature.BaseViewModel
import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeterViewModel @Inject constructor(
    private val snackbarHolder: SnackbarHolder
) : BaseViewModel<MeterContract.Event, MeterContract.State, MeterContract.Effect>() {

    private val _existingMeter = MutableStateFlow<MeterInfo?>(null)
    val existingMeter = _existingMeter.asStateFlow()

    private val _overlayLoaderVisible = MutableStateFlow(false)
    val overlayLoaderVisible = _overlayLoaderVisible.asStateFlow()

    override fun setInitialState(): MeterContract.State {
        return MeterContract.State.Idle
    }

    override fun handleEvent(event: MeterContract.Event) = when(event) {
        is MeterContract.Event.LoadInitialMeterData -> loadExistingMeter()
        is MeterContract.Event.UploadMeterInfo -> uploadMeterData(event.meterInfo)
    }

    private fun loadExistingMeter() {

    }

    private fun uploadMeterData(meterInfo: MeterInfo) {
        viewModelScope.launch {
            _overlayLoaderVisible.emit(true)
            delay(1200)
            _overlayLoaderVisible.emit(false)
            setEffect(MeterContract.Effect.OpenMeasurementScreen)
        }
    }

}