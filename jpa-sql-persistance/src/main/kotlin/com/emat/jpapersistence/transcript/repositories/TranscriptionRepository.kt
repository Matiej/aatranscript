package com.emat.jpapersistence.transcript.repositories

import com.emat.jpapersistence.transcript.entity.TranscriptionEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TranscriptionRepository : CrudRepository<TranscriptionEntity, Long>{
}