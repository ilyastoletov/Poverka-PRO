package com.poverka.pro.presentation.feature.navigation.screen

import kotlinx.parcelize.Parcelize

object NavGraph {

    @Parcelize
    data object Splash : Screen

    @Parcelize
    data object Authorization : Screen

    @Parcelize
    data object Home : Screen

    @Parcelize
    data object VerifierData : Screen

    @Parcelize
    data object VerificationTools : Screen

    @Parcelize
    data object EnvironmentData : Screen

    @Parcelize
    data class CheckupDetails(val protocolId: String) : Screen

    @Parcelize
    data object CurrentCheckup : Screen

}