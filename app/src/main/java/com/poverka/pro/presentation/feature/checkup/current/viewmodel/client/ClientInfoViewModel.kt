package com.poverka.pro.presentation.feature.checkup.current.viewmodel.client

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.poverka.domain.feature.checkup.interactor.CheckupInteractor
import com.poverka.domain.feature.checkup.model.client.Client
import com.poverka.pro.R
import com.poverka.pro.presentation.core.feature.BaseViewModel
import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientInfoViewModel @Inject constructor(
    private val res: Resources,
    private val snackbarHolder: SnackbarHolder,
    private val interactor: CheckupInteractor
) : BaseViewModel<ClientContract.Event, ClientContract.State, ClientContract.Effect>() {

    private val _client = MutableStateFlow<Client?>(null)
    val client = _client.asStateFlow()

    private val _protocols = MutableStateFlow<List<String>>(listOf())
    val protocols = _protocols.asStateFlow()

    private val _overlayLoaderVisible = MutableStateFlow(false)
    val overlayLoaderVisible = _overlayLoaderVisible.asStateFlow()

    override fun setInitialState(): ClientContract.State {
        return ClientContract.State.Idle
    }

    override fun handleEvent(event: ClientContract.Event) = when(event) {
        is ClientContract.Event.LoadExistingData -> loadExistingClientData()
        is ClientContract.Event.LoadLatestProtocols -> loadProtocols()
        is ClientContract.Event.LoadClientFromProtocol -> loadClientFromProtocol(event.protocolId)
        is ClientContract.Event.UploadClientData -> uploadClientData(event.client)
    }

    private fun loadExistingClientData() {
        viewModelScope.launch(dispatcher) {
            interactor.getCurrentClientData()
                .onSuccess { _client.emit(it) }
        }
    }

    private fun loadProtocols() {
        viewModelScope.launch(dispatcher) {
            interactor.loadLatestProtocols()
                .onSuccess { _protocols.emit(it) }
        }
    }

    private fun loadClientFromProtocol(protocolId: String) {
        viewModelScope.launch(dispatcher) {
            _overlayLoaderVisible.emit(true)
            interactor.getClientByProtocol(protocolId).fold(
                onSuccess = {
                    _client.emit(it)
                    _overlayLoaderVisible.emit(false)
                },
                onFailure = {
                    snackbarHolder.setSnackbar(res.getString(R.string.client_fetching_error))
                    _overlayLoaderVisible.emit(false)
                }
            )
        }
    }

    private fun uploadClientData(client: Client) {
        viewModelScope.launch {
            _overlayLoaderVisible.emit(true)
            interactor.uploadClientData(client)
                .fold(
                    onSuccess = {
                        _overlayLoaderVisible.emit(false)
                        setEffect(ClientContract.Effect.OpenMeterScreen)
                    },
                    onFailure = {
                        snackbarHolder.setSnackbar(res.getString(R.string.client_uploading_error))
                        _overlayLoaderVisible.emit(false)
                    }
                )
        }
    }

}