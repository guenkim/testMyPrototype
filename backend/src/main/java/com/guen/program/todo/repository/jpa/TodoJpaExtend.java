package com.guen.program.todo.repository.jpa;

import com.guen.common.model.dto.PageResponse;
import org.springframework.data.domain.Pageable;


public interface TodoJpaExtend {

    PageResponse findAll(final String subject, final Pageable pageable);

}
