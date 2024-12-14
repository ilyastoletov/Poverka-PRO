package com.poverka.pro.presentation.feature.verifier.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poverka.domain.feature.verifier.VerifierData
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.shared.FilledLoaderButton
import com.poverka.pro.presentation.feature.shared.LoadingScreen
import com.poverka.pro.presentation.feature.shared.text.PTextField
import com.poverka.pro.presentation.feature.shared.PTopBar
import com.poverka.pro.presentation.feature.shared.text.transformation.rememberMaskVisualTransformation
import com.poverka.pro.presentation.feature.verifier.viewmodel.VerifierDataContract
import com.poverka.pro.presentation.feature.verifier.viewmodel.VerifierDataViewModel
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun VerifierDataScreen(
    viewModel: VerifierDataViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val existingData by viewModel.existingVerifierData.collectAsState()
    val overlayLoading by viewModel.overlayLoading.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect {
            if (it is VerifierDataContract.Effect.NavigateBack) {
                onBack()
            }
        }
    }

    Content(
        existingData = existingData,
        isButtonLoading = (state is VerifierDataContract.State.Uploading),
        onSubmit = { viewModel.handleEvent(VerifierDataContract.Event.Update(it)) },
        onBack = onBack
    )

    if (overlayLoading) {
        LoadingScreen(
            overlayAlpha = 0.65F
        )
    }

}

@Composable
private fun Content(
    existingData: VerifierData?,
    isButtonLoading: Boolean,
    onBack: () -> Unit,
    onSubmit: (VerifierData) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var fullNameInput by remember { mutableStateOf(existingData?.fullName.orEmpty()) }
    var snilsNumberInput by remember { mutableStateOf(existingData?.snilsCardNumber.orEmpty()) }

    val cyrillicRegex = remember { Regex("^[\\p{IsCyrillic}\\s]*\$") }
    val isInputValid by remember {
        derivedStateOf {
            val isNameCyrillic = cyrillicRegex.matches(fullNameInput)
            val fullNameWordsCount = fullNameInput.split(' ').size

            (isNameCyrillic && fullNameWordsCount == 3) && snilsNumberInput.length in 10..11
        }
    }

    Scaffold(
        topBar = {
            PTopBar(
                title = stringResource(R.string.verifier_data_title),
                enableBackButton = true,
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PTextField(
                value = fullNameInput,
                onValueChange = { fullNameInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                labelText = stringResource(R.string.full_name_text_field_label),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words
                )
            )
            Spacer(
                modifier = Modifier.height(42.dp)
            )
            PTextField(
                value = snilsNumberInput,
                onValueChange = { snilsNumberInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                labelText = stringResource(R.string.snils_number_label),
                maxLength = 11,
                visualTransformation = rememberMaskVisualTransformation(mask = "###-###-### ##"),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                )
            )
            Spacer(
                modifier = Modifier.height(76.dp)
            )
            FilledLoaderButton(
                modifier = Modifier.width(200.dp),
                label = stringResource(R.string.save_button),
                enabled = (isInputValid && !isButtonLoading),
                isLoading = isButtonLoading,
                onClick = {
                    focusManager.clearFocus()
                    onSubmit.invoke(VerifierData(fullNameInput, snilsNumberInput))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VerifierDataScreenPreview() {
    PoverkaTheme {
        Content(
            existingData = null,
            isButtonLoading = false,
            onBack = {},
            onSubmit = {}
        )
    }
}