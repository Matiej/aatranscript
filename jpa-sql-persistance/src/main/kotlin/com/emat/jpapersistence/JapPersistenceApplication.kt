package com.emat.jpapersistence

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.emat.jpapersistence"])
class JapPersistenceApplication


fun main(args: Array<String>) {
    runApplication<JapPersistenceApplication>(*args)
}