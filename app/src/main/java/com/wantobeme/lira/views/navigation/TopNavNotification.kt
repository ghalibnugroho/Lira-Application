package com.wantobeme.lira.views.Nagivation

import androidx.compose.ui.graphics.vector.ImageVector

data class TopNavNotification(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)