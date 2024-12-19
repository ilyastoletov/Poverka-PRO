package com.poverka.pro.presentation.feature.shared

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.poverka.pro.R

@Composable
fun PhotoBox(
    modifier: Modifier = Modifier,
    photoBitmap: Bitmap?,
    saveButtonLabelResId: Int = R.string.save_button,
    enableSaveButton: Boolean = true,
    onClickCapture: () -> Unit,
    onClickRecapture: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
                .height(377.dp)
                .background(
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            if (photoBitmap != null) {
                Image(
                    bitmap = photoBitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
        Spacer(
            modifier = Modifier.height(30.dp)
        )
        Column(
            modifier = Modifier.width(200.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilledButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = enableSaveButton,
                label = stringResource(
                    id = if (photoBitmap == null) {
                        R.string.take_picture_button
                    } else {
                        saveButtonLabelResId
                    }
                ),
                onClick = onClickCapture
            )
            if (photoBitmap != null) {
                GhostButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.recapture_button),
                    onClick = onClickRecapture
                )
            }
        }
    }
}