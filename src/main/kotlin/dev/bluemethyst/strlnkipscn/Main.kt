package dev.bluemethyst.strlnkipscn

import dev.bluemethyst.strlnkipscn.common.Device
import dev.bluemethyst.strlnkipscn.common.scanSubnet
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = runBlocking {
    val parser = ArgParser("strlnkipscn")
    val lowestSubnet by parser.option(ArgType.Int, shortName = "sl", description = "Lowest subnet").default(1)
    val highestSubnet by parser.option(ArgType.Int, shortName = "sh", description = "Highest subnet").default(1)
    val lowestRange by parser.option(ArgType.Int, shortName = "rl", description = "Lowest range").default(1)
    val highestRange by parser.option(ArgType.Int, shortName = "rh", description = "Highest range").default(254)

    parser.parse(args)

    val subnetRange = lowestSubnet..highestSubnet
    val range = lowestRange..highestRange
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