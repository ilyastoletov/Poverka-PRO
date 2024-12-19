package com.poverka.pro.presentation.feature.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onSelect
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioChecker(
            selected = selected,
            onSelect = onSelect
        )
        Spacer(
            modifier = Modifier.width(5.dp)
        )
        Text(
            text = text,
            fontSize = 16.sp,
            lineHeight = 32.sp,
            color = Color.Black
        )
    }
}

@Composable
fun RadioChecker(
    selected: Boolean,
    onSelect: () -> Unit
) {
    RadioButton(
        selected = selected,
        onClick = onSelect,
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.secondary,
            unselectedColor = MaterialTheme.colorScheme.secondary
        )
    )
}