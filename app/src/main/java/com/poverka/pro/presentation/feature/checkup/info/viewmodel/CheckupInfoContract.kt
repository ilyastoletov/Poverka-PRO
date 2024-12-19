package com.poverka.pro.presentation.feature.checkup.info.viewmodel

import com.poverka.domain.feature.checkup.model.Checkup
import com.poverka.pro.presentation.core.feature.ViewEvent
import com.poverka.pro.presentation.core.feature.ViewState

object CheckupInfoContract {

    sealed interface Event : ViewEvent {
        data class LoadCheckupData(val id: String) : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data class Loaded(val checkup: Checkup) : State
        data object NetworkError : State
    }

}