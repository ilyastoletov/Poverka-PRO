package com.poverka.pro.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.poverka.pro.presentation.feature.auth.ui.AuthScreen
import com.poverka.pro.presentation.feature.home.ui.HomeScreen
import com.poverka.pro.presentation.feature.navigation.bottombar.BottomNavigationBar
import com.poverka.pro.presentation.feature.navigation.screen.NavGraph
import com.poverka.pro.presentation.feature.navigation.screen.Screen
import com.poverka.pro.presentation.feature.snackbar.host.SnackbarHolder
import com.poverka.pro.presentation.theme.PoverkaTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.navigate
import dev.olshevski.navigation.reimagined.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var snackbarHolder: SnackbarHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoverkaTheme {

                val navController = rememberNavController<Screen>(
                    startDestination = NavGraph.Authorization
                )

                val snackbarHostState = remember { SnackbarHostState() }

                // TODO: Test snackbar
                LaunchedEffect(Unit) {
                    snackbarHolder.snackbarMessage.collectLatest { message ->
                        message?.let { snackbarHostState.showSnackbar(it) }
                    }
                }

                NavBackHandler(navController)

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    bottomBar = {
                        val currentDestination by rememberUpdatedState(
                            newValue = navController.backstack.entries.lastOrNull()?.destination
                                ?: NavGraph.Splash
                        )
                        val enableNavigationBar = remember(currentDestination) {
                            currentDestination is NavGraph.Home
                                    || currentDestination is NavGraph.CurrentCheckup
                        }

                        if (enableNavigationBar) {
                            BottomNavigationBar(
                                currentDestination = currentDestination,
                                onOpenHomeScreen = { navController.navigate(NavGraph.Home) },
                                onOpenCurrentCheckupScreen = {
                                    navController.navigate(NavGraph.CurrentCheckup)
                                }
                            )
                        }
                    }
                ) { scaffoldPadding ->
                    NavHost(
                        controller = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(scaffoldPadding)
                    ) { destination ->
                        when(destination) {

                            is NavGraph.Splash -> {}

                            is NavGraph.Authorization -> {
                                AuthScreen(
                                    viewModel = hiltViewModel(),
                                    openHomeScreen = { navController.navigate(NavGraph.Home) },
                                )
                            }

                            is NavGraph.Home -> {
                                HomeScreen(
                                    viewModel = hiltViewModel(),
                                    openCheckupDetailsScreen = { id ->
                                        navController.navigate(NavGraph.CheckupDetails(id))
                                    },
                                    openVerifierDataScreen = {
                                        navController.navigate(NavGraph.VerifierData)
                                    },
                                    openVerificationToolsScreen = {
                                        navController.navigate(NavGraph.VerificationTools)
                                    },
                                    openEnvironmentalDataScreen = {
                                        navController.navigate(NavGraph.EnvironmentData)
                                    }
                                )
                            }

                            is NavGraph.CheckupDetails -> {}
                            is NavGraph.CurrentCheckup -> {}
                            is NavGraph.EnvironmentData -> {}
                            is NavGraph.VerificationTools -> {}
                            is NavGraph.VerifierData -> {}
                        }
                    }
                }


            }
        }
    }

}
