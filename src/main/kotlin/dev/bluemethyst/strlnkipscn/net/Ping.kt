package dev.bluemethyst.strlnkipscn.net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress

suspend fun ping(ip: String): Boolean {
    return try {
        val address = withContext(Dispatchers.IO) {
            InetAddress.getByName(ip)
        }
        withContext(Dispatchers.IO) {
            address.isReachable(1000)
        }  // Timeout in milliseconds
    } catch (e: Exception) {
        false
    }
}

fun getDeviceName(ip: String): String {
    return try {
        val address = InetAddress.getByName(ip)
        address.hostName
    } catch (e: Exception) {
        "Unknown"
    }
}
