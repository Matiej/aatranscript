package com.emat.apigateway.global

import com.emat.coreserv.global.AppData
import mu.KotlinLogging
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
class SelfCheckControllerIntgTest {

    private val log = KotlinLogging.logger {}

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var appData: AppData

    @BeforeEach
    fun setup(testInfo: TestInfo) {
        log.info("Starting test: ${testInfo.displayName}")
    }

    @Test
    fun `retrieve application version returns version as expected and use GET method, returns 200 response code`() {
        //given
        val applicationVersion = "1.0.0_SNAPSHOT"
        //when
        Mockito.`when`(appData.getApplicationVersion()).thenReturn(applicationVersion)

        //then
        webTestClient.get().uri("/v1/selfcheck/version")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .consumeWith { response ->
                assertNotNull(response.responseBody)
                assertEquals("1.0.0_SNAPSHOT", response.responseBody)
                assertNotEquals("Other verrsion", response.responseBody)
            }
    }

    @Test
    fun `greetings endpoint returns expected message and use GET method returns 200 response code`() {
        //given
        val testName: String = "Maciek"

        //when
        var uri = webTestClient.get()
            .uri("/v1/selfcheck/{name}", testName)


        //then
        val resposne = uri
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()

        assertEquals("Hello $testName", resposne.responseBody)

    }
}