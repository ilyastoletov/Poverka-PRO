package com.poverka.pro.presentation.feature.environmentData.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poverka.domain.feature.environmentData.EnvironmentData
import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnvironmentDataViewModel @Inject constructor(
    private val snackbarHolder: SnackbarHolder
) : ViewModel() {

    private val _overlayLoading = MutableStateFlow(false)
    val overlayLoading = _overlayLoading.asStateFlow()

    private val _openHomeScreen = MutableStateFlow(false)
    val openHomeScreen = _openHomeScreen.asSharedFlow()

    fun submitForm(data: EnvironmentData) {
        viewModelScope.launch {
            _overlayLoading.emit(true)
            delay(1400)
            snackbarHolder.setSnackbar("Данные внесены")
            _openHomeScreen.emit(true)
        }
    }

}