package com.emat.apigateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["com.emat.coreserv", "com.emat.apigateway", "com.emat.jpapersistence", "com.emat.openAi"])
@EnableJpaRepositories(basePackages = ["com.emat.jpapersistence.transcript.repositories"])
@EntityScan(basePackages = ["com.emat.jpapersistence.transcript.entity"])
@EnableJpaAuditing
class ApiGatewayApplication

fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}
