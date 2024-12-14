package com.poverka.pro.presentation.feature.auth.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.shared.text.PTextField
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var isRevealed by remember { mutableStateOf(false) }

    val visualTransformation = remember(isRevealed) {
        if (isRevealed) VisualTransformation.None else PasswordVisualTransformation()
    }

    PTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        visualTransformation = visualTransformation,
        labelText = stringResource(R.string.password_text_field_label),
        trailing = {
            IconButton(
                onClick = { isRevealed = !isRevealed }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = if (isRevealed) R.drawable.ic_eye_open else R.drawable.ic_eye_closed
                    ),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                )
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