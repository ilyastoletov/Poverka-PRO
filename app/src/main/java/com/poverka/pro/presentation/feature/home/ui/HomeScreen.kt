package com.poverka.pro.presentation.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.home.ui.components.CheckupsList
import com.poverka.pro.presentation.feature.home.viewmodel.HomeContract
import com.poverka.pro.presentation.feature.home.viewmodel.HomeViewModel
import com.poverka.pro.presentation.feature.shared.LoadingScreen
import com.poverka.pro.presentation.feature.shared.PTopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    openCheckupDetailsScreen: (id: String) -> Unit,
    openVerifierDataScreen: () -> Unit,
    openVerificationToolsScreen: () -> Unit,
    openEnvironmentalDataScreen: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val enableEnvironmentalDataItem by viewModel.enableEnvironmentDataButton.collectAsState()
    val enableVerificationToolsItem by viewModel.enableVerificationToolsButton.collectAsState()

    val checkups by viewModel.checkups.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                enableEnvironmentalDataItem = enableEnvironmentalDataItem,
                enableVerificationToolsItem = enableVerificationToolsItem,
                onClickVerifier = openVerifierDataScreen,
                onClickToolsMenuItem = openVerificationToolsScreen,
                onClickEnvironmentData = openEnvironmentalDataScreen,
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            when(state) {

                is HomeContract.State.Loading -> LoadingScreen()

                is HomeContract.State.Loaded -> {
                    PullRefreshWrapper(
                        onRefresh = { viewModel.handleEvent(HomeContract.Event.RefreshData) }
                    ) {
                        CheckupsList(
                            checkups = checkups,
                            onClickCheckupItem = { openCheckupDetailsScreen(it.protocolId) }
                        )
                    }
                }

            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PullRefreshWrapper(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    var refreshing by remember { mutableStateOf(false) }
    val state = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            onRefresh()
            scope.launch {
                refreshing = true
                delay(1500L)
                refreshing = false
            }
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state)
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

@Composable
private fun TopBar(
    enableVerificationToolsItem: Boolean,
    enableEnvironmentalDataItem: Boolean,
    onClickToolsMenuItem: () -> Unit,
    onClickVerifier: () -> Unit,
    onClickEnvironmentData: () -> Unit,
) {
    var actionsMenuExpanded by remember { mutableStateOf(false) }

    PTopBar(
        title = stringResource(R.string.home_title),
        enableBackButton = false,
        actions = {
            Box {
                IconButton(
                    onClick = { actionsMenuExpanded = !actionsMenuExpanded }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_more),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }
                DropdownMenu(
                    expanded = actionsMenuExpanded,
                    onDismissRequest = { actionsMenuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.verifier_menu_item)
                            )
                        },
                        onClick = onClickVerifier
                    )
                    if (enableVerificationToolsItem) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.verification_tools_menu_item)
                                )
                            },
                            onClick = onClickToolsMenuItem
                        )
                    }
                    if (enableEnvironmentalDataItem) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.environmental_data_menu_item)
                                )
                            },
                            onClick = onClickEnvironmentData
                        )
                    }
                }
            }
        }
    )
}
