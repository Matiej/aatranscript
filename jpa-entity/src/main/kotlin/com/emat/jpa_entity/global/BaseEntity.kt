package com.emat.jpa_entity.global

import jakarta.persistence.*
import lombok.EqualsAndHashCode
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID.randomUUID

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@EqualsAndHashCode(of = ["UUID"])
open class BaseEntity {
    @Id
    @GeneratedValue
    open val id: Long? = null
    open val UUID: String = randomUUID().toString()

    @CreatedDate
    open val createdAt: LocalDateTime? = null

    @LastModifiedDate
    open val lastUpdatedAt: LocalDateTime? = null

    @Version
    open val version: Long? = null
}