#!/usr/bin/env kscript

//DEPS com.github.holgerbrandl:kscript-annotations:1.2
//DEPS com.xenomachina:kotlin-argparser:2.0.7

//INCLUDE Scanner.kt

println("Starting scan...")

val results = scan()

println("Hosts which are up: ${results.map { it.ipAddress }}")

println("Done scanning.")
