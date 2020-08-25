#!/usr/bin/env kscript

@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
@file:Include("Scanner.kt")

import kotlinx.coroutines.runBlocking

println("Starting scan...")

val results = runBlocking {
    scan()
}

println("Hosts which are up: ${results.map { it.ipAddress }}")

println("Done scanning")
