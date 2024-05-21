package com.emat.jpapersistence.transcript

import com.emat.jpapersistence.transcript.entity.TranscriptionEntity
import com.emat.jpapersistence.transcript.repositories.TranscriptionRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RepositoryTranscriptionServiceImpl(
    @Autowired private val transcriptionRepository: TranscriptionRepository
) : RepositoryTranscriptionService {

    private val logger = KotlinLogging.logger {}

    override fun saveTranscription(transcriptionEntity: TranscriptionEntity): TranscriptionEntity {
        logger.info("Saving transcription: ${transcriptionEntity.openAiTranscriptionId}")
        val savedTranscription = transcriptionRepository.save(transcriptionEntity)
        logger.info("Saved transcription: ${savedTranscription.id}")
        return savedTranscription
    }

    override fun findAllTranscriptions(): List<TranscriptionEntity> {
        logger.info("Trying to find all transcriptions")
        val transcriptionEntityMutableList: List<TranscriptionEntity> = transcriptionRepository.findAll().toList()
        logger.info("Found ${transcriptionEntityMutableList.size} transcriptions")
        return transcriptionEntityMutableList
    }

    override fun findTranscriptionById(id: Long): Optional<TranscriptionEntity> {
        logger.info("Trying to find transcription with ID: $id")
        return transcriptionRepository.findById(id).also {
            if (it.isPresent) {
                logger.info("Found transcription with ID: $id")
            } else {
                logger.info("Transcription with ID: $id not found")
            }
        }
    }
}