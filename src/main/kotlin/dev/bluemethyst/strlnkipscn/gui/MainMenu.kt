package dev.bluemethyst.strlnkipscn.gui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import dev.bluemethyst.strlnkipscn.common.*
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    val colorScheme = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette
    MaterialTheme(colorScheme = colorScheme) {
        var ipRangesInput by remember { mutableStateOf(TextFieldValue("192.168.1.1-254, 192.168.2.1-254, 192.168.100.1-254")) }
        val allDevices = remember { mutableStateListOf<Device>() }
        val coroutineScope = rememberCoroutineScope()
        Box(modifier = Modifier.fillMaxSize().background(colorScheme.background)) {
            Column(modifier = Modifier.fillMaxSize().background(colorScheme.background)) {
                Row {
                    Column {
                        TextField(
                            value = ipRangesInput,
                            onValueChange = { ipRangesInput = it },
                            label = { Text("IP Ranges") },
                            singleLine = true,
                            // placeholder = { Text("192.168.1.1-254, 192.168.2.1-254, 192.168.100.1-254") },
                        )
                    }
                    var scanning: String by remember { mutableStateOf("Scan Network") }
                    var isEnabled: Boolean by remember { mutableStateOf(true) }
                    Button(onClick = {
                        allDevices.clear()
                        coroutineScope.launch {
                            scanning = "Scanning..."
                            isEnabled = false
                            val ipRanges = parseIpRanges(ipRangesInput.text)
                            for (ipRange in ipRanges) {
                                println("Scanning range ${ipRange.first} to ${ipRange.second}...")
                                val devices = scanIpRange(ipRange, allDevices)
                            }
                            scanning = "Scan Network"
                            isEnabled = true
                        }
                    }, enabled = isEnabled) {
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