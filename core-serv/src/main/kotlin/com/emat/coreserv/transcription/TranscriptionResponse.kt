package com.emat.coreserv.transcription

import org.springframework.http.HttpStatus

data class TranscriptionResponse(
    var id: Long?,
    var success: Boolean?,
    var message: String?,
    var errorStatus: TranscriptionErrorStatus?

) {
    enum class TranscriptionErrorStatus(val status: HttpStatus) {
        NOT_FOUND(HttpStatus.NOT_FOUND),
        FORBIDDEN(HttpStatus.FORBIDDEN),
        BAD_REQUEST(HttpStatus.BAD_REQUEST),
        SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    companion object {
        fun success(id: Long): TranscriptionResponse {
            return TranscriptionResponse(
                id = id,
                success = true,
                message = "Transcription created successfully",
                errorStatus = null
            )
        }

        fun error(errorStatus: TranscriptionErrorStatus, errorMessage: String): TranscriptionResponse {
            return TranscriptionResponse(
                id = null,
                success = false,
                message = errorMessage,
                errorStatus = errorStatus
            )
        }
    }


}
