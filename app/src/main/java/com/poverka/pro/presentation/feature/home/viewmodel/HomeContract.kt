package com.poverka.pro.presentation.feature.home.viewmodel

import com.poverka.pro.presentation.core.feature.ViewEvent
import com.poverka.pro.presentation.core.feature.ViewState

object HomeContract {

    sealed interface Event : ViewEvent {
        data object RefreshData : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
    }

}