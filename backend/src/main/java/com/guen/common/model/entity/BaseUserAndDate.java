package com.guen.common.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseUserAndDate implements Serializable {

    private static final long serialVersionID = 1L;

    @CreatedBy
    @Column(name = "createdBy",length = 255, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modifiedBy",length = 255)
    private String modifiedBy;

    @CreatedDate
    @Column(name = "regdt", updatable = false)
    protected LocalDateTime regdt;

    @LastModifiedDate
    @Column(name = "moddt")
    protected LocalDateTime moddt;
}
