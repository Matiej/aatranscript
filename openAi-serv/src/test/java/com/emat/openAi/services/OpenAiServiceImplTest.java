//package com.emat.openAi.services;
//
//import com.emat.openAi.OpenAiApplication;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.PropertySource;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Slf4j
//@SpringBootTest(classes = OpenAiApplication.class)
//@ComponentScan(basePackages = {"com.emat.coreserv.global", "com.emat.apigateway.global", "com.emat.apigateway"})
////@ActiveProfiles("test")
////@AutoConfigureWebClient
//@PropertySource("classpath:application-test.properties")
//class OpenAiServiceImplTest {
//
//    @Autowired
//    OpenAiServiceImpl service;
//
//    @BeforeEach
//    void setUp(TestInfo testInfo) {
//        log.info("Test started - {} .", testInfo.getDisplayName());
//    }
//
//    @AfterEach
//    void tearDown(TestInfo testInfo) {
//        log.info("Test finished - {} .", testInfo.getDisplayName());
//    }
//
//    @Test
//    void getAnswer() {
//        //given
//        String question = "What is the capital of Poland and what are two biggest cites in that country?";
//
//        //when
//        String answer = service.getAnswer(question);
//
//        //then
//        assertNotNull(answer);
//        assertTrue(answer.contains("Warsaw") && answer.contains("Krakow"));
//        System.out.printf(answer);
//    }
//}