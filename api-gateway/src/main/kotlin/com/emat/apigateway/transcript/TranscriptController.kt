package com.emat.apigateway.transcript

import com.emat.apigateway.global.headerfactory.HttpHeaderFactory.Companion.getSuccessfulHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/v1/transcriptions")
class TranscriptController {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(
        summary = "Add new Transcription",
        description = "Add new Transcription using RestCreateTranscription. All fields are validated"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Author object created successful"),
            ApiResponse(
                responseCode = "400",
                description = "Validation failed. Some fields are wrong. Response contains all details."
            ),
        ]
    )
    fun addTranscription(@RequestBody createTranscription: RestCreateTranscription): ResponseEntity<Any> {
        //my logic
        val myExampleID: Long = 1L
        return ResponseEntity.created(createURI(myExampleID))
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
}