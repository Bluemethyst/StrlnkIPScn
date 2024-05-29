package dev.bluemethyst.strlnkipscn

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.bluemethyst.strlnkipscn.gui.App

fun main() = application {
    val state = rememberWindowState(
        // https://stackoverflow.com/questions/78532146/how-can-i-change-the-default-window-dimensions-for-kotlin-compose-desktop
        width = 1280.dp,
        height = 720.dp,
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "KadvancedIpScanner",
        state = state,
        //icon = BitmapPainter(useResource("", ::loadImageBitmap)),
    ) {
        App()
    }
}