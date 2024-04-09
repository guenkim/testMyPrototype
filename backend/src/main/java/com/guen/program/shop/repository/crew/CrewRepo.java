package com.guen.program.shop.repository.crew;

import com.guen.program.shop.model.entity.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CrewRepo extends JpaRepository<Crew, Long>, CrewRepoExtend , JpaSpecificationExecutor<Crew> , QuerydslPredicateExecutor<Crew> {

}
