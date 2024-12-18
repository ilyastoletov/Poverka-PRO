package com.poverka.pro.presentation.feature.verifier.viewmodel

import com.poverka.domain.feature.verifier.VerifierData
import com.poverka.pro.presentation.core.feature.ViewEffect
import com.poverka.pro.presentation.core.feature.ViewEvent
import com.poverka.pro.presentation.core.feature.ViewState

object VerifierDataContract {

    sealed interface Event : ViewEvent {
        data class Update(val verifierData: VerifierData) : Event
    }

    sealed interface State : ViewState {
        data object Idle : State
        data object Uploading : State
    }

    sealed interface Effect : ViewEffect {
        data object NavigateBack : Effect
    }

}