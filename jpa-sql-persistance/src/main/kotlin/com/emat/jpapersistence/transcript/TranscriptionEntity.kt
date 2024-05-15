package com.emat.jpapersistence.transcript

import com.emat.jpapersistence.global.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import lombok.EqualsAndHashCode

@Entity
@Table(name = "Transcriptions")
@EqualsAndHashCode(callSuper = true, of = ["originalContent", "summary", "transcriptionDate", "source", "author"])
class TranscriptionEntity : BaseEntity() {

    val originalContent: String? = null
    val summary: String? = null
    val transcriptionDate: String? = null
    val source: String? = null
    val author: String? = null
}