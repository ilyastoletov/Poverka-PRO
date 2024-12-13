package com.poverka.pro.presentation.feature.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.poverka.domain.feature.auth.model.Credentials
import com.poverka.pro.presentation.feature.base.BaseViewModel
import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val snackbarHolder: SnackbarHolder
) : BaseViewModel<AuthContract.Event, AuthContract.State, AuthContract.Effect>() {

    private val _enableButtonLoading = MutableStateFlow(false)
    val enableButtonLoading = _enableButtonLoading.asStateFlow()

    override fun setInitialState(): AuthContract.State {
        return AuthContract.State.Initial
    }

    override fun handleEvent(event: AuthContract.Event) {
        if (event is AuthContract.Event.Login) {
            handleLoginEvent(event.credentials)
        }
    }

    private fun handleLoginEvent(credentials: Credentials) {
        viewModelScope.launch {
            _enableButtonLoading.update { true }
            delay(1500)
            if (credentials.login == "error") {
                _enableButtonLoading.update { false }
                snackbarHolder.setSnackbar("Активная сессия с таким IMEI уже существует")
            } else {
                setEffect(AuthContract.Effect.OpenHomeScreen)
            }
        }
    }

}