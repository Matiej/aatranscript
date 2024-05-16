//package com.emat.apigateway.global
//
//import com.emat.coreserv.global.AppData
//import mu.KotlinLogging
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//
//@RestController
//@RequestMapping("/v1/selfcheck")
//class SelfCheckController(
//    @Autowired private val appData: AppData
//) {
//
//    private val logger = KotlinLogging.logger {}
//
//    @GetMapping(value = ["/version"])
//    fun retrieveApplicationVersion(): ResponseEntity<Any> {
//        logger.info("Received request on endpoint: /v1/selfcheck/version")
//        return ResponseEntity.ok(this.appData.getApplicationVersion())
//    }
//
//    @GetMapping(value = ["/{name}"])
//    fun greetings(@PathVariable name: String): ResponseEntity<String> {
//        logger.info("Received request on endpoint: /v1/selfcheck//{name} with parameter: $name")
//        return ResponseEntity.ok("Hello $name")
//    }
//
//}