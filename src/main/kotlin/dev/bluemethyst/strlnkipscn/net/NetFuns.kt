package dev.bluemethyst.strlnkipscn.net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.net.*

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

fun getMacAddress(ip: String): String? {
    try {
        val address = InetAddress.getByName(ip)
        val networkInterface = NetworkInterface.getByInetAddress(address) ?: return null
        val macBytes = networkInterface.hardwareAddress ?: return null

        val builder = StringBuilder()
        for (b in macBytes) {
            builder.append(String.format("%02X:", b))
        }

        if (builder.isNotEmpty()) {
            builder.deleteCharAt(builder.length - 1)
        }

        return builder.toString()
    } catch (ex: UnknownHostException) {
        return null
    } catch (ex: SocketException) {
        return null
    }
}

fun getDeviceName(ip: String): String? {
    return try {
        val address = InetAddress.getByName(ip)
        address.hostName
    } catch (e: Exception) {
        null
    }
}

fun getManufacturer(mac: String): String? {
    val url = URL("https://api.macvendors.com/$mac")

    with(url.openConnection() as HttpURLConnection) {
        requestMethod = "GET"

        try {
            val reader = InputStreamReader(inputStream)
            return reader.readText()
        } catch (ex: Exception) {
            return null
        }
    }
}