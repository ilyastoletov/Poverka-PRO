package com.poverka.pro.presentation.feature.checkup.current.navhost.graph

import kotlinx.parcelize.Parcelize

object CurrentCheckupNavGraph {

    @Parcelize
    data class Client(val fromProtocol: String? = null) : CurrentCheckupScreen

    @Parcelize
    data object Meter : CurrentCheckupScreen

    @Parcelize
    data class Measurement(
        val number: Int,
        val isFinal: Boolean
    ) : CurrentCheckupScreen

}