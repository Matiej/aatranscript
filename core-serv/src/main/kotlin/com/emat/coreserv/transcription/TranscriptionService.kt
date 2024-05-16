package com.emat.coreserv.transcription

import com.emat.coreserv.transcription.domian.Transcription

interface TranscriptionService {
    fun addTranscription(createTranscriptionCommand: CreateTranscriptionCommand): CreateTranscriptionResponse
    fun findAllTranscriptions(): List<Transcription>
}