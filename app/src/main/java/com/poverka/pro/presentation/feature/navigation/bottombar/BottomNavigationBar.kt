package com.poverka.pro.presentation.feature.navigation.bottombar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.sp
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.navigation.screen.NavGraph
import com.poverka.pro.presentation.feature.navigation.screen.Screen

@Composable
fun BottomNavigationBar(
    currentDestination: Screen,
    onOpenHomeScreen: () -> Unit,
    onOpenCurrentCheckupScreen: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.outlineVariant,
    ) {
        BottomBarItem(
            selected = (currentDestination is NavGraph.Home),
            iconRes = R.drawable.ic_folder,
            labelRes = R.string.home_title,
            onClick = onOpenHomeScreen
        )
        BottomBarItem(
            selected = (currentDestination is NavGraph.CurrentCheckup),
            iconRes = R.drawable.ic_add_circle,
            labelRes = R.string.current_checkup_title,
            onClick = onOpenCurrentCheckupScreen
        )
    }
}

@Composable
private fun RowScope.BottomBarItem(
    selected: Boolean,
    iconRes: Int,
    labelRes: Int,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(iconRes),
                contentDescription = null
            )
        },
        label = {
            Text(
                text = stringResource(labelRes),
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = Color(0xFF1D1B20),
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = MaterialTheme.colorScheme.secondary
        )
    )
}