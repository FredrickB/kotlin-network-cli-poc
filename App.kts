#!/usr/bin/env kscript

import kotlinx.coroutines.runBlocking

//DEPS org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8

//INCLUDE Scanner.kt

println("Starting scan...")

val results = runBlocking {
    scan()
}

println("Hosts which are up: ${results.map { it.ipAddress }}")

println("Done scanning")
