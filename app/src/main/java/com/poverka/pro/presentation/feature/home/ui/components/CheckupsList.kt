package com.poverka.pro.presentation.feature.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.poverka.domain.feature.checkup.model.Checkup
import com.poverka.domain.util.Mock
import com.poverka.pro.R
import com.poverka.pro.presentation.theme.PoverkaTheme

@Composable
fun CheckupsList(
    checkups: List<Checkup>,
    onClickCheckupItem: (Checkup) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        items(checkups) { item ->
            CheckupItem(
                date = item.date,
                protocolId = item.protocolId,
                onClick = { onClickCheckupItem.invoke(item) }
            )
        }

    }
}

@Composable
private fun CheckupItem(
    date: String,
    protocolId: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            )
            .clickable(onClick = onClick)
    ) {
        Text(
            text = stringResource(R.string.checkup_date, date),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            text = stringResource(R.string.checkup_protocol_id, protocolId),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckupsListPreview() {
    PoverkaTheme {
        CheckupsList(
            checkups = Mock.demoCheckups,
            onClickCheckupItem = {}
        )
    }
}