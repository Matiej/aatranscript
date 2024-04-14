package com.emat.apigateway.global.headerfactory

enum class HeaderKey(private val keyLabel: String) {
    STATUS("Status"),
    MESSAGE("Message"),
    CREATED_AT("CreatedAt"),
    UPDATED_AT("UpdatedAt"),
    SERVER_FILENAME("ServerFileName");

    fun getHearKeyLabel(): String = keyLabel
}