package com.poverka.pro.presentation.feature.shared.text

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun PTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    labelText: String = "",
    trailing: @Composable () -> Unit = {},
    maxLength: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
        modifier = modifier,
        label = {
            Text(
                text = labelText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = trailing,
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedContainerColor = MaterialTheme.colorScheme.outlineVariant,
            errorContainerColor = MaterialTheme.colorScheme.outlineVariant,
            disabledContainerColor = MaterialTheme.colorScheme.outlineVariant,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PTextFieldPreview() {
    PoverkaTheme {
        var input by remember { mutableStateOf("Input") }

        PTextField(
            modifier = Modifier.padding(12.dp),
            value = input,
            onValueChange = { input = it },
            trailing = {},
            labelText = "Test text field"
        )
    }
}