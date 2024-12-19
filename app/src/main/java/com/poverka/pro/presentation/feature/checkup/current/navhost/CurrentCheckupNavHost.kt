package com.poverka.pro.presentation.feature.checkup.current.navhost

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.poverka.pro.R
import com.poverka.pro.presentation.feature.checkup.current.navhost.graph.CurrentCheckupNavGraph
import com.poverka.pro.presentation.feature.checkup.current.navhost.graph.CurrentCheckupScreen
import com.poverka.pro.presentation.feature.checkup.current.ui.ClientInfoScreen
import com.poverka.pro.presentation.feature.checkup.current.ui.MeasurementScreen
import com.poverka.pro.presentation.feature.checkup.current.ui.MeterInfoScreen
import com.poverka.pro.presentation.feature.checkup.current.ui.components.CancelCheckupDialog
import com.poverka.pro.presentation.feature.shared.PTopBar
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.pop
import dev.olshevski.navigation.reimagined.rememberNavController

@Composable
fun CurrentCheckupNavHost(
    openMainScreen: () -> Unit
) {

    val navController = rememberNavController<CurrentCheckupScreen>(
        startDestination = CurrentCheckupNavGraph.Client()
    )

    var cancelCheckupDialogVisible by remember { mutableStateOf(false) }

    NavBackHandler(
        controller = navController
    )

    NavHost(
        modifier = Modifier.fillMaxSize(),
        controller = navController
    ) { route ->
        Scaffold(
            topBar = {
                if (route !is CurrentCheckupNavGraph.Measurement) {
                    PTopBar(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(
                            when (route) {
                                is CurrentCheckupNavGraph.Client -> R.string.client_info_title
                                is CurrentCheckupNavGraph.Meter -> R.string.meter_info_title
                                else -> R.string.measurement_title
                            }
                        ),
                        enableBackButton = route !is CurrentCheckupNavGraph.Client,
                        actions = {
                            IconButton(
                                onClick = { cancelCheckupDialogVisible = true }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                                    tint = Color.White,
                                    contentDescription = null
                                )
                            }
                        },
                        onBack = { navController.pop() }
                    )
                }
            }
        ) { scaffoldPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
            ) {
                when(route) {

                    is CurrentCheckupNavGraph.Client -> {
                        ClientInfoScreen(
                            viewModel = hiltViewModel(),
                            protocolId = route.fromProtocol,
                            openMeterInfoScreen = {
                                navController.navigate(CurrentCheckupNavGraph.Meter)
                            }
                        )
                    }

                    is CurrentCheckupNavGraph.Meter -> {
                        MeterInfoScreen(
                            viewModel = hiltViewModel(),
                            openMeasurementScreen = {
                                navController.navigate(
                                    CurrentCheckupNavGraph.Measurement(
                                        number = 1,
                                        isFinal = false
                                    )
                                )
                            }
                        )
                    }

                    is CurrentCheckupNavGraph.Measurement -> {
                        MeasurementScreen(
                            viewModel = hiltViewModel(),
                            number = route.number,
                            isFinalMeasurement = route.isFinal,
                            onCancelCheckup = { cancelCheckupDialogVisible = true },
                            openClientScreen = { protocolId ->
                                navController.navigate(
                                    CurrentCheckupNavGraph.Client(protocolId)
                                )
                            },
                            openMainScreen = openMainScreen,
                            onBack = { navController.pop() },
                            openNextMeasurementScreen = {
                                val nextMeasurement = route.number + 1
                                navController.navigate(
                                    CurrentCheckupNavGraph.Measurement(
                                        number = nextMeasurement,
                                        isFinal = nextMeasurement == 3
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    if (cancelCheckupDialogVisible) {
        CancelCheckupDialog(
            onDismiss = { cancelCheckupDialogVisible = false },
            onConfirm = {}
        )
    }

}