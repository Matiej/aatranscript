package com.emat.coreserv.transcription

import com.emat.coreserv.transcription.domian.Transcription
import com.emat.coreserv.transcription.domian.toTranscription
import com.emat.jpapersistence.transcript.RepositoryTranscriptionService
import com.emat.jpapersistence.transcript.entity.TranscriptionEntity
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
private class TranscriptionServiceImpl(
    @Autowired private val repositoryService: RepositoryTranscriptionService
) : TranscriptionService {

    private val logger = KotlinLogging.logger {}
    override fun addTranscription(createTranscriptionCommand: CreateTranscriptionCommand): CreateTranscriptionResponse {
        logger.info("Received request to add transcription: ${createTranscriptionCommand.openAiTranscriptionId}")
        val transcriptionEntity = createTranscriptionCommand.toEntity()
        val savedTranscription = repositoryService.saveTranscription(transcriptionEntity)
        return savedTranscription.id?.let { CreateTranscriptionResponse.success(it) }
            ?: CreateTranscriptionResponse.error(
                "SERVER_ERROR",
                "Transcription could not be saved"
            )
    }

    override fun findAllTranscriptions(): List<Transcription> {
        logger.info("Received request to find all transcriptions")
        val transcriptionEntityList: List<TranscriptionEntity> = repositoryService.findAllTranscriptions()
        return transcriptionEntityList.map { it.toTranscription() }.toList()
    }

//    private fun saveExtra() {
//        val transcription = TranscriptionEntity(
//            openAiTranscriptionId = "initialIDextra",
//            originalContent = "Initial contentEX",
//            summary = "Initial summary",
//            transcriptionDate = "2023-01-01",
//            source = "Initial sourceEX",
//            author = "Initial authorEX"
//        )
//        repositoryService.saveTranscription(transcription)
//    }
}
