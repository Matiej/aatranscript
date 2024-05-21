package com.emat.coreserv.transcription

import com.emat.coreserv.transcription.domian.Transcription

interface TranscriptionService {
    fun addTranscription(transcriptionCommand: TranscriptionCommand): TranscriptionResponse
    fun findAllTranscriptions(): List<Transcription>
    fun updateTranscription(id: Long, toCommand: TranscriptionCommand): TranscriptionResponse
}