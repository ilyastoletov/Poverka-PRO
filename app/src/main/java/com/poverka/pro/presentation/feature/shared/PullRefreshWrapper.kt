package com.poverka.pro.presentation.feature.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val FAKE_REFRESH_DELAY = 1500L

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshWrapper(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean? = null,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    var refreshing by remember { mutableStateOf(isRefreshing ?: false) }
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            onRefresh()
            if (isRefreshing == null) {
                scope.launch {
                    refreshing = true
                    delay(FAKE_REFRESH_DELAY)
                    refreshing = false
                }
            }
        }
    )

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        content.invoke()
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = colorScheme.secondary,
            backgroundColor = colorScheme.primary,
            refreshing = refreshing,
            state = state
        )
    }

}