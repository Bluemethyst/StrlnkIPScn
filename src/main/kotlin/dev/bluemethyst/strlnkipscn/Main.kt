package dev.bluemethyst.strlnkipscn

import dev.bluemethyst.strlnkipscn.common.Device
import dev.bluemethyst.strlnkipscn.common.scanSubnet
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val subnetRange = 1..100  // Example: Scanning subnets 192.168.1.* to 192.168.2.*
    val range = 1..254  // Scanning typical home network range
    val allDevices = mutableListOf<Device>()

    println("Scanning network...")

    val duration = measureTimeMillis {
        for (subnet in subnetRange) {
            val subnetString = "192.168.$subnet"
            println("Scanning subnet $subnetString...")
            val devices = scanSubnet(subnetString, range)
            allDevices.addAll(devices)
        }
    }

    println("Scan completed in $duration ms")
    println("Devices found:")
    allDevices.forEach { device ->
        println("IP: ${device.ip}, Name: ${device.name}")
    }
}