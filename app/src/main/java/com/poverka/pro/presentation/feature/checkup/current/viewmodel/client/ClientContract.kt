package com.poverka.pro.presentation.feature.checkup.current.viewmodel.client

import com.poverka.domain.feature.checkup.model.client.Client
import com.poverka.pro.presentation.core.feature.ViewEffect
import com.poverka.pro.presentation.core.feature.ViewEvent
import com.poverka.pro.presentation.core.feature.ViewState

object ClientContract {

    sealed interface Event : ViewEvent {
        data object LoadExistingData : Event
        data object LoadLatestProtocols : Event
        data class LoadClientFromProtocol(val protocolId: String) : Event
        data class UploadClientData(
            val client: Client,
            val checkupDate: String
        ) : Event
    }

    sealed interface State : ViewState {
        data object Idle : State
    }

    sealed interface Effect : ViewEffect {
        data object OpenMeterScreen : Effect
    }

}