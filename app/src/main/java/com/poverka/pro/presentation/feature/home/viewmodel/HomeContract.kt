package com.poverka.pro.presentation.feature.home.viewmodel

import com.poverka.pro.presentation.feature.base.*

object HomeContract {

    sealed interface Event : ViewEvent {
        data object RefreshData : Event
    }

    sealed interface State : ViewState {
        data object Loading : State
        data object Loaded : State
    }

}