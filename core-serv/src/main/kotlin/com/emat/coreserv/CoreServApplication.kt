package com.emat.coreserv

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoreServApplication

fun main(args: Array<String>) {
    runApplication<CoreServApplication>(*args)
}
