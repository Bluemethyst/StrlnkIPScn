package dev.bluemethyst.strlnkipscn.gui

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkColorPalette = darkColorScheme(
    background = Color(0xFF1E1E2E),
    primary = Color(0xFF181825),
    secondary = Color(0xFF11111B),
    onBackground = Color(0xFFCBA6F7),
    onPrimary = Color(0xFFCDD6F4),
    // https://github.com/catppuccin/catppuccin/blob/main/docs/style-guide.md
)

val LightColorPalette = lightColorScheme(
    background = Color(0xFFFFFFFF), primary = Color(0xFF1FAA59), secondary = Color(0xFFB2FF59)
)