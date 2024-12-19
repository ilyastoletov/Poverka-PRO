package com.poverka.pro.presentation.feature.checkup.current.viewmodel.measurement

import androidx.lifecycle.viewModelScope
import com.poverka.domain.feature.checkup.model.Measurement
import com.poverka.domain.util.Mock
import com.poverka.pro.presentation.core.feature.BaseViewModel
import com.poverka.pro.presentation.feature.checkup.current.viewmodel.measurement.model.PhotoWithReadings
import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeasurementViewModel @Inject constructor(
    private val snackbarHolder: SnackbarHolder
) : BaseViewModel<MeasurementContract.Event, MeasurementContract.State, MeasurementContract.Effect>() {

    private val _beforePressure = MutableStateFlow<PhotoWithReadings?>(null)
    val beforePressure = _beforePressure.asStateFlow()

    private val _afterPressure = MutableStateFlow<PhotoWithReadings?>(null)
    val afterPressure = _afterPressure.asStateFlow()

    private val _overlayLoadingEnabled = MutableStateFlow(false)
    val overlayLoadingEnabled = _overlayLoadingEnabled.asStateFlow()

    private val _calculatedResult = MutableStateFlow(Measurement())
    val calculatedResult = _calculatedResult.asStateFlow()

    override fun setInitialState(): MeasurementContract.State {
        return MeasurementContract.State.BeforePressure
    }

    override fun handleEvent(event: MeasurementContract.Event) = when(event) {
        is MeasurementContract.Event.SavePhotoAndReadings -> savePhotoWithReadings(event.model)
        is MeasurementContract.Event.TurnBack -> setPreviousState()
        is MeasurementContract.Event.CalculateResult -> calculateResult()
        is MeasurementContract.Event.CompleteCheckup -> {
            completeCheckup()
            setEffect(
                if (event.addAnotherMeter) {
                    MeasurementContract.Effect.OpenClientInfoScreen("1-00044")
                } else {
                    MeasurementContract.Effect.OpenHomeScreen
                }
            )
        }
        is MeasurementContract.Event.Clear -> clearData()
        is MeasurementContract.Event.SaveMeasurementData -> saveMeasurementData()
    }

    private fun savePhotoWithReadings(model: PhotoWithReadings) {
        viewModelScope.launch {
            when(state.value) {
                MeasurementContract.State.AfterPressure -> {
                    _afterPressure.emit(model)
                }

                MeasurementContract.State.BeforePressure -> {
                    _beforePressure.emit(model)
                    setState(MeasurementContract.State.AfterPressure)
                }

                else -> {}
            }
        }
    }

    private fun setPreviousState() {
        when(state.value) {
            MeasurementContract.State.AfterPressure -> {
                setState(MeasurementContract.State.BeforePressure)
            }
            MeasurementContract.State.BeforePressure -> {
                setEffect(MeasurementContract.Effect.OpenPreviousScreen)
            }
            MeasurementContract.State.MeasurementResult -> {
                setState(MeasurementContract.State.AfterPressure)
            }
        }
    }

    private fun calculateResult() {
        viewModelScope.launch {
            _overlayLoadingEnabled.emit(true)
            delay(1000)
            _calculatedResult.emit(Mock.demoMeasurements.first())
            setState(MeasurementContract.State.MeasurementResult)
            _overlayLoadingEnabled.emit(false)
        }
    }

    private fun completeCheckup() {
        viewModelScope.launch {
            _overlayLoadingEnabled.emit(true)
            delay(800)
            _overlayLoadingEnabled.emit(false)
        }
    }

    private fun clearData() {
        viewModelScope.launch {
            _beforePressure.emit(null)
            _afterPressure.emit(null)
            setState(MeasurementContract.State.BeforePressure)
        }
    }

    private fun saveMeasurementData() {
        viewModelScope.launch {
            _overlayLoadingEnabled.emit(true)
            delay(800)
            setEffect(MeasurementContract.Effect.OpenNextMeasurementScreen)
            _overlayLoadingEnabled.emit(false)
        }
    }

}