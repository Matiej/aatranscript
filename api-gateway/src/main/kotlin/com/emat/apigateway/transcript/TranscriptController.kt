//package com.emat.apigateway.transcript
//
//import com.emat.apigateway.global.headerfactory.HttpHeaderFactory.Companion.getSuccessfulHeaders
//import com.emat.coreserv.transcription.TranscriptionService
//import io.swagger.v3.oas.annotations.Operation
//import io.swagger.v3.oas.annotations.responses.ApiResponse
//import io.swagger.v3.oas.annotations.responses.ApiResponses
//import mu.KotlinLogging
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.HttpMethod
//import org.springframework.http.HttpStatus
//import org.springframework.http.MediaType
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder
//import java.net.URI
//
//@RestController
//@RequestMapping("/v1/transcriptions")
//class TranscriptController(
//    @Autowired private val transcriptionService: TranscriptionService
//) {
//
//    private val logger = KotlinLogging.logger {}
//
//    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
//    @Operation(
//        summary = "Add new Transcription",
//        description = "Add new Transcription using RestCreateTranscription. All fields are validated"
//    )
//    @ApiResponses(
//        value = [
//            ApiResponse(responseCode = "201", description = "Author object created successful"),
//            ApiResponse(
//                responseCode = "400",
//                description = "Validation failed. Some fields are wrong. Response contains all details."
//            ),
//        ]
//    )
//    fun addTranscription(@RequestBody createTranscription: RestCreateTranscription): ResponseEntity<Any> {
//        logger.info("Received request to add new transcription ID: ${createTranscription.openAiTranscriptionId}, on endpoint '/v1/transcriptions'")
//        var addTranscriptionResult = transcriptionService.addTranscription(createTranscription.toCommand())
//
//        var id = addTranscriptionResult.id
//        logger.info("Successfully processed transcription, ID: $id")
//        return ResponseEntity.created(createURI(id!!))
//            .headers(getSuccessfulHeaders(HttpStatus.CREATED, HttpMethod.POST))
//            .build()
//    }
//
//    private fun createURI(id: Long): URI {
//        return ServletUriComponentsBuilder
//            .fromCurrentServletMapping()
//            .path("/v1")
//            .path("/transcriptions")
//            .path("/{id}")
//            .buildAndExpand(id)
//            .toUri()
//    }
//}