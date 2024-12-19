package com.poverka.pro.presentation.feature.checkup.current.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poverka.domain.feature.checkup.model.Measurement
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.checkup.current.viewmodel.measurement.MeasurementContract
import com.poverka.pro.presentation.feature.checkup.current.viewmodel.measurement.MeasurementViewModel
import com.poverka.pro.presentation.feature.checkup.current.viewmodel.measurement.model.PhotoWithReadings
import com.poverka.pro.presentation.feature.checkup.info.ui.component.MeasurementDataTable
import com.poverka.pro.presentation.feature.shared.FilledButton
import com.poverka.pro.presentation.feature.shared.GhostButton
import com.poverka.pro.presentation.feature.shared.LoadingScreen
import com.poverka.pro.presentation.feature.shared.PTopBar
import com.poverka.pro.presentation.feature.shared.PhotoBox
import com.poverka.pro.presentation.feature.shared.text.PTextField
import com.poverka.pro.presentation.provider.PhotoFileProvider

@Composable
fun MeasurementScreen(
    viewModel: MeasurementViewModel,
    number: Int,
    isFinalMeasurement: Boolean,
    onCancelCheckup: () -> Unit,
    openClientScreen: (protocol: String) -> Unit,
    openMainScreen: () -> Unit,
    onBack: () -> Unit,
    openNextMeasurementScreen: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val beforePressure by viewModel.beforePressure.collectAsState()
    val afterPressure by viewModel.afterPressure.collectAsState()
    val calculatedResult by viewModel.calculatedResult.collectAsState()

    val overlayLoaderEnabled by viewModel.overlayLoadingEnabled.collectAsState()

    BackHandler(
        enabled = true,
        onBack = {
            viewModel.setEvent(
                MeasurementContract.Event.TurnBack
            )
        }
    )

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            when(it) {
                is MeasurementContract.Effect.OpenClientInfoScreen -> openClientScreen(it.forProtocol)
                is MeasurementContract.Effect.OpenHomeScreen -> openMainScreen()
                is MeasurementContract.Effect.OpenNextMeasurementScreen -> openNextMeasurementScreen()
                is MeasurementContract.Effect.OpenPreviousScreen -> onBack()
            }
        }
    }

    val handleEvent = remember {
        { event: MeasurementContract.Event -> viewModel.setEvent(event) }
    }

    Content(
        state = state,
        measurementNumber = number,
        isFinalMeasurement = isFinalMeasurement,
        beforePressure = beforePressure,
        afterPressure = afterPressure,
        measurementResult = calculatedResult,
        handleEvent = handleEvent,
        onCancelCheckup = onCancelCheckup
    )

    if (overlayLoaderEnabled) {
        LoadingScreen(
            overlayAlpha = 0.65F
        )
    }

}

@Composable
private fun Content(
    state: MeasurementContract.State,
    measurementNumber: Int,
    isFinalMeasurement: Boolean,
    beforePressure: PhotoWithReadings?,
    afterPressure: PhotoWithReadings?,
    measurementResult: Measurement,
    handleEvent: (MeasurementContract.Event) -> Unit,
    onCancelCheckup: () -> Unit
) {

    Scaffold(
        topBar = {
            PTopBar(
                title = stringResource(
                    id = when(state) {
                        MeasurementContract.State.AfterPressure,
                        MeasurementContract.State.BeforePressure -> R.string.meter_readings_title
                        MeasurementContract.State.MeasurementResult -> R.string.measurement_result_title
                    }
                ),
                enableBackButton = true,
                actions = {
                    IconButton(
                        onClick = onCancelCheckup
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                },
                onBack = { handleEvent(MeasurementContract.Event.TurnBack) }
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            when(state) {
                is MeasurementContract.State.BeforePressure -> {
                    MeterReadingsScreen(
                        existing = beforePressure,
                        measurementNumber = measurementNumber,
                        isAfterPressure = false,
                        onSave = {
                            handleEvent(MeasurementContract.Event.SavePhotoAndReadings(it))
                        }
                    )
                }

                is MeasurementContract.State.AfterPressure -> {
                    MeterReadingsScreen(
                        existing = afterPressure,
                        measurementNumber = measurementNumber,
                        isAfterPressure = true,
                        onSave = {
                            handleEvent(MeasurementContract.Event.SavePhotoAndReadings(it))
                            handleEvent(MeasurementContract.Event.CalculateResult)
                        }
                    )
                }

                is MeasurementContract.State.MeasurementResult -> {
                    MeasurementResultScreen(
                        results = measurementResult,
                        isFinalMeasurement = isFinalMeasurement,
                        onCompleteCheckup = {
                            handleEvent(MeasurementContract.Event.CompleteCheckup(false))
                        },
                        onSave = { handleEvent(MeasurementContract.Event.SaveMeasurementData) },
                        onCancel = { handleEvent(MeasurementContract.Event.Clear) },
                        onClickAddAnotherMeter = {
                            handleEvent(MeasurementContract.Event.CompleteCheckup(true))
                        }
                    )
                }
            }
        }
    }

}

@Composable
private fun MeterReadingsScreen(
    existing: PhotoWithReadings?,
    measurementNumber: Int,
    isAfterPressure: Boolean,
    onSave: (PhotoWithReadings) -> Unit
) {

    var readingsInput by remember(existing) { mutableStateOf(existing?.readings.orEmpty()) }

    val context = LocalContext.current
    val photoSaveUri = remember(existing) {
        existing?.photoUri ?: PhotoFileProvider.getUriForImage(context)
    }
    var captureCounter by remember { mutableIntStateOf(0) }
    var photoBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { captureSuccess ->
        if (captureSuccess) {
            captureCounter++
        }
    }

    LaunchedEffect(captureCounter, photoSaveUri) {
        runCatching {
            val contentResolver = context.contentResolver
            val photoParcelFileDescriptor = contentResolver.openFileDescriptor(photoSaveUri, "r")
            if (photoParcelFileDescriptor != null) {
                val fileDescriptor = photoParcelFileDescriptor.fileDescriptor
                photoBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            }
            photoParcelFileDescriptor?.close()
        }
    }

    val launchCamera = remember {
        { cameraLauncher.launch(photoSaveUri) }
    }

    val pressurePrefix = remember { if (isAfterPressure) "после" else "до" }
    val saveButtonLabelResource = remember {
        if (isAfterPressure) R.string.calculate_result_button else R.string.save_button
    }

    val enableSaveButton = remember {
        derivedStateOf {
            readingsInput.isNotEmpty() || photoBitmap == null
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 30.dp)
        ) {
            Text(
                text = "Измерение №$measurementNumber ($pressurePrefix нагрузки)",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(
                modifier = Modifier.height(20.dp)
            )
            PTextField(
                value = readingsInput,
                onValueChange = { readingsInput = it },
                labelText = stringResource(R.string.meter_readings_placeholder),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        PhotoBox(
            photoBitmap = photoBitmap,
            saveButtonLabelResId = saveButtonLabelResource,
            enableSaveButton = enableSaveButton.value,
            onClickCapture = {
                if (photoBitmap != null) {
                    onSave.invoke(PhotoWithReadings(photoSaveUri, readingsInput))
                } else {
                    launchCamera()
                }
            },
            onClickRecapture = launchCamera
        )
        Spacer(
            modifier = Modifier.height(30.dp)
        )
    }

}

@Composable
private fun MeasurementResultScreen(
    results: Measurement,
    isFinalMeasurement: Boolean,
    onSave: () -> Unit,
    onCompleteCheckup: () -> Unit,
    onCancel: () -> Unit,
    onClickAddAnotherMeter: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 50.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(70.dp)
    ) {
        MeasurementDataTable(
            model = results
        )
        Column(
            modifier = Modifier.width(282.dp)
        ) {
            FilledButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(
                    id = if (isFinalMeasurement)
                        R.string.complete_checkup_button
                    else
                        R.string.save_button
                ),
                onClick = { if (isFinalMeasurement) onCompleteCheckup() else onSave() },
            )
            if (isFinalMeasurement) {
                FilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.add_another_meter_button),
                    onClick = onClickAddAnotherMeter
                )
            }
            GhostButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.cancel_button),
                onClick = onCancel
            )
        }
    }
}