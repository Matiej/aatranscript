package com.emat.jpa_entity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["com.emat.jpa_entity"])
@EnableJpaRepositories
class JpaEntityApplication

fun main(args: Array<String>) {
    runApplication<JpaEntityApplication>(*args)
}
