package com.emat.apigateway.transcript

import com.emat.apigateway.global.headerfactory.HeaderKey
import com.emat.apigateway.global.headerfactory.HttpHeaderFactory.Companion.getSuccessfulHeaders
import com.emat.coreserv.transcription.TranscriptionResponse
import com.emat.coreserv.transcription.TranscriptionService
import com.emat.coreserv.transcription.domian.Transcription
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
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
    fun addTranscription(@Valid @RequestBody createTranscription: RestTranscriptionRequest): ResponseEntity<Any> {
        logger.info("Received request to add new transcription ID: ${createTranscription.openAiTranscriptionId}, on endpoint '/v1/transcriptions'")
        val addTranscriptionResult = transcriptionService.addTranscription(createTranscription.toCommand())
        val id = addTranscriptionResult.id

        return addTranscriptionResult.success?.let {
            if (it) {
                logger.info("Successfully processed transcription save request, ID: $id")
                ResponseEntity.created(createURI(id!!))
                    .headers(getSuccessfulHeaders(HttpStatus.CREATED, HttpMethod.POST))
                    .build()
            } else {
                ResponseEntity.internalServerError()
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.POST.name())
                    .header(HeaderKey.STATUS.getHearKeyLabel(), addTranscriptionResult.errorStatus!!.status.name)
                    .header(HeaderKey.MESSAGE.getHearKeyLabel(), addTranscriptionResult.message)
                    .build()
            }
        } ?: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .header(HeaderKey.STATUS.getHearKeyLabel(), HttpStatus.INTERNAL_SERVER_ERROR.name)
            .header(HeaderKey.MESSAGE.getHearKeyLabel(), "Unknown error occurred")
            .build()
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

    @PutMapping(path = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Update transcription by ID",
        description = "Method to update transcription by ID in sql database using core-serv module and jpa-sql-persistence."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Transcription updated successful, returned ID"),
            ApiResponse(
                responseCode = "500",
                description = "Server error, something is not working"
            ),
            ApiResponse(responseCode = "404", description = "Transcription with ID not found")
        ]
    )
    fun updateTranscription(
        @PathVariable id: Long,
        @Valid @RequestBody updateTranscription: RestTranscriptionRequest
    ): ResponseEntity<Any> {
        logger.info("Received request to update transcription ID: $id, on endpoint '/v1/transcriptions'")
        val response: TranscriptionResponse =
            transcriptionService.updateTranscription(id, updateTranscription.toCommand())
        logger.info("Successfully processed transcription update, ID: $id")
        return response.success?.let {
            if (it) {
                ResponseEntity.ok()
                    .headers(getSuccessfulHeaders(HttpStatus.OK, HttpMethod.PUT))
                    .location(createURI(id))
                    .build()
            } else {
                ResponseEntity.badRequest()
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.PUT.name())
                    .header(HeaderKey.STATUS.getHearKeyLabel(), response.errorStatus!!.status.name)
                    .header(HeaderKey.MESSAGE.getHearKeyLabel(), response.message)
                    .build()
            }
        } ?: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .header(HeaderKey.STATUS.getHearKeyLabel(), HttpStatus.INTERNAL_SERVER_ERROR.name)
            .header(HeaderKey.MESSAGE.getHearKeyLabel(), "Unknown error occurred")
            .build()
    }

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
