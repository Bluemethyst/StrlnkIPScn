package dev.bluemethyst.strlnkipscn

import kotlinx.coroutines.launch
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.bluemethyst.strlnkipscn.common.Device
import dev.bluemethyst.strlnkipscn.common.scanSubnet

val DarkColorPalette = darkColorScheme(
    background = Color(0xFF121212),
    primary = Color(0xFFBB86FC),
    secondary = Color(0xFF03DAC6)
)

val LightColorPalette = lightColorScheme(
    background = Color(0xFFFFFFFF),
    primary = Color(0xFF1FAA59),
    secondary = Color(0xFFB2FF59)
)


@Composable
@Preview
fun App() {
    val colorScheme = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette
    MaterialTheme(colorScheme = colorScheme) {
        var subnetMax by remember { mutableStateOf(TextFieldValue()) }
        var subnetMin by remember { mutableStateOf(TextFieldValue()) }
        var ipRangeMax by remember { mutableStateOf(TextFieldValue()) }
        var ipRangeMin by remember { mutableStateOf(TextFieldValue()) }
        val allDevices = remember { mutableStateListOf<Device>() }
        val coroutineScope = rememberCoroutineScope()
        Box(modifier = Modifier.fillMaxSize().background(colorScheme.background)) {
            Column(modifier = Modifier.fillMaxSize().background(colorScheme.background)) {
                Row {
                    Column {
                        TextField(
                            value = subnetMin,
                            onValueChange = {
                                if (it.text.all { char -> char.isDigit() }) {
                                    subnetMin = it
                                }
                            },
                            placeholder = { Text("Subnet Min") },
                            singleLine = true
                        )
                    }
                    Column {
                        TextField(
                            value = subnetMax,
                            onValueChange = {
                                if (it.text.all { char -> char.isDigit() }) {
                                    subnetMax = it
                                }
                            },
                            placeholder = { Text("Subnet Max") },
                            singleLine = true
                        )
                    }
                    Column {
                        TextField(
                            value = ipRangeMax,
                            onValueChange = {
                                if (it.text.all { char -> char.isDigit() }) {
                                    ipRangeMax = it
                                }
                            },
                            placeholder = { Text("IP Range Max") },
                            singleLine = true
                        )
                    }
                    Column {
                        TextField(
                            value = ipRangeMin,
                            onValueChange = {
                                if (it.text.all { char -> char.isDigit() }) {
                                    ipRangeMin = it
                                }
                            },
                            placeholder = { Text("IP Range Min") },
                            singleLine = true
                        )
                    }
                    var subnetRange: IntRange
                    var range: IntRange
                    try { // change this to check each condition separately
                        subnetRange = if (subnetMin.text.isNotEmpty() && subnetMax.text.isNotEmpty()) {
                            subnetMin.text.toInt()..subnetMax.text.toInt()
                        } else {
                            99..99
                        }
                        range = if (ipRangeMin.text.isNotEmpty() && ipRangeMax.text.isNotEmpty()) {
                            ipRangeMin.text.toInt()..ipRangeMax.text.toInt()
                        } else {
                            1..254
                        }
                    } catch (e: NumberFormatException) {
                        println(e)
                        println("Invalid input")
                        subnetRange = 99..99
                        range = 1..254
                    }
                    Button(onClick = {
                        allDevices.clear()
                        coroutineScope.launch {
                            for (subnet in subnetRange) {
                                val subnetString = "192.168.$subnet"
                                println("Scanning subnet $subnetString...")
                                val devices = scanSubnet(subnetString, range, allDevices)
                                println("Devices found: $devices")
                            }
                        }
                    }) {
                        Text("Scan Network")
                    }

                }
                Row {
                    Column {
                        for (device in allDevices) {
                            Text("IP: ${device.ip}, Name: ${device.name}", color = colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}


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
        //icon = BitmapPainter(useResource("assets/koquestor.ico", ::loadImageBitmap)),
    ) {
        App()
    }
}