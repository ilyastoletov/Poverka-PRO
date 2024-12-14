package com.poverka.pro.presentation.feature.shared

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.poverka.pro.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PTopBar(
    modifier: Modifier = Modifier,
    title: String,
    titleFontSize: TextUnit = 22.sp,
    enableBackButton: Boolean,
    onBack: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                fontSize = titleFontSize,
                color = Color.White,
                fontWeight = FontWeight.Normal
            )
        },
        navigationIcon = {
            if (enableBackButton) {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        )
    )
}