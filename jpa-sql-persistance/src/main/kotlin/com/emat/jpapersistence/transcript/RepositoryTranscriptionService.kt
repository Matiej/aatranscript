package com.emat.jpapersistence.transcript

import com.emat.jpapersistence.transcript.entity.TranscriptionEntity
import java.util.*

interface RepositoryTranscriptionService {
    fun saveTranscription(transcriptionEntity: TranscriptionEntity): TranscriptionEntity
    fun findAllTranscriptions(): List<TranscriptionEntity>
    fun findTranscriptionById(id: Long): Optional<TranscriptionEntity>
}