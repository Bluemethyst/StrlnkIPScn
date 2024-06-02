package dev.bluemethyst.strlnkipscn.gui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
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
        Box(
            modifier = Modifier.fillMaxSize().background(colorScheme.background).border(10.dp, colorScheme.primary)
                .padding(20.dp)
        ) {

            Column(modifier = Modifier.fillMaxSize().background(colorScheme.background)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        TextField(
                            value = ipRangesInput,
                            onValueChange = { ipRangesInput = it },
                            label = { Text("IP Ranges", color = colorScheme.onBackground) },
                            singleLine = true
                        )
                    }
                    var scanning: String by remember { mutableStateOf("Scan Network") }
                    var isEnabled: Boolean by remember { mutableStateOf(true) }
                    Button(
                        onClick = {
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
                        },
                        enabled = isEnabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primary,
                            disabledContainerColor = colorScheme.onPrimary
                        )
                    ) {
                        Text(scanning, color = colorScheme.onBackground)
                    }
                }
                Row {
                    Column(modifier = Modifier.width(200.dp)) {
                        Text("IP:", color = colorScheme.onBackground)
                        for (device in allDevices) {
                            Text(device.ip, color = colorScheme.onPrimary)
                        }
                    }
                    Column(modifier = Modifier.width(200.dp)) {
                        Text("Name:", color = colorScheme.onBackground)
                        for (device in allDevices) {
                            Text(device.name ?: " ", color = colorScheme.onPrimary)
                        }
                    }
                    Column(modifier = Modifier.width(200.dp)) {
                        Text("MAC:", color = colorScheme.onBackground)
                        for (device in allDevices) {
                            Text(device.mac ?: " ", color = colorScheme.onPrimary)
                        }
                    }
                    Column(modifier = Modifier.width(200.dp)) {
                        Text("Manufacturer:", color = colorScheme.onBackground)
                        for (device in allDevices) {
                            Text(device.manufacturer ?: " ", color = colorScheme.onPrimary)
                        }
                    }
                }
            }
        }
    }
}