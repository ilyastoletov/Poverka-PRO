package com.poverka.pro.presentation.feature.verificationTools.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poverka.domain.feature.verificationTools.ReferenceData
import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import com.poverka.pro.presentation.feature.verificationTools.model.Stage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationToolsSharedViewModel @Inject constructor(
    private val snackbarHolder: SnackbarHolder
) : ViewModel() {

    private val _referenceData = MutableStateFlow<ReferenceData?>(null)
    val referenceData = _referenceData.asStateFlow()

    private val _referencePhoto = MutableStateFlow<Uri?>(null)
    private val _thermohygrometerPhoto = MutableStateFlow<Uri?>(null)
    private val _thermometerPhoto = MutableStateFlow<Uri?>(null)
    private val _stopwatchPhoto = MutableStateFlow<Uri?>(null)

    private val _overlayLoadingEnabled = MutableStateFlow(false)
    val overlayLoadingEnabled = _overlayLoadingEnabled.asStateFlow()

    private val _openHomeScreen = MutableStateFlow(false)
    val openHomeScreen = _openHomeScreen.asSharedFlow()

    fun setReferenceData(data: ReferenceData) {
        _referenceData.update { data }
        // ...
    }

    fun getPhotoStateForStage(stage: Stage): StateFlow<Uri?> {
        return getPhotoMutableStateForStage(stage).asStateFlow()
    }

    private fun getPhotoMutableStateForStage(stage: Stage): MutableStateFlow<Uri?> {
        return when(stage) {
            Stage.Reference -> _referencePhoto
            Stage.Stopwatch -> _stopwatchPhoto
            Stage.Thermohygrometer -> _thermohygrometerPhoto
            Stage.Thermometer -> _thermometerPhoto
        }
    }

    fun updatePhotoForStage(stage: Stage, photoUri: Uri) {
        val photoStateFlow = getPhotoMutableStateForStage(stage)
        photoStateFlow.update { photoUri }
    }

    fun submitVerificationToolsForm() {
        viewModelScope.launch {
            _overlayLoadingEnabled.emit(true)
            delay(1500)
            _openHomeScreen.emit(true)
            snackbarHolder.setSnackbar("Данные внесены")
        }
    }

}