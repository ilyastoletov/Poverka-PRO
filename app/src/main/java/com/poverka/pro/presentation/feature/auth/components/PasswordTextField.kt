package com.poverka.pro.presentation.feature.auth.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.enableLiveLiterals
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.shared.PTextField
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var enableRevealButton by remember { mutableStateOf(false) }
    var isRevealed by remember { mutableStateOf(false) }

    val visualTransformation = remember(isRevealed) {
        if (isRevealed) VisualTransformation.None else PasswordVisualTransformation()
    }

    PTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .onFocusChanged { enableRevealButton = it.isFocused },
        visualTransformation = visualTransformation,
        labelText = stringResource(R.string.password_text_field_label),
        trailing = {
            if (enableRevealButton) {
                IconButton(
                    onClick = { isRevealed = !isRevealed }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_eye),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PasswordTextFieldPreview() {
    PoverkaTheme {
        PasswordTextField(
            value = "some pass",
            onValueChange = {},
            modifier = Modifier.padding(12.dp)
        )
    }
}