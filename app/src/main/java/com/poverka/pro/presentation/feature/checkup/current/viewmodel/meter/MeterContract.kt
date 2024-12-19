package com.poverka.pro.presentation.feature.checkup.current.viewmodel.meter

import com.poverka.domain.feature.checkup.model.meter.MeterInfo
import com.poverka.pro.presentation.core.feature.ViewEffect
import com.poverka.pro.presentation.core.feature.ViewEvent
import com.poverka.pro.presentation.core.feature.ViewState

object MeterContract {

    sealed interface Event : ViewEvent {
        data object LoadInitialMeterData : Event
        data class UploadMeterInfo(
            val meterInfo: MeterInfo,
            val dateOfNextCheckup: String?
        ) : Event
    }

    sealed interface State : ViewState {
        data object Idle : State
    }

    sealed interface Effect : ViewEffect {
        data object OpenMeasurementScreen : Effect
    }

}