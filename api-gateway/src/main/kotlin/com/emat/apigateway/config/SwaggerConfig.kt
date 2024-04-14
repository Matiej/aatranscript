//package com.emat.apigateway.config
//
//import io.swagger.v3.oas.models.ExternalDocumentation
//import io.swagger.v3.oas.models.OpenAPI
//import io.swagger.v3.oas.models.info.Contact
//import io.swagger.v3.oas.models.info.Info
//import io.swagger.v3.oas.models.info.License
//import org.springdoc.core.GroupedOpenApi
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//class SwaggerConfig(@Autowired private val appData: AppData) {
//
//    companion object {
//        private val PUBLIC_PATHS_TO_MATCH = arrayOf(
//            "/api/transcript/**",
//        )
//
//        private const val ADMIN_PATH_TO_MAP = "/admin/**"
//    }
//
////    @Bean
////    fun publicApi(): GroupedOpenApi = GroupedOpenApi.builder()
////        .group("1_trans_public")
////        .displayName("PUBLIC_API")
////        .pathsToMatch(*PUBLIC_PATHS_TO_MATCH)
////        .build()
////
////    @Bean
////    fun adminApi(): GroupedOpenApi = GroupedOpenApi.builder()
////        .group("2_springRecallBookApp-admin")
////        .displayName("ADMIN-API")
////        .pathsToMatch(ADMIN_PATH_TO_MAP)
////        .build()
//
//    @Bean
//    fun aaTranscriptOpenAPI(): OpenAPI = OpenAPI()
//        .info(
//            Info().title("aaTranscript API")
//                .description("Saving mp3 files transcription summary and embeddings")
//                .version(appData.applicationVersion())
//                .contact(Contact().name("Maciej Wójcik").email("myEmail@gmail.com"))
//                .license(License().name("Apache 2.0").url("http://springdoc.org"))
//        ).externalDocs(
//            ExternalDocumentation()
//                .description("BookApp store Wiki Documentation")
//                .url("https://springshop.wiki.github.org/docs")
//        )
//}