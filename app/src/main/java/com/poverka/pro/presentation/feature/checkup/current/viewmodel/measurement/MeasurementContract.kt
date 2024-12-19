package com.poverka.pro.presentation.feature.checkup.current.viewmodel.measurement

import com.poverka.pro.presentation.core.feature.ViewEffect
import com.poverka.pro.presentation.core.feature.ViewEvent
import com.poverka.pro.presentation.core.feature.ViewState
import com.poverka.pro.presentation.feature.checkup.current.viewmodel.measurement.model.PhotoWithReadings

object MeasurementContract {

    sealed interface Event : ViewEvent {
        data class SavePhotoAndReadings(val model: PhotoWithReadings) : Event
        data object TurnBack : Event
        data object CalculateResult : Event
        data class CompleteCheckup(val addAnotherMeter: Boolean) : Event
        data object SaveMeasurementData : Event
        data object Clear : Event
    }

    sealed interface State : ViewState {
        data object BeforePressure : State
        data object AfterPressure : State
        data object MeasurementResult : State
    }

    sealed interface Effect : ViewEffect {
        data object OpenPreviousScreen : Effect
        data object OpenNextMeasurementScreen : Effect
        data object OpenHomeScreen : Effect
        data class OpenClientInfoScreen(val forProtocol: String) : Effect
    }

}