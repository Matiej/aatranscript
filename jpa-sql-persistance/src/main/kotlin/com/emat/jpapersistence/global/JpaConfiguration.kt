package com.emat.jpapersistence.global

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@PropertySource("classpath:application.properties")
@Configuration
class JpaConfiguration {
    @Value("\${database.driver.name}")
    private lateinit var driverClassName: String

    @Value("\${database.url}")
    private lateinit var dataBaseUrl: String

    @Value("\${database.username}")
    private lateinit var username: String

    @Value("\${database.password}")
    private lateinit var password: String

    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName(driverClassName)
        dataSource.url = dataBaseUrl
        dataSource.username = username
        dataSource.password = password
        return dataSource
    }
}