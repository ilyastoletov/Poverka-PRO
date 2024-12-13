package com.poverka.pro.presentation.feature.navigation.screen

object NavGraph {

    data object Splash : Screen

    data object Authorization : Screen

    data object Home : Screen

    data object VerifierData : Screen

    data object VerificationTools : Screen

    data object EnvironmentData : Screen

    data class CheckupDetails(val protocolId: String) : Screen

    data object CurrentCheckup : Screen

}