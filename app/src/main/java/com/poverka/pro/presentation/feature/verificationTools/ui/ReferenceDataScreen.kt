package com.poverka.pro.presentation.feature.verificationTools.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poverka.domain.feature.verificationTools.ReferenceData
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.shared.FilledButton
import com.poverka.pro.presentation.feature.shared.text.PTextField
import com.poverka.pro.presentation.feature.verificationTools.viewmodel.VerificationToolsSharedViewModel

@Composable
fun ReferenceDataScreen(
    sharedViewModel: VerificationToolsSharedViewModel,
    openReferencePhotoStage: () -> Unit,
) {
    val existingData by sharedViewModel.referenceData.collectAsState()

    Content(
        existingReferenceData = existingData,
        onClickSave = { data ->
            sharedViewModel.setReferenceData(data)
            openReferencePhotoStage()
        }
    )
}

@Composable
private fun Content(
    existingReferenceData: ReferenceData?,
    onClickSave: (ReferenceData) -> Unit
) {
    var registrationNumberInput by remember {
        mutableStateOf(existingReferenceData?.registrationNumber.orEmpty())
    }
    var factoryNumberInput by remember {
        mutableStateOf(existingReferenceData?.factoryNumber.orEmpty())
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.reference_data_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Spacer(
            modifier = Modifier.height(28.dp)
        )
        Text(
            text = stringResource(R.string.reference_data_subtitle),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(
            modifier = Modifier.height(55.dp)
        )
        PTextField(
            value = registrationNumberInput,
            onValueChange = { registrationNumberInput = it },
            labelText = stringResource(R.string.reference_registration_number_label),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        Spacer(
            modifier = Modifier.height(42.dp)
        )
        PTextField(
            value = factoryNumberInput,
            onValueChange = { factoryNumberInput = it },
            labelText = stringResource(R.string.reference_factory_number_label),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        )
        Spacer(
            modifier = Modifier.height(42.dp)
        )
        FilledButton(
            modifier = Modifier.width(200.dp),
            label = stringResource(R.string.save_button),
            enabled = (registrationNumberInput.isNotEmpty() && factoryNumberInput.isNotEmpty()),
            onClick = {
                onClickSave.invoke(
                    ReferenceData(registrationNumberInput, factoryNumberInput)
                )
            }
        )
    }
}