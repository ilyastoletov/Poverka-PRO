package com.poverka.pro.presentation.feature.checkup.current.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.poverka.pro.R

@Composable
fun CancelCheckupDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Отменить поверку?"
            )
        },
        text = {
            Text(
                text = "Все внесенные данные будут удалены"
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(); onDismiss() }
            ) {
                Text(
                    text = stringResource(R.string.confirm_button),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Нет",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
    )
}