package com.emat.apigateway.global

import com.emat.apigateway.transcript.TranscriptController
import com.emat.coreserv.global.AppData
import io.mockk.every
import io.mockk.mockk
import mu.KotlinLogging
import org.junit.jupiter.api.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

class SelfCheckControllerUnitTest {

    private val log = KotlinLogging.logger {}

    private lateinit var mockMvc: MockMvc
    private lateinit var appData: AppData

    @BeforeEach
    fun setup(testInfo: TestInfo) {
        log.info("Starting test: ${testInfo.displayName}")

        appData = mockk()

        mockMvc = MockMvcBuilders.standaloneSetup(SelfCheckController(appData))
            .setControllerAdvice(GlobalControllerExceptionHandler::class)
            .build()
    }

    @AfterEach
    fun tearDown(testInfo: TestInfo) {
        log.info("Finished test: ${testInfo.displayName}")
    }


    @Test
    fun `retrieve application version returns version as expected and use GET method, returns 200 response code`() {
        //given
        val applicationVersion = "1.0.0_SNAPSHOT".plus(" on the day: ").plus("2024-10-15T00:00")

        //when
        val fixedClock = Clock.fixed(Instant.parse("2024-10-15T00:00:00Z"), ZoneOffset.UTC)
        every { appData.clock } returns fixedClock
        every { appData.getApplicationVersion() } returns applicationVersion

        //expect
        mockMvc.get("/v1/selfcheck/version")
            .andExpect {
                status { isOk() }
                content { string(applicationVersion) }
            }
//            .expectStatus().isOk
//            .expectBody(String::class.java)
//            .consumeWith { response ->
//                Assertions.assertNotNull(response.responseBody)
//                Assertions.assertEquals(applicationVersion, response.responseBody)
//                Assertions.assertNotEquals("Other verrsion", response.responseBody)
//            }

    }

//    @Test
//    fun `greetings endpoint returns expected message and use GET method returns 200 response code`() {
//        //given
//        val testName: String = "Maciek"
//
//        //when
//        var uri = webTestClient.get()
//            .uri("/v1/selfcheck/{name}", testName)
//
//        //then
//        val resposne = uri
//            .exchange()
//            .expectStatus().isOk
//            .expectBody(String::class.java)
//            .returnResult()
//
//        Assertions.assertEquals("Hello $testName", resposne.responseBody)
//
//    }


}