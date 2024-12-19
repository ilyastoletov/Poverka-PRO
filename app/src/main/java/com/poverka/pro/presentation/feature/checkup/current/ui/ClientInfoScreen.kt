package com.poverka.pro.presentation.feature.checkup.current.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poverka.domain.feature.checkup.model.client.Client
import com.poverka.domain.feature.checkup.model.client.ClientType
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.checkup.current.viewmodel.client.ClientContract
import com.poverka.pro.presentation.feature.checkup.current.viewmodel.client.ClientInfoViewModel
import com.poverka.pro.presentation.feature.shared.FilledButton
import com.poverka.pro.presentation.feature.shared.LoadingScreen
import com.poverka.pro.presentation.feature.shared.PRadioButton
import com.poverka.pro.presentation.feature.shared.text.DatePickerTextField
import com.poverka.pro.presentation.feature.shared.text.PTextField
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun ClientInfoScreen(
    protocolId: String?,
    viewModel: ClientInfoViewModel,
    openMeterInfoScreen: () -> Unit
) {

    val client by viewModel.client.collectAsState()
    val protocols by viewModel.protocols.collectAsState()
    val overlayLoaderEnabled by viewModel.overlayLoaderVisible.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.apply {
            if (protocolId != null) {
                setEvent(ClientContract.Event.LoadClientFromProtocol(protocolId))
            } else {
                setEvent(ClientContract.Event.LoadExistingData)
            }
            setEvent(ClientContract.Event.LoadLatestProtocols)
        }
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            if (it is ClientContract.Effect.OpenMeterScreen) {
                openMeterInfoScreen()
            }
        }
    }

    Content(
        existingClient = client,
        initialProtocol = protocolId,
        latestProtocols = protocols,
        loadClientFromProtocol = {
            viewModel.setEvent(ClientContract.Event.LoadClientFromProtocol(it))
        },
        onSave = { clientData, date ->
            viewModel.setEvent(
                ClientContract.Event.UploadClientData(clientData, date)
            )
        }
    )

    if (overlayLoaderEnabled) {
        LoadingScreen(
            overlayAlpha = 0.65F
        )
    }

}

@Composable
private fun Content(
    initialProtocol: String?,
    existingClient: Client?,
    latestProtocols: List<String>,
    loadClientFromProtocol: (String) -> Unit,
    onSave: (client: Client, date: String) -> Unit
) {
    var checkupDate by remember { mutableStateOf("") }

    var selectedProtocol by remember { mutableStateOf(initialProtocol.orEmpty()) }

    var fullName by remember(existingClient) { mutableStateOf(existingClient?.fullName.orEmpty()) }
    var clientType by remember(existingClient) {
        mutableStateOf(existingClient?.type ?: ClientType.PERSON)
    }

    var postalCode by remember(existingClient) {
        mutableStateOf(existingClient?.postalCode.orEmpty())
    }
    var city by remember(existingClient) { mutableStateOf(existingClient?.city.orEmpty()) }
    var street by remember(existingClient) { mutableStateOf(existingClient?.street.orEmpty()) }
    var house by remember(existingClient) { mutableStateOf(existingClient?.house.orEmpty()) }
    var flat by remember(existingClient) { mutableStateOf(existingClient?.flat.orEmpty()) }

    val isInputCorrect by remember(existingClient) {
        derivedStateOf {
            checkupDate.isNotEmpty()
                    && fullName.isNotEmpty()
                    && postalCode.length == 6
                    && city.isNotEmpty()
                    && street.isNotEmpty()
                    && house.isNotEmpty()
                    && flat.isNotEmpty()
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
        ProtocolDropdown(
            selectedProtocol = selectedProtocol,
            protocols = latestProtocols,
            onSelect = { protocol ->
                selectedProtocol = protocol
                loadClientFromProtocol(protocol)
            }
        )
        PTextField(
            value = fullName,
            labelText = stringResource(R.string.full_name_placeholder),
            onValueChange = { fullName = it },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            ),
            modifier = Modifier.fillMaxWidth()
        )
        DatePickerTextField(
            label = stringResource(R.string.checkup_date_placeholder),
            date = checkupDate,
            onSaveDate = { checkupDate = it }
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            PRadioButton(
                text = stringResource(R.string.client_type_person),
                selected = (clientType == ClientType.PERSON),
                onSelect = { clientType = ClientType.PERSON },
            )
            PRadioButton(
                text = stringResource(R.string.client_type_company),
                selected = (clientType == ClientType.COMPANY),
                onSelect = { clientType = ClientType.COMPANY }
            )
        }
        PTextField(
            value = postalCode,
            labelText = stringResource(R.string.postal_code_placeholder),
            onValueChange = { postalCode = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier.fillMaxWidth()
        )
        PTextField(
            value = city,
            labelText = stringResource(R.string.city_placeholder),
            onValueChange = { city = it },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            ),
            modifier = Modifier.fillMaxWidth()
        )
        PTextField(
            value = street,
            labelText = stringResource(R.string.street_placeholder),
            onValueChange = { street = it },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            PTextField(
                value = house,
                labelText = stringResource(R.string.house_placeholder),
                onValueChange = { house = it },
                modifier = Modifier.weight(0.5f)
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            PTextField(
                value = flat,
                labelText = stringResource(R.string.flat_placeholder),
                onValueChange = { flat = it },
                modifier = Modifier.weight(0.5f)
            )
        }
        Spacer(
            modifier = Modifier
        )
        FilledButton(
            label = stringResource(R.string.save_button),
            modifier = Modifier.width(200.dp),
            enabled = isInputCorrect,
            onClick = {
                onSave.invoke(
                    Client(
                        fullName = fullName,
                        type = clientType,
                        postalCode = postalCode,
                        city = city,
                        street = street,
                        house = house,
                        flat = flat,
                    ),
                    checkupDate
                )
            }
        )
        Spacer(
            modifier = Modifier
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProtocolDropdown(
    selectedProtocol: String,
    protocols: List<String>,
    onSelect: (String) -> Unit
) {
    var protocolsListExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = protocolsListExpanded,
        onExpandedChange = { protocolsListExpanded = it }
    ) {
        PTextField(
            value = selectedProtocol,
            onValueChange = {},
            labelText = stringResource(R.string.borrow_protocol_placeholder),
            readOnly = true,
            trailing = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = protocolsListExpanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = protocolsListExpanded,
            onDismissRequest = { protocolsListExpanded = false }
        ) {
            repeat(protocols.size) { index ->
                val item = remember { protocols[index] }

                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        onSelect(item)
                        protocolsListExpanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClientInfoContentPreview() {
    PoverkaTheme {
        Content(
            initialProtocol = null,
            existingClient = null,
            latestProtocols = listOf(),
            loadClientFromProtocol = {},
            onSave = { _, _ -> }
        )
    }
}