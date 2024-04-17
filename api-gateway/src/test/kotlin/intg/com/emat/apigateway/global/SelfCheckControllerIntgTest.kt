package com.emat.apigateway.global

import com.emat.coreserv.global.AppData
import mu.KotlinLogging
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = ["com.emat.coreserv.global"])
@ActiveProfiles("test")
@AutoConfigureWebClient
@PropertySource("classpath:application-test.properties")
class SelfCheckControllerIntgTest {

    private val log = KotlinLogging.logger {}

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var appData: AppData

    @BeforeEach
    fun setup(testInfo: TestInfo) {
        log.info("Starting test: ${testInfo.displayName}")
    }

    @Test
    fun `retrieve application version returns version as expected and use GET method, returns 200 response code`() {
        //given
        val expectedApplicationVersion = "1.0.0_SNAPSHOT".plus( " on the day: ").plus("2024-10-15T00:00")

        //when
        appData.clock = Clock.fixed(Instant.parse("2024-10-15T00:00:00Z"), ZoneOffset.UTC)

        //then
        webTestClient.get().uri("/v1/selfcheck/version")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .consumeWith { response ->
                assertNotNull(response.responseBody)
                assertEquals(expectedApplicationVersion, response.responseBody)
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