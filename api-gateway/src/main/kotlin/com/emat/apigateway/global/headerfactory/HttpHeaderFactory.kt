package com.emat.apigateway.global.headerfactory

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

class HttpHeaderFactory {
    companion object {
        fun getSuccessfulHeaders(status: HttpStatus, vararg allowedMethods: HttpMethod): HttpHeaders {
            val httpHeaders = HttpHeaders()
            httpHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, allowedMethods.toString())
            httpHeaders.add(HeaderKey.STATUS.getHearKeyLabel(), status.name)
            httpHeaders.add(HeaderKey.MESSAGE.getHearKeyLabel(), "SUCCESSFUL")
            return httpHeaders
        }
    }
}