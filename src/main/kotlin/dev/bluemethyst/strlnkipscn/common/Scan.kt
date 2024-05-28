package dev.bluemethyst.strlnkipscn.common

import dev.bluemethyst.strlnkipscn.net.getDeviceName
import dev.bluemethyst.strlnkipscn.net.ping
import kotlinx.coroutines.*

data class Device(
    val ip: String,
    val name: String
)

suspend fun scanSubnet(subnet: String, range: IntRange, allDevices: MutableList<Device>): List<Device> {
    val devices = mutableListOf<Device>()
    coroutineScope {
        val jobs = range.map { host ->
            async(Dispatchers.IO) {
                val ip = "$subnet.$host"
                if (ping(ip)) {
                    val name = getDeviceName(ip)
                    val device = Device(ip, name)
                    devices.add(device)
                    allDevices.add(device)
                    println("Found device - IP: $ip, Name: $name")
                }
            }
        }
        jobs.awaitAll()
    }
    return devices
}