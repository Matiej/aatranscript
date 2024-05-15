package com.emat.coreserv.global

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDateTime

@Component
 class AppData {
    var clock: Clock = Clock.systemDefaultZone()
        set(value) {
            field = value
        }

    @Value("\${app.version}")
    private lateinit var appVersion: String

    private fun now(): LocalDateTime {
        return LocalDateTime.now(clock)
    }

    fun getApplicationVersion(): String {
        return appVersion.plus(" on the day: ").plus(now().toString())
    }
}