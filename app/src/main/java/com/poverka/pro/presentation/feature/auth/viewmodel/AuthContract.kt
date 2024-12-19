package com.poverka.pro.presentation.feature.auth.viewmodel

import com.poverka.domain.feature.auth.model.Credentials
import com.poverka.pro.presentation.core.feature.ViewEffect
import com.poverka.pro.presentation.core.feature.ViewEvent
import com.poverka.pro.presentation.core.feature.ViewState

object AuthContract {

    sealed interface Event : ViewEvent {
        data class Login(val credentials: Credentials) : Event
    }

    sealed interface State : ViewState {
        data object Initial : State
    }

    sealed interface Effect : ViewEffect {
        data object OpenHomeScreen : Effect
    }

}