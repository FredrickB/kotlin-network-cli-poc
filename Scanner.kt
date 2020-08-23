//INCLUDE ScanResult.kt

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import java.net.InetAddress
import java.net.NetworkInterface
import java.time.LocalDateTime

fun ping(ipAddress: String): Boolean {
    val address = InetAddress.getByName(ipAddress)
    return try {
        address.isReachable(2000)
    } catch (e: IOException) {
        println("Could not reach IP address: $ipAddress. Reason: $e")
        false
    }
}

suspend fun ping(addresses: List<String>): List<ScanResult> = coroutineScope {
    val res = addresses.map { ipAddress ->
        println("Pinging IP address: $ipAddress...")
        val deferred = async(Dispatchers.IO) {
            ScanResult(ipAddress = ipAddress, up = ping(ipAddress), scannedAt = LocalDateTime.now())
        }
        println("Done")
        deferred
    }

    res.map { it.await() }
}

suspend fun pingNetwork(broadcastAddress: String): List<ScanResult> {
    val endIpAddress = broadcastAddress.substring(broadcastAddress.length - 3, broadcastAddress.length)
    val ipStartRange = broadcastAddress.substring(0, broadcastAddress.length - 3)

    println("Pinging network with broadcast address: $broadcastAddress")

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

    println("Addresses to ping: $broadcastAddressesToScan")

    return broadcastAddressesToScan.flatMap { pingNetwork(it) }.filter { it.up }
}
