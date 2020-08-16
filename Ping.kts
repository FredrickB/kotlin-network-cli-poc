#!/usr/bin/env kscript

//DEPS com.xenomachina:kotlin-argparser:2.0.7
//DEPS org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8

//INCLUDE Scanner.kt

println("Starting scan...")

val results = scan()

println("Hosts which are up: ${results.map { it.ipAddress }}")

println("Done scanning")
