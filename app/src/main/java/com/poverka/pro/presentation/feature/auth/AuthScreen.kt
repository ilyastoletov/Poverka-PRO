package com.poverka.pro.presentation.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poverka.domain.feature.auth.model.Credentials
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.auth.components.PasswordTextField
import com.poverka.pro.presentation.feature.shared.FilledButton
import com.poverka.pro.presentation.feature.shared.PButton
import com.poverka.pro.presentation.feature.shared.PTextField
import com.poverka.pro.presentation.feature.shared.PTopBar
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun AuthScreen() {

    Content(
        onLogin = {}
    )

}

@Composable
private fun Content(
    onLogin: (Credentials) -> Unit
) {
    var loginInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    val loginButtonEnabled by remember {
        derivedStateOf {
            loginInput.isNotEmpty() && passwordInput.isNotEmpty()
        }
    }

    Scaffold(
        topBar = {
            PTopBar(
                title = stringResource(R.string.auth_screen_title),
                enableBackButton = false
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PTextField(
                value = loginInput,
                onValueChange = { loginInput = it },
                labelText = stringResource(R.string.login_text_field_label),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            )
            Spacer(
                modifier = Modifier.height(30.dp)
            )
            PasswordTextField(
                value = passwordInput,
                onValueChange = { passwordInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            )
            Spacer(
                modifier = Modifier.height(60.dp)
            )
            FilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                enabled = loginButtonEnabled,
                label = stringResource(R.string.login_button),
                onClick = {
                    onLogin.invoke(Credentials(loginInput, passwordInput))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    PoverkaTheme {
        Content(
            onLogin = {}
        )
    }
}