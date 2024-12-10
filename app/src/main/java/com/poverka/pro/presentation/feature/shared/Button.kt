package com.poverka.pro.presentation.feature.shared

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    PButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun PButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        content = content,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun FilledButtonPreview() {
    PoverkaTheme {
        FilledButton(
            modifier = Modifier.padding(12.dp),
            label = "ТЕСТОВЫЙ ТЕКСТ",
            onClick = {}
        )
    }
}