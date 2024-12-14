package com.poverka.pro.presentation.feature.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poverka.pro.presentation.feature.shared.text.LabelText
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
        LabelText(
            text = label
        )
    }
}

@Composable
fun FilledLoaderButton(
    modifier: Modifier = Modifier,
    label: String,
    isLoading: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    PButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            LabelText(
                text = label
            )
        }
    }
}

@Composable
fun GhostButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    PButton(
        modifier = modifier,
        enabled = enabled,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.secondary,
        onClick = onClick
    ) {
        LabelText(text = label)
    }
}

@Composable
fun PButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        content = content,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ButtonsPreview() {
    PoverkaTheme {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            FilledButton(
                label = "ТЕСТОВЫЙ ТЕКСТ",
                onClick = {}
            )
            FilledLoaderButton(
                label = "ТЕСТОВЫЙ ТЕКСТ",
                isLoading = true,
                onClick = {}
            )
            GhostButton(
                label = "GHOST",
                onClick = {}
            )
        }
    }
}