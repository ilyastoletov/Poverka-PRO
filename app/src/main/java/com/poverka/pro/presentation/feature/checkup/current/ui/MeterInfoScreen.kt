package com.poverka.pro.presentation.feature.checkup.current.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poverka.domain.feature.checkup.model.meter.MeterInfo
import com.poverka.domain.feature.checkup.model.meter.MeterPosition
import com.poverka.domain.feature.checkup.model.meter.VisualInspection
import com.poverka.domain.feature.checkup.model.meter.WaterSupply
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.checkup.current.viewmodel.meter.MeterContract
import com.poverka.pro.presentation.feature.checkup.current.viewmodel.meter.MeterViewModel
import com.poverka.pro.presentation.feature.shared.FilledButton
import com.poverka.pro.presentation.feature.shared.LoadingScreen
import com.poverka.pro.presentation.feature.shared.PRadioButton
import com.poverka.pro.presentation.feature.shared.text.DatePickerTextField
import com.poverka.pro.presentation.feature.shared.text.PTextField
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun MeterInfoScreen(
    viewModel: MeterViewModel,
    openMeasurementScreen: () -> Unit
) {

    val existingMeter by viewModel.existingMeter.collectAsState()
    val overlayLoaderVisible by viewModel.overlayLoaderVisible.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setEvent(MeterContract.Event.LoadInitialMeterData)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            if (it is MeterContract.Effect.OpenMeasurementScreen) {
                openMeasurementScreen()
            }
        }
    }

    Content(
        existingMeter = existingMeter,
        onSave = { meterInfo, nextCheckup ->
            viewModel.setEvent(
                MeterContract.Event.UploadMeterInfo(meterInfo, nextCheckup)
            )
        }
    )

    if (overlayLoaderVisible) {
        LoadingScreen(
            overlayAlpha = 0.65F
        )
    }
}

@Composable
private fun Content(
    existingMeter: MeterInfo?,
    onSave: (meter: MeterInfo, dateOfNext: String?) -> Unit
) {
    var protocolIdentifier by remember(existingMeter) {
        mutableStateOf(existingMeter?.protocolId.orEmpty())
    }
    var waterSupplyType by remember(existingMeter) {
        mutableStateOf(existingMeter?.waterSupply ?: WaterSupply.HOT)
    }
    var registrationIdentifier by remember(existingMeter) {
        mutableStateOf(existingMeter?.registrationId.orEmpty())
    }
    var releaseYear by remember(existingMeter) {
        mutableStateOf(existingMeter?.releaseYear.orEmpty())
    }
    var modification by remember(existingMeter) {
        mutableStateOf(existingMeter?.modification.orEmpty())
    }
    var factoryNumber by remember(existingMeter) {
        mutableStateOf(existingMeter?.factoryNumber.orEmpty())
    }
    var meterPosition by remember(existingMeter) {
        mutableStateOf(existingMeter?.meterPosition ?: MeterPosition.HORIZONTAL)
    }
    var visualInspection by remember(existingMeter) {
        mutableStateOf(existingMeter?.visualInspection ?: VisualInspection.CORRECT)
    }

    var nextCheckupDate by remember { mutableStateOf("") }

    val inputIsCorrect by remember(existingMeter) {
        derivedStateOf {
            protocolIdentifier.isNotEmpty()
                    && registrationIdentifier.isNotEmpty()
                    && (releaseYear.length == 4)
                    && factoryNumber.isNotEmpty()
                    && (nextCheckupDate.isNotEmpty() || visualInspection == VisualInspection.INCORRECT)
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(horizontal = 25.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
        )
        PTextField(
            value = protocolIdentifier,
            onValueChange = { protocolIdentifier = it },
            labelText = stringResource(R.string.protocol_identifier_placeholder),
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalRadioGroup(
            title = stringResource(R.string.water_supply_type_title),
            firstTitle = stringResource(R.string.water_supply_hot),
            firstSelected = waterSupplyType == WaterSupply.HOT,
            onSelectFirst = { waterSupplyType = WaterSupply.HOT },
            secondTitle = stringResource(R.string.water_supply_cold),
            secondSelected = waterSupplyType == WaterSupply.COLD,
            onSelectSecond = { waterSupplyType = WaterSupply.COLD }
        )
        PTextField(
            value = registrationIdentifier,
            onValueChange = { registrationIdentifier = it },
            labelText = stringResource(R.string.meter_registration_identifier_placeholder),
            modifier = Modifier.fillMaxWidth()
        )
        PTextField(
            value = releaseYear,
            onValueChange = { releaseYear = it },
            labelText = stringResource(R.string.meter_release_year_placeholder),
            maxLength = 4,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier.fillMaxWidth()
        )
        PTextField(
            value = modification,
            onValueChange = { modification = it },
            labelText = stringResource(R.string.meter_modification_placeholder),
            modifier = Modifier.fillMaxWidth()
        )
        PTextField(
            value = factoryNumber,
            onValueChange = { factoryNumber = it },
            labelText = stringResource(R.string.meter_factory_number_placeholder),
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalRadioGroup(
            title = stringResource(R.string.meter_position_title),
            firstTitle = stringResource(R.string.meter_position_horizontal),
            firstSelected = meterPosition == MeterPosition.HORIZONTAL,
            onSelectFirst = { meterPosition = MeterPosition.HORIZONTAL },
            secondTitle = stringResource(R.string.meter_position_non_horizontal),
            secondSelected = meterPosition == MeterPosition.NON_HORIZONTAL,
            onSelectSecond = { meterPosition = MeterPosition.NON_HORIZONTAL }
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.meter_visual_inspection_title),
                fontSize = 16.sp,
                color = Color.Black,
            )
            Spacer(
                modifier = Modifier.height(12.dp)
            )
            PRadioButton(
                text = stringResource(R.string.meter_visual_inspection_correct),
                selected = visualInspection == VisualInspection.CORRECT,
                onSelect = {
                    visualInspection = VisualInspection.CORRECT
                    nextCheckupDate = ""
                }
            )
            PRadioButton(
                text = stringResource(R.string.meter_visual_inspection_incorrect),
                selected = visualInspection == VisualInspection.INCORRECT,
                onSelect = { visualInspection = VisualInspection.INCORRECT }
            )
        }
        if (visualInspection == VisualInspection.CORRECT) {
            DatePickerTextField(
                label = stringResource(R.string.meter_next_checkup_date_placeholder),
                date = nextCheckupDate,
                onSaveDate = { nextCheckupDate = it }
            )
        }
        FilledButton(
            label = stringResource(R.string.save_button),
            modifier = Modifier.width(200.dp),
            enabled = inputIsCorrect,
            onClick = {
                onSave.invoke(
                    MeterInfo(
                        protocolId = protocolIdentifier,
                        registrationId = registrationIdentifier,
                        waterSupply = waterSupplyType,
                        releaseYear = releaseYear,
                        modification = modification,
                        factoryNumber = factoryNumber,
                        meterPosition = meterPosition,
                        visualInspection = visualInspection
                    ),
                    nextCheckupDate
                )
            }
        )
        Spacer(
            modifier = Modifier
        )
    }

}

@Composable
private fun HorizontalRadioGroup(
    modifier: Modifier = Modifier,
    title: String,
    firstTitle: String,
    firstSelected: Boolean,
    onSelectFirst: () -> Unit,
    secondTitle: String,
    secondSelected: Boolean,
    onSelectSecond: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(12.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PRadioButton(
                text = firstTitle,
                selected = firstSelected,
                onSelect = onSelectFirst
            )
            PRadioButton(
                text = secondTitle,
                selected = secondSelected,
                onSelect = onSelectSecond
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MeterInfoScreenPreview() {
    PoverkaTheme {
        Content(
            existingMeter = null,
            onSave = { _, _ -> }
        )
    }
}