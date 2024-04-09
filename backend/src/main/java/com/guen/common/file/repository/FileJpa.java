package com.guen.common.file.repository;

import com.guen.common.file.model.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface FileJpa extends JpaRepository<Files,Long>,FileJpaExtend, QuerydslPredicateExecutor<Files>, JpaSpecificationExecutor<Files> {
    Optional<Files> findFileById(Long fileId);

    void deleteByTodoId(Long todoId);
}
