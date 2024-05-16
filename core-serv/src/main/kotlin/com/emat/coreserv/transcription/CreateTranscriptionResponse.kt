package com.emat.coreserv.transcription

//import org.springframework.http.HttpStatus

data class CreateTranscriptionResponse (
    var id: Long?,
    var success: Boolean?,
    var message: String?,
    var errorStatus: String?

){
//    enum class CreateTranscriptionErrorStatus(val status: HttpStatus) {
//        NOT_FOUND(HttpStatus.NOT_FOUND),
//        FORBIDDEN(HttpStatus.FORBIDDEN),
//        BAD_REQUEST(HttpStatus.BAD_REQUEST),
//        SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR)
//    }

    companion object {
        fun success(id: Long): CreateTranscriptionResponse {
            return CreateTranscriptionResponse(
                id = id,
                success = true,
                message = "Transcription created successfully",
                errorStatus = null
            )
        }

        fun error(errorStatus: String, errorMessage: String): CreateTranscriptionResponse {
            return CreateTranscriptionResponse(
                id = null,
                success = false,
                message = errorMessage,
                errorStatus = errorStatus
            )
        }
    }


}
