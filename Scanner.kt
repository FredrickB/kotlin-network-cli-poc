//INCLUDE ScanResult.kt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import java.net.InetAddress
import java.net.NetworkInterface
import java.time.LocalDateTime

fun ping(device: String): Boolean {
    val address = InetAddress.getByName(device)
    return try {
        address.isReachable(2000)
    } catch (e: IOException) {
        println("Failed: $e")
        false
    }
}

suspend fun ping(addresses: List<String>): List<ScanResult> = coroutineScope {
    val res = addresses.map { ipAddress ->
        println("Pinging device $ipAddress...")
        val deferred = async(Dispatchers.IO) {
            ScanResult(ipAddress = ipAddress, up = ping(ipAddress), scannedAt = LocalDateTime.now())
        }
        println("Done pinging device.")
        deferred
    }

    res.map { it.await() }
}

suspend fun pingNetwork(broadcastAddress: String): List<ScanResult> {
    val endIpAddress = broadcastAddress.substring(broadcastAddress.length - 3, broadcastAddress.length)
    val ipStartRange = broadcastAddress.substring(0, broadcastAddress.length - 3)

    println("Pinging network with broadcast: $broadcastAddress")

    val addresses = IntRange(start = 1, endInclusive = endIpAddress.toInt()).map { "$ipStartRange$it" }

    return ping(addresses)
}

fun findBroadcastAddresses() = NetworkInterface.getNetworkInterfaces().toList()
        .filterNotNull()
        .filter { !it.isLoopback && it.isUp }
        .flatMap { it.interfaceAddresses }
        .mapNotNull { it.broadcast }
        .map { it.hostAddress }
        .toList()

suspend fun scan(): List<ScanResult> {
    val broadcastAddressesToScan = findBroadcastAddresses()

    println("broadcastAddressesToScan: $broadcastAddressesToScan")

    return broadcastAddressesToScan.flatMap { pingNetwork(it) }.filter { it.up }
}
