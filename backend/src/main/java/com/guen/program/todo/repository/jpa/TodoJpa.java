package com.guen.program.todo.repository.jpa;

import com.guen.common.model.dto.PageResponse;
import com.guen.program.todo.model.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.querydsl.core.types.Predicate;

public interface TodoJpa extends JpaRepository<Todo,Long> , TodoJpaExtend , JpaSpecificationExecutor<Todo> , QuerydslPredicateExecutor<Todo> {
    /******************************************************************
     * 동일한 결과를 도출해 내는 함수 작성 예제
     *****************************************************************/

    // Query Method 사용
    @EntityGraph(attributePaths = {"files"})
    Page<Todo> findBySubjectContaining(final String subject, final Pageable pageable);

    //JpaSpecificationExecutor  사용
    @EntityGraph(attributePaths = {"files"})
    Page<Todo> findAll(final Specification specification, final Pageable pageable);

    //QuerydslPredicateExecutor 사용
    @EntityGraph(attributePaths = {"files"})
    Page<Todo> findAll(final Predicate predicate, final Pageable pageable);


    //querydsl 사용
    PageResponse findAll(final String subject, final Pageable pageable);

}
