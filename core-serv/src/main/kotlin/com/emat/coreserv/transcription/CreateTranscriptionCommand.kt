package com.emat.coreserv.transcription

import com.emat.jpapersistence.transcript.entity.TranscriptionEntity

data class CreateTranscriptionCommand(
    var openAiTranscriptionId: String? = null,
    var originalContent: String? = null,
    var summary: String? = null,
    var transcriptionDate: String? = null,
    var source: String? = null,
    var author: String? = null
) {
    fun toEntity(): TranscriptionEntity {
           return TranscriptionEntity(
                openAiTranscriptionId = openAiTranscriptionId,
                originalContent = originalContent,
                summary = summary,
                transcriptionDate = transcriptionDate,
                source = source,
                author = author
            )
    }
}
