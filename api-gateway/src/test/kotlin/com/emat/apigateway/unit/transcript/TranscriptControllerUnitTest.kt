package com.emat.apigateway.unit.transcript

import com.emat.apigateway.global.GlobalControllerExceptionHandler
import com.emat.apigateway.transcript.RestTranscriptionRequest
import com.emat.apigateway.transcript.TranscriptController
import com.emat.coreserv.transcription.TranscriptionResponse
import com.emat.coreserv.transcription.TranscriptionService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import mu.KotlinLogging
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInfo
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TranscriptControllerUnitTest {
    private val log = KotlinLogging.logger {}

    private lateinit var mockMvc: MockMvc

    private lateinit var transcriptionService: TranscriptionService

    private lateinit var transcriptionRequest: RestTranscriptionRequest

    @BeforeEach()
    fun setUp(testInfo: TestInfo) {
        log.info("Starting test: ${testInfo.displayName}")

        transcriptionRequest = RestTranscriptionRequest()
        transcriptionRequest.openAiTranscriptionId = "1234"
        transcriptionRequest.originalContent = "Test content"
        transcriptionRequest.transcriptionDate = "2024-05-21 11:21:45"
        transcriptionRequest.source = "Test source"
        transcriptionRequest.author = "Test author"
        transcriptionRequest.summary = "Test summary"

        transcriptionService = mockk()
        val noSpringInitiateGlobalCExceptionHandler = GlobalControllerExceptionHandler()

        mockMvc = MockMvcBuilders.standaloneSetup(TranscriptController(transcriptionService))
            .setControllerAdvice(noSpringInitiateGlobalCExceptionHandler)
            .build()
    }

    @AfterEach
    fun tearDown(testInfo: TestInfo) {
        log.info("Finished test: ${testInfo.displayName}")
    }

    @Test
    fun `should return 201 when addTranscription is successful`() {
        //given
        val response = TranscriptionResponse.success(id = 1L)

        //when
        every { transcriptionService.addTranscription(any()) } returns response

        //expect
        mockMvc.perform(
            post("/v1/transcriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "openAiTranscriptionId": "1234",
                        "originalContent": "Test content",
                        "transcriptionDate": "2024-05-21 11:21:45",
                        "source": "Test source",
                        "author": "Test author",
                        "summary": "Test summary"
    }
    """.trimIndent()
                )
        )
            .andExpect(status().isCreated)
            .andExpect(header().exists(HttpHeaders.LOCATION))

        //then
        verify { transcriptionService.addTranscription(any()) }
    }

    @Test
    fun `should return 404 not found when addTranscription is unsuccessful - bad url`() {
        //given
        val response = TranscriptionResponse.success(id = 1L)

        //when
        every { transcriptionService.addTranscription(any()) } returns response

        //expect
        mockMvc.perform(
            post("/v1/transcriptionsxyz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "openAiTranscriptionId": "1234",
                        "originalContent": "Test content",
                        "transcriptionDate": "2024-05-21 11:21:45",
                        "source": "Test source",
                        "author": "Test author",
                        "summary": "Test summary"
    }
    """.trimIndent()
                )
        )
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.error").value("No URL found for this request /v1/transcriptionsxyz"))
            .andReturn()

        //then
        verify(exactly = 0) { transcriptionService.addTranscription(any()) }
    }

    @Test
    fun `should return 500 internal server error when addTranscription is unsuccessful`() {
        //given
        val response = TranscriptionResponse.error( TranscriptionResponse.TranscriptionErrorStatus.SERVER_ERROR,
            "Transcription could not be saved")

        //when
        every { transcriptionService.addTranscription(any()) } returns response

        //expect
        mockMvc.perform(
            post("/v1/transcriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "openAiTranscriptionId": "1234",
                        "originalContent": "Test content",
                        "transcriptionDate": "2024-05-21 11:21:45",
                        "source": "Test source",
                        "author": "Test author",
                        "summary": "Test summary"
    }
    """.trimIndent()
                )
        )
            .andExpect(status().isInternalServerError)
            .andExpect(header().exists("Message"))
            .andExpect(header().string("Message", "Transcription could not be saved"))
            .andReturn()

        //then
        verify(exactly = 1) { transcriptionService.addTranscription(any()) }
    }

    @Test
    fun `should return 500 internal server error when addTranscription throws exception`() {
        //given
        val exceptionMessage = "Transcription could not be saved"

        //when
        every { transcriptionService.addTranscription(any()) } throws Exception(exceptionMessage)

        //expect
        mockMvc.perform(
            post("/v1/transcriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "openAiTranscriptionId": "1234",
                        "originalContent": "Test content",
                        "transcriptionDate": "2024-05-21 11:21:45",
                        "source": "Test source",
                        "author": "Test author",
                        "summary": "Test summary"
    }
    """.trimIndent()
                )
        )
            .andExpect(status().isInternalServerError)
            .andExpect(jsonPath("$.error").value(exceptionMessage))
            .andReturn()

        //then
        verify(exactly = 1) { transcriptionService.addTranscription(any()) }
    }

    @Test
    fun `should return 400 when openAiTranscriptionId is null`() {
        val requestContent = """
            {
                "originalContent": "Test content",
                "transcriptionDate": "2024-05-21 11:21:45",
                "source": "Test source",
                "author": "Test author",
                "summary": "Test summary"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/v1/transcriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.openAiTranscriptionId").value("openAiTranscriptionId is required"))
    }

    @Test
    fun `should return 400 when originalContent is null`() {
        val requestContent = """
            {
                "openAiTranscriptionId": "1234",
                "transcriptionDate": "2024-05-21 11:21:45",
                "source": "Test source",
                "author": "Test author",
                "summary": "Test summary"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/v1/transcriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.originalContent").value("Field 'originalContent' is required"))
    }

    @Test
    fun `should return 400 when summary is null`() {
        val requestContent = """
            {
                "openAiTranscriptionId": "1234",
                "originalContent": "Test content",
                "transcriptionDate": "2024-05-21 11:21:45",
                "source": "Test source",
                "author": "Test author"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/v1/transcriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.summary").value("Field 'summary' is required"))
    }

    @Test
    fun `should return 400 when transcriptionDate is invalid`() {
        val requestContent = """
            {
                "openAiTranscriptionId": "1234",
                "originalContent": "Test content",
                "transcriptionDate": "invalid-date",
                "source": "Test source",
                "author": "Test author",
                "summary": "Test summary"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/v1/transcriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.transcriptionDate").value("Field 'transcriptionDate' must be in the format dd:MM:yyyy hh:mm:ss"))
    }

    @Test
    fun `should return 400 when source is null`() {
        val requestContent = """
            {
                "openAiTranscriptionId": "1234",
                "originalContent": "Test content",
                "transcriptionDate": "2024-05-21 11:21:45",
                "author": "Test author",
                "summary": "Test summary"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/v1/transcriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.source").value("Field 'source' is required"))
    }

    @Test
    fun `should return 400 when author is null`() {
        val requestContent = """
            {
                "openAiTranscriptionId": "1234",
                "originalContent": "Test content",
                "transcriptionDate": "2024-05-21 11:21:45",
                "source": "Test source",
                "summary": "Test summary"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/v1/transcriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.author").value("Field 'author' is required"))
    }

//    @Test
//    fun findAllTranscriptions() {
//    }
//
//    @Test
//    fun updateTranscription() {
//    }
}