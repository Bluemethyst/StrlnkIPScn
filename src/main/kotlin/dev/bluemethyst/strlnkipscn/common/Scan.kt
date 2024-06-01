package dev.bluemethyst.strlnkipscn.common

import dev.bluemethyst.strlnkipscn.net.getDeviceName
import dev.bluemethyst.strlnkipscn.net.getMacAddress
import dev.bluemethyst.strlnkipscn.net.getManufacturer
import dev.bluemethyst.strlnkipscn.net.ping
import kotlinx.coroutines.*

data class Device(
    val ip: String,
    val name: String? = null,
    val mac: String? = null,
    val manufacturer: String? = null
)/*
@Deprecated("Use scanIpRange instead")
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
                ensureActive()
            }
        }
        jobs.awaitAll()
    }
    return devices
}*/

fun parseIpRanges(input: String): List<Pair<String, String>> {
    val ranges = input.split(", ")
    return ranges.map { range ->
        val ips = range.split("-")
        Pair(ips[0], ips[1])
    }
}

suspend fun scanIpRange(ipRange: Pair<String, String>, allDevices: MutableList<Device>): List<Device> {
    val devices = mutableListOf<Device>()
    val startIpParts = ipRange.first.split(".")
    val endIpParts = ipRange.second.split(".")
    val subnet = startIpParts.dropLast(1).joinToString(".")
    val startHost = startIpParts.last().toInt()
    val endHost = endIpParts.last().toInt()
    val range = startHost..endHost
    coroutineScope {
        val jobs = range.map { host ->
            async(Dispatchers.IO) {
                val ip = "$subnet.$host"
                if (ping(ip)) {
                    var name = getDeviceName(ip)
                    if (name == ip) {
                        name = null
                    }
                    val mac = getMacAddress(ip)
                    val manufacturer = mac?.let { getManufacturer(it) }
                    val device = Device(ip, name, mac, manufacturer)
                    devices.add(device)
                    allDevices.add(device)
                    println("Found device - IP: $ip, Name: $name, MAC: $mac, Manufacturer: $manufacturer")
                }
                ensureActive()
            }
        }
        jobs.awaitAll()
    }
    return devices
}