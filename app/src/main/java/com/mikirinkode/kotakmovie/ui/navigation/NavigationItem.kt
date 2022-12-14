package com.mikirinkode.kotakmovie.ui.navigation

import androidx.compose.ui.graphics.painter.Painter

data class NavigationItem(
    val title: String,
    val iconSelected: Painter,
    val iconUnselected: Painter,
    val screen: Screen,
)
