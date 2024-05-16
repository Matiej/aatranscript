package com.emat.jpapersistence.transcript

import com.emat.jpapersistence.transcript.entity.TranscriptionEntity

interface RepositoryTranscriptionService {
    fun saveTranscription(transcriptionEntity: TranscriptionEntity): TranscriptionEntity
    fun findAllTranscriptions(): List<TranscriptionEntity>
}