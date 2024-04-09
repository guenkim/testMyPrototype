package com.guen.common.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseDate implements Serializable {

    private static final long serialVersionID = 1L;

    @CreatedDate
    @Column(name = "regdt", updatable = false)
    protected LocalDateTime regdt;

    @LastModifiedDate
    @Column(name = "moddt")
    protected LocalDateTime moddt;

}

