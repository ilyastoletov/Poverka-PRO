package com.poverka.pro.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = Color(0xFF52A6FF),
    onSecondary = Color.White,
    outlineVariant = Color(0x1A52A6FF),
    onSurfaceVariant = Color(0xFF49454F)
)

@Composable
fun PoverkaTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}