package com.emat.apigateway.global

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/selfcheck")
class SelfCheckController(
    @Autowired private val appData: AppData
) {

    @GetMapping(value = ["/version"])
    fun retrieveApplicationVersion(): ResponseEntity<Any> {
        return ResponseEntity.ok(this.appData.applicationVersion())
    }

}