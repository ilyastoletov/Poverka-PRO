package com.poverka.pro.presentation.feature.checkup.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poverka.domain.feature.checkup.model.Checkup
import com.poverka.domain.feature.checkup.model.Measurement
import com.poverka.domain.feature.checkup.model.client.Client
import com.poverka.domain.feature.checkup.model.meter.MeterInfo
import com.poverka.domain.util.Mock
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.checkup.ui.component.MeasurementDataTable
import com.poverka.pro.presentation.feature.checkup.viewmodel.CheckupInfoContract
import com.poverka.pro.presentation.feature.checkup.viewmodel.CheckupInfoViewModel
import com.poverka.pro.presentation.feature.shared.LoadingScreen
import com.poverka.pro.presentation.feature.shared.PTopBar
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun CheckupInfoScreen(
    id: String,
    viewModel: CheckupInfoViewModel,
    onBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(CheckupInfoContract.Event.LoadCheckupData(id))
    }

    CheckupInfoContent(
        state = state,
        onBack = onBack
    )
}

@Composable
private fun CheckupInfoContent(
    state: CheckupInfoContract.State,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            val title = remember(state) {
                if (state is CheckupInfoContract.State.Loaded)
                    "Протокол №${state.checkup.protocolId}"
                else
                    ""
            }

            PTopBar(
                title = title,
                enableBackButton = true,
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            when(state) {

                is CheckupInfoContract.State.Loading -> {
                    LoadingScreen()
                }

                is CheckupInfoContract.State.Loaded -> {
                    Content(
                        checkup = state.checkup
                    )
                }

                is CheckupInfoContract.State.NetworkError -> {}
            }
        }
    }
}

@Composable
private fun Content(checkup: Checkup) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(21.dp)
    ) {
        Spacer(
            modifier = Modifier
        )
        Text(
            text = stringResource(R.string.checkup_info_meter_id, checkup.meter.registrationId),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        InfoText(
            text = stringResource(R.string.checkup_date, checkup.date)
        )
        ClientInfo(
            client = checkup.client
        )
        Meter(
            meterInfo = checkup.meter
        )
        Headline(
            text = stringResource(R.string.checkup_results)
        )
        checkup.measurements.forEachIndexed { index, model ->
            MeasurementInfo(
                number = index + 1,
                measurement = model
            )
        }
        Spacer(
            modifier = Modifier
        )
    }
}

@Composable
private fun ClientInfo(client: Client) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Headline(
            text = stringResource(R.string.checkup_client)
        )
        InfoText(
            text = stringResource(R.string.client_full_name, client.fullName)
        )
        InfoText(
            text = client.type.display
        )
        InfoText(
            text = stringResource(R.string.client_postal_code, client.postalCode)
        )
        InfoText(
            text = stringResource(R.string.client_city, client.city)
        )
        InfoText(
            text = stringResource(R.string.client_street, client.street)
        )
        InfoText(
            text = stringResource(R.string.client_house, client.house)
        )
        InfoText(
            text = stringResource(R.string.client_flat, client.flat)
        )
    }
}

@Composable
private fun Meter(meterInfo: MeterInfo) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Headline(
            text = stringResource(R.string.checkup_meter)
        )
        InfoText(
            text = stringResource(R.string.meter_water_supply, meterInfo.waterSupply.display)
        )
        InfoText(
            text = stringResource(R.string.meter_registration_id, meterInfo.registrationId)
        )
        InfoText(
            text = stringResource(R.string.meter_release_year, meterInfo.releaseYear)
        )
        InfoText(
            text = stringResource(
                id = R.string.meter_modification,
                meterInfo.modification ?: "-"
            )
        )
        InfoText(
            text = stringResource(R.string.meter_factory_number, meterInfo.factoryNumber)
        )
        InfoText(
            text = stringResource(R.string.meter_position, meterInfo.meterPosition.display)
        )
        InfoText(
            text = stringResource(
                id = R.string.meter_visual_inspection,
                meterInfo.visualInspection.display
            )
        )
    }
}

@Composable
private fun MeasurementInfo(
    number: Int,
    measurement: Measurement
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Headline(
            text = stringResource(R.string.checkup_measurement_number, number)
        )
        MeasurementDataTable(
            model = measurement
        )
    }
}

@Composable
private fun InfoText(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Black
    )
}

@Composable
private fun Headline(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black
    )
}

@Preview(showBackground = true)
@Composable
private fun CheckupInfoScreenPreview() {
    PoverkaTheme {
        CheckupInfoContent(
            state = CheckupInfoContract.State.Loaded(Mock.demoCheckup),
            onBack = {}
        )
    }
}