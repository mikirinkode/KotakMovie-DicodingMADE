package com.mikirinkode.kotakmovie.ui.navigation

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val iconSelected: Painter,
    val iconUnselected: Painter,
    val screen: Screen,
)
