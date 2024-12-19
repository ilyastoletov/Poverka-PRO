package com.poverka.pro.presentation.feature.shared.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.poverka.domain.util.DateFormat
import com.poverka.pro.R
import com.poverka.pro.presentation.theme.PoverkaTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun DatePickerTextField(
    modifier: Modifier = Modifier,
    label: String,
    date: String,
    onSaveDate: (String) -> Unit
) {
    var chooseDateDialogVisible by remember { mutableStateOf(false) }

    PTextField(
        value = date,
        labelText = label,
        onValueChange = {},
        readOnly = true,
        trailing = {
            IconButton(
                onClick = { chooseDateDialogVisible = true }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_calendar),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    )

    if (chooseDateDialogVisible) {
        PDatePickerDialog(
            dateToSet = date,
            onSelectDate = onSaveDate,
            onDismiss = { chooseDateDialogVisible = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PDatePickerDialog(
    dateToSet: String,
    onSelectDate: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val dateToSetMillis = remember {
        val instant = if (dateToSet.isNotEmpty()) {
            LocalDate
                .parse(dateToSet, DateFormat.DD_MM_YYYY)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
        } else {
            Instant.now()
        }
        instant.toEpochMilli()
    }
    val state = rememberDatePickerState(
        initialSelectedDateMillis = dateToSetMillis
    )

    DatePickerDialog(
        modifier = Modifier.fillMaxWidth(0.9f),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                enabled = state.selectedDateMillis != null,
                onClick = {
                    state.selectedDateMillis?.let { selectedDate ->
                        val instant = Instant.ofEpochMilli(selectedDate)
                        val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
                        val formatted = DateFormat.DD_MM_YYYY.format(localDate)
                        onSelectDate(formatted)
                        onDismiss()
                    }
                },
            ) {
                Text(
                    text = stringResource(R.string.ok_button),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = stringResource(R.string.dismiss_button),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    ) {
        DatePicker(
            state = state,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = MaterialTheme.colorScheme.secondary,
                todayContentColor = Color.Black,
                selectedDayContentColor = Color.White,
                selectedYearContainerColor = MaterialTheme.colorScheme.secondary,
                currentYearContentColor = Color.Black,
                selectedYearContentColor = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DatePickerTextFieldPreview() {
    PoverkaTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var dateValue by remember { mutableStateOf("") }

            DatePickerTextField(
                modifier = Modifier.padding(horizontal = 15.dp),
                label = "Test",
                date = dateValue,
                onSaveDate = { dateValue = it }
            )
        }
    }
}