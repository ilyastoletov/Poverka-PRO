package com.poverka.pro.presentation.feature.environmentData.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poverka.domain.feature.environmentData.EnvironmentData
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.environmentData.viewmodel.EnvironmentDataViewModel
import com.poverka.pro.presentation.feature.shared.FilledButton
import com.poverka.pro.presentation.feature.shared.LoadingScreen
import com.poverka.pro.presentation.feature.shared.PTopBar
import com.poverka.pro.presentation.feature.shared.text.PTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EnvironmentDataScreen(
    viewModel: EnvironmentDataViewModel,
    onBack: () -> Unit
) {

    val overlayLoading by viewModel.overlayLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.openHomeScreen.collectLatest { turnBack ->
            if (turnBack) onBack()
        }
    }

    Scaffold(
        topBar = {
            PTopBar(
                title = stringResource(R.string.environment_data_title),
                titleFontSize = 18.sp,
                enableBackButton = true,
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->
        Content(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            onSubmit = { viewModel.submitForm(it) }
        )
    }

    if (overlayLoading) {
        LoadingScreen(
            overlayAlpha = 0.65F
        )
    }

}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    onSubmit: (EnvironmentData) -> Unit
) {
    var airTemperatureInput by remember { mutableStateOf("") }
    var waterTemperatureInput by remember { mutableStateOf("") }
    var airHumidityInput by remember { mutableStateOf("") }
    var airPressureInput by remember { mutableStateOf("") }

    var showConfirmationDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(26.dp)
        ) {
            PTextField(
                value = airTemperatureInput,
                onValueChange = { airTemperatureInput = it },
                modifier = Modifier.fillMaxWidth(),
                labelText = stringResource(R.string.environment_air_temperature_label),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            PTextField(
                value = waterTemperatureInput,
                onValueChange = { waterTemperatureInput = it },
                modifier = Modifier.fillMaxWidth(),
                labelText = stringResource(R.string.environment_water_temperature_label),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            PTextField(
                value = airHumidityInput,
                onValueChange = { airHumidityInput = it },
                modifier = Modifier.fillMaxWidth(),
                labelText = stringResource(R.string.environment_air_humidity_label),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            PTextField(
                value = airPressureInput,
                onValueChange = { airPressureInput = it },
                modifier = Modifier.fillMaxWidth(),
                labelText = stringResource(R.string.environment_air_pressure_label),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Spacer(
            modifier = Modifier.height(76.dp)
        )
        FilledButton(
            modifier = Modifier.width(200.dp),
            label = stringResource(R.string.save_button),
            onClick = { showConfirmationDialog = true }
        )
    }

    if (showConfirmationDialog) {
        ConfirmFormSubmitDialog(
            onConfirm = {
                onSubmit.invoke(
                    EnvironmentData(
                        airTemperature = airTemperatureInput,
                        airPressure = airPressureInput,
                        airHumidity = airHumidityInput,
                        waterTemperature = waterTemperatureInput
                    )
                )
            },
            onDismiss = { showConfirmationDialog = false }
        )
    }

}

@Composable
private fun ConfirmFormSubmitDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.environment_confirmation_dialog_title)) },
        text = { Text(text = stringResource(R.string.environment_confirmation_dialog_text)) },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(); onDismiss() }
            ) {
                Text(
                    text = stringResource(R.string.confirm_button),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(R.string.decline_button),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
    )
}