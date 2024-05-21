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
    override fun addTranscription(transcriptionCommand: TranscriptionCommand): TranscriptionResponse {
        logger.info("Received request to add transcription: ${transcriptionCommand.openAiTranscriptionId}")
        val transcriptionEntity = transcriptionCommand.toEntity()
        val savedTranscription = repositoryService.saveTranscription(transcriptionEntity)
        return savedTranscription.id?.let { TranscriptionResponse.success(it) }
            ?: TranscriptionResponse.error(
                TranscriptionResponse.TranscriptionErrorStatus.SERVER_ERROR,
                "Transcription could not be saved"
            )
    }

    override fun findAllTranscriptions(): List<Transcription> {
        logger.info("Received request to find all transcriptions")
        val transcriptionEntityList: List<TranscriptionEntity> = repositoryService.findAllTranscriptions()
        return transcriptionEntityList.map { it.toTranscription() }.toList()
    }

    override fun updateTranscription(id: Long, toCommand: TranscriptionCommand): TranscriptionResponse {
        logger.info("Received request to update transcription: $id")
        return repositoryService.findTranscriptionById(id).orElse(null)?.let {
            TranscriptionResponse.success(it.id!!)
        } ?: TranscriptionResponse.error(
            TranscriptionResponse.TranscriptionErrorStatus.NOT_FOUND,
            "Transcription with ID: $id not found"
        )
    }
}
