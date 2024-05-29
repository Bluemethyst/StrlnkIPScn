package dev.bluemethyst.strlnkipscn

import kotlinx.coroutines.launch
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.bluemethyst.strlnkipscn.common.Device
import dev.bluemethyst.strlnkipscn.common.scanSubnet
import dev.bluemethyst.strlnkipscn.gui.*


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
                        TextField(value = subnetMin, onValueChange = {
                            if (it.text.all { char -> char.isDigit() }) {
                                subnetMin = it
                            }
                        }, label = { Text("Subnet Min") }, singleLine = true
                        )
                    }
                    Column {
                        TextField(value = subnetMax, onValueChange = {
                            if (it.text.all { char -> char.isDigit() }) {
                                subnetMax = it
                            }
                        }, label = { Text("Subnet Max") }, singleLine = true
                        )
                    }
                    Column {
                        TextField(value = ipRangeMin, onValueChange = {
                            if (it.text.all { char -> char.isDigit() }) {
                                ipRangeMin = it
                            }
                        }, label = { Text("IP Range Min") }, singleLine = true
                        )
                    }
                    Column {
                        TextField(value = ipRangeMax, onValueChange = {
                            if (it.text.all { char -> char.isDigit() }) {
                                ipRangeMax = it
                            }
                        }, label = { Text("IP Range Max") }, singleLine = true
                        )
                    }
                    var subnetRange: IntRange
                    var snMin = 1
                    var snMax = 1
                    var irMin = 1
                    var irMax = 254
                    var range: IntRange
                    try {
                        if (subnetMin.text.isNotEmpty()) {
                            snMin = subnetMin.text.toInt()
                        }
                        if (subnetMax.text.isNotEmpty()) {
                            snMax = subnetMax.text.toInt()
                        }
                        subnetRange = snMin..snMax
                        if (ipRangeMin.text.isNotEmpty()) {
                            irMin = ipRangeMin.text.toInt()
                        }
                        if (ipRangeMax.text.isNotEmpty()) {
                            irMax = ipRangeMax.text.toInt()
                        }
                        range = irMin..irMax
                    } catch (e: NumberFormatException) {
                        println(e)
                        subnetRange = 1..1
                        range = 1..254
                    }
                    var scanning: String by remember { mutableStateOf("Scan Network") }
                    var enabled: Boolean by remember { mutableStateOf(true) }
                    Button(onClick = {
                        allDevices.clear()
                        coroutineScope.launch {
                            scanning = "Scanning..."
                            enabled = false
                            for (subnet in subnetRange) {
                                val subnetString = "192.168.$subnet"
                                println("Scanning subnet $subnetString...")
                                val devices = scanSubnet(subnetString, range, allDevices)
                                scanning = "Scan Network"
                                enabled = true
                            }
                        }
                    }, enabled = enabled) {
                        Text(scanning)
                    }
                }
                Row {
                    Column {
                        val seenDevices = mutableSetOf<Device>()
                        for (device in allDevices) {
                            if (device in seenDevices) {
                                allDevices.remove(device)
                            } else {
                                seenDevices.add(device)
                            }
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
        //icon = BitmapPainter(useResource("", ::loadImageBitmap)),
    ) {
        App()
    }
}