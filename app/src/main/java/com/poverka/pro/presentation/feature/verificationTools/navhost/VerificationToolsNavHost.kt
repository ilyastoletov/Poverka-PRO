package com.poverka.pro.presentation.feature.verificationTools.navhost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.shared.LoadingScreen
import com.poverka.pro.presentation.feature.shared.PTopBar
import com.poverka.pro.presentation.feature.verificationTools.model.Stage
import com.poverka.pro.presentation.feature.verificationTools.navhost.graph.ToolsScreen
import com.poverka.pro.presentation.feature.verificationTools.navhost.graph.VerificationToolsNavGraph
import com.poverka.pro.presentation.feature.verificationTools.ui.ReferenceDataScreen
import com.poverka.pro.presentation.feature.verificationTools.ui.ToolPhotoScreen
import com.poverka.pro.presentation.feature.verificationTools.viewmodel.VerificationToolsSharedViewModel
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.pop
import dev.olshevski.navigation.reimagined.rememberNavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun VerificationToolsNavHost(
    onBackToHomeScreen: () -> Unit
) {

    val sharedViewModel = hiltViewModel<VerificationToolsSharedViewModel>()

    val overlayLoadingEnabled by sharedViewModel.overlayLoadingEnabled.collectAsState()

    LaunchedEffect(Unit) {
        sharedViewModel.openHomeScreen.collectLatest { openHome ->
            if (openHome) onBackToHomeScreen()
        }
    }

    val navController = rememberNavController<ToolsScreen>(
        startDestination = VerificationToolsNavGraph.ReferenceData
    )

    NavBackHandler(
        controller = navController
    )

    Scaffold(
        topBar = {
            PTopBar(
                title = stringResource(R.string.verification_tools_title),
                enableBackButton = true,
                onBack = {
                    if (navController.backstack.entries.size == 1) {
                        onBackToHomeScreen()
                    } else {
                        navController.pop()
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        NavHost(
            controller = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) { route ->
            when(route) {

                is VerificationToolsNavGraph.ReferenceData -> {
                    ReferenceDataScreen(
                        sharedViewModel = sharedViewModel,
                        openReferencePhotoStage = {
                            navController.navigate(
                                VerificationToolsNavGraph.ToolPhoto(Stage.Reference)
                            )
                        },
                    )
                }

                is VerificationToolsNavGraph.ToolPhoto -> {
                    ToolPhotoScreen(
                        stage = route.stage,
                        sharedViewModel = sharedViewModel,
                        openNextStage = {
                            route.stage.next?.let { nextStage ->
                                navController.navigate(
                                    VerificationToolsNavGraph.ToolPhoto(nextStage)
                                )
                            }
                        }
                    )
                }

            }
        }
    }

    if (overlayLoadingEnabled) {
        LoadingScreen(
            overlayAlpha = 0.65F
        )
    }


}