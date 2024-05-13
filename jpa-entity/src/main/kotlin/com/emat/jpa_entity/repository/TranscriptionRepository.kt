package com.emat.jpa_entity.repository

import com.emat.jpa_entity.sql_entities.TranscriptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TranscriptionRepository : JpaRepository<TranscriptionEntity, Long> {
}