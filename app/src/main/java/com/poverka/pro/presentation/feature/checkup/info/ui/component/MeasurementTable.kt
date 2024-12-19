package com.poverka.pro.presentation.feature.checkup.info.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poverka.domain.feature.checkup.model.Measurement
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun MeasurementDataTable(
    modifier: Modifier = Modifier,
    model: Measurement
) {

    val measurementData = remember {
        listOf(
            "Расход, м3/час" to model.consumption,
            "Объем по эталону СИ (Vэт), м3" to model.referenceConsumption,
            "Относительная погрешность (Δ), %" to model.relativeInaccuracy,
            "Время измерения, сек" to model.measurementTime
        )
    }

    Column(modifier) {
        repeat(measurementData.size) { index ->
            val item = remember { measurementData[index] }
            val isFirstItem = remember { index == 0 }

            val leftCellShape = remember {
                when {
                    isFirstItem -> RoundedCornerShape(topStart = 6.dp)
                    (index == measurementData.lastIndex) -> RoundedCornerShape(bottomStart = 4.dp)
                    else -> RectangleShape
                }
            }
            val rightCellShape = remember {
                when {
                    isFirstItem -> RoundedCornerShape(topEnd = 6.dp)
                    (index == measurementData.lastIndex) -> RoundedCornerShape(bottomEnd = 4.dp)
                    else -> RectangleShape
                }
            }

            Row {
                TableCell(
                    weight = 0.7f,
                    content = item.first,
                    boldText = isFirstItem,
                    shape = leftCellShape
                )
                TableCell(
                    weight = 0.3f,
                    content = item.second,
                    boldText = isFirstItem,
                    shape = rightCellShape
                )
            }
        }
    }

}

@Composable
private fun RowScope.TableCell(
    weight: Float,
    content: String,
    boldText: Boolean,
    shape: Shape
) {
    Box(
        modifier = Modifier
            .border(
                width = Dp.Hairline,
                color = Color.Black,
                shape = shape
            )
            .weight(weight)
            .padding(
                horizontal = 12.dp,
                vertical = 10.dp
            )
    ) {
        val fontWeight = remember {
            if (boldText) FontWeight.SemiBold else FontWeight.Normal
        }

        CellText(
            text = content,
            weight = fontWeight
        )
    }
}

@Composable
private fun CellText(
    text: String,
    weight: FontWeight
) {
    var fontSize by remember { mutableStateOf(12.sp) }

    Text(
        text = text,
        fontSize = fontSize,
        color = Color.Black,
        fontWeight = weight,
        maxLines = 1,
        onTextLayout = { layout ->
            if (layout.hasVisualOverflow) {
                fontSize *= 0.9
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun MeasurementTablePreview() {
    PoverkaTheme {
        MeasurementDataTable(
            modifier = Modifier.padding(12.dp),
            model = Measurement(
                consumption = "0,04",
                referenceConsumption = "0,039581",
                relativeInaccuracy = "0,09",
                measurementTime = "360"
            )
        )
    }
}