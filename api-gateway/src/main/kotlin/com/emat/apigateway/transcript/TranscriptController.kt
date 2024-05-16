package com.emat.apigateway.transcript

import com.emat.apigateway.global.headerfactory.HttpHeaderFactory.Companion.getSuccessfulHeaders
import com.emat.coreserv.transcription.TranscriptionService
import com.emat.coreserv.transcription.domian.Transcription
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/v1/transcriptions")
class TranscriptController(
    @Autowired private val transcriptionService: TranscriptionService
) {

    private val logger = KotlinLogging.logger {}

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Add new Transcription",
        description = "Add new Transcription using RestCreateTranscription. All fields are validated"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Transcription object created successful"),
            ApiResponse(
                responseCode = "400",
                description = "Validation failed. Some fields are wrong. Response contains all details."
            ),
        ]
    )
    fun addTranscription(@RequestBody createTranscription: RestCreateTranscription): ResponseEntity<Any> {
        logger.info("Received request to add new transcription ID: ${createTranscription.openAiTranscriptionId}, on endpoint '/v1/transcriptions'")
        val addTranscriptionResult = transcriptionService.addTranscription(createTranscription.toCommand())
        val id = addTranscriptionResult.id
        logger.info("Successfully processed transcription, ID: $id")
        return ResponseEntity.created(createURI(id!!))
            .headers(getSuccessfulHeaders(HttpStatus.CREATED, HttpMethod.POST))
            .build()
    }

    private fun createURI(id: Long): URI {
        return ServletUriComponentsBuilder
            .fromCurrentServletMapping()
            .path("/v1")
            .path("/transcriptions")
            .path("/{id}")
            .buildAndExpand(id)
            .toUri()
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Find all transcriptions from sql database",
        description = "Method to find all transcriptions from sql database using core-serv module and jpa-sql-persistence."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "List of transcriptions objects found successful"),
            ApiResponse(
                responseCode = "500",
                description = "Server error, something is not working"
            ),
        ]
    )
    fun findAllTranscriptions(): ResponseEntity<List<Transcription>> {
        logger.info("Received request to find all transcriptions")
        val transcriptions = transcriptionService.findAllTranscriptions()
        return ResponseEntity.ok(transcriptions)
    }

}