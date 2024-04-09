package com.guen.common.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseUser implements Serializable {

    private static final long serialVersionID = 1L;

    @CreatedBy
    @Column(name = "createdBy",length = 255, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modifiedBy",length = 255)
    private String modifiedBy;
}
