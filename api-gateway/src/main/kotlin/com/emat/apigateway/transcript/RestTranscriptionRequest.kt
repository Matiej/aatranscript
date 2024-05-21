package com.emat.apigateway.transcript

import com.emat.coreserv.transcription.TranscriptionCommand
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class RestTranscriptionRequest {
    @field:NotNull(message = "openAiTranscriptionId is required")
    @field:Size(min = 1, max = 100, message = "Field 'openAiTranscriptionId' must be between 1 and 100 characters")
    var openAiTranscriptionId: String? = null

    @field:NotNull(message = "Field 'originalContent' is required")
    var originalContent: String? = null

    @field:NotNull(message = "Field 'summary' is required")
    var summary: String? = null

    @field:Pattern(
        regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01]) (0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$",
        message = "Field 'transcriptionDate' must be in the format dd:MM:yyyy hh:mm:ss"
    )
    var transcriptionDate: String? = null

    @field:NotNull(message = "Field 'source' is required")
    var source: String? = null

    @field:NotNull(message = "Field 'author' is required")
    var author: String? = null

    fun toCommand(): TranscriptionCommand {
        return TranscriptionCommand(
            openAiTranscriptionId = openAiTranscriptionId,
            originalContent = originalContent,
            summary = summary,
            transcriptionDate = transcriptionDate,
            source = source,
            author = author
        )
    }
}

