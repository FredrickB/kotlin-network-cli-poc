//INCLUDE ScanResult.kt

import kotlinx.coroutines.*
import java.net.InetAddress
import java.net.NetworkInterface
import java.time.LocalDateTime
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

fun ping(device: String): Boolean {
    val address = InetAddress.getByName(device)
    return address.isReachable(2000)
}

suspend fun test(addresses: List<String>): List<ScanResult> = coroutineScope {
    addresses.map { ipAddress ->
        println("Pinging device $ipAddress")
        val deferred = async(Dispatchers.IO) {
            ScanResult(ipAddress = ipAddress, up = ping(ipAddress), scannedAt = LocalDateTime.now())
        }
        println("Done pinging device")
        deferred
    }.awaitAll()
}

fun pingNetwork(broadcastAddress: String): List<ScanResult> = runBlocking {
    val endIpAddress = broadcastAddress.substring(broadcastAddress.length - 3, broadcastAddress.length)
    val ipStartRange = broadcastAddress.substring(0, broadcastAddress.length - 3)

    println("Pinging network with broadcast: $broadcastAddress")

//    val service = Executors.newCachedThreadPool()

    val addresses = IntRange(start = 1, endInclusive = endIpAddress.toInt()).map { "$ipStartRange$it" }

    println("Bam!")
    val results = test(addresses)
    println("results: $results")
//
//    runBlocking {
//        println("Bam!")
//        val results = test(addresses)
//        println("results: $results")
//    }

    emptyList<ScanResult>()
    // return emptyList<ScanResult>()
//
//    val results = IntRange(start = 1, endInclusive = endIpAddress.toInt())
//            .map { device ->
//                val ipAddress = "$ipStartRange$device"
//                Callable { ScanResult(ipAddress = ipAddress, up = ping(ipAddress), scannedAt = LocalDateTime.now()) }
//            }
//            .map { service.submit(it) }
//            .map { it.get() }
//
//    service.shutdown()

    // return results
}

fun findBroadcastAddresses() = NetworkInterface.getNetworkInterfaces().toList()
        .filterNotNull()
        .filter { !it.isLoopback && it.isUp }
        .flatMap { it.interfaceAddresses }
        .mapNotNull { it.broadcast }
        .map { it.hostAddress }
        .toList()

fun scan(): List<ScanResult> {
    val broadcastAddressesToScan = findBroadcastAddresses()

    println("broadcastAddressesToScan: $broadcastAddressesToScan")

    return broadcastAddressesToScan.flatMap { pingNetwork(it) }.filter { it.up }
}
