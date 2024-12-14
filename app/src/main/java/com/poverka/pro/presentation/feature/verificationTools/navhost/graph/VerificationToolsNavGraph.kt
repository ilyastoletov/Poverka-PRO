package com.poverka.pro.presentation.feature.verificationTools.navhost.graph

import com.poverka.pro.presentation.feature.verificationTools.model.Stage
import kotlinx.parcelize.Parcelize

object VerificationToolsNavGraph {

    @Parcelize
    data object ReferenceData : ToolsScreen

    @Parcelize
    data class ToolPhoto(val stage: Stage) : ToolsScreen

}