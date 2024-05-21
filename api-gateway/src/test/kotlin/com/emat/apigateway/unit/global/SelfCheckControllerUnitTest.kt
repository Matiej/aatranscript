package com.emat.apigateway.unit.global

import com.emat.apigateway.global.GlobalControllerExceptionHandler
import com.emat.apigateway.global.SelfCheckController
import com.emat.coreserv.global.AppData
import io.mockk.every
import io.mockk.mockk
import mu.KotlinLogging
import org.junit.jupiter.api.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
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
        mockMvc.perform(get("/v1/selfcheck/version"))
            .andExpect(status().isOk)
            .andExpect(content().string(applicationVersion))
            .andDo { result ->
                val responseBody = result.response.contentAsString
                Assertions.assertNotNull(responseBody)
                Assertions.assertEquals(applicationVersion, responseBody)
                Assertions.assertNotEquals("Other version", responseBody)
            }

    }

    @Test
    fun `greetings endpoint returns expected message and use GET method returns 200 response code`() {
        //given
        val testName: String = "Maciek"

        //when
        var expect = mockMvc.perform(get("/v1/selfcheck/{name}", testName))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello $testName"))

        //then
        expect
            .andDo { result ->
                val responseBody = result.response.contentAsString
                Assertions.assertEquals("Hello $testName", responseBody)
            }
    }
}