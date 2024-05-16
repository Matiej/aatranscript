package com.emat.coreserv.transcription.domian

import com.emat.jpapersistence.transcript.entity.TranscriptionEntity
import java.time.LocalDateTime

data class Transcription(
    val id: Long? = null,
    val UUID: String,
    val createdAt: LocalDateTime? = null,
    val lastUpdatedAt: LocalDateTime? = null,
    val version: Long? = null,
    val openAiTranscriptionId: String? = null,
    val originalContent: String? = null,
    val summary: String? = null,
    val transcriptionDate: String? = null,
    val source: String? = null,
    val author: String? = null
)

fun TranscriptionEntity.toTranscription(): Transcription {
    return Transcription(
        id = id,
        UUID = UUID,
        createdAt = createdAt,
        lastUpdatedAt = lastUpdatedAt,
        version = version,
        openAiTranscriptionId = openAiTranscriptionId,
        originalContent = originalContent,
        summary = summary,
        transcriptionDate = transcriptionDate,
        source = source,
        author = author
    )
}



