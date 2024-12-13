package com.poverka.pro.presentation.feature.snackbar.host

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update

class SnackbarHolder {

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage = _snackbarMessage.asSharedFlow()

    fun setSnackbar(message: String?) {
        _snackbarMessage.update { message }
    }

}