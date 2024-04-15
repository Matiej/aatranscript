package com.emat.coreserv.global

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AppData {

    @Value("\${app.version}")
    private lateinit var appVersion: String

    fun getApplicationVersion(): String {
        return appVersion.plus(" on the day: ").plus(LocalDateTime.now().toString())
    }
}