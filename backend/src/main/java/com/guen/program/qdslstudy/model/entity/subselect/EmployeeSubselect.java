package com.guen.program.qdslstudy.model.entity.subselect;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import java.math.BigDecimal;

@Entity
@Subselect(
        "SELECT"+
            " department_id,"+
            " SUM(salary) dept_sal"+
        " FROM HR.employees"+
        " GROUP BY department_id"+
        " ORDER BY sum(salary) DESC"
)
@Immutable
@Table(name = "EMPLOYEES" ,schema = "HR")
@Synchronize("EMPLOYEES")
@NoArgsConstructor(access= AccessLevel.PROTECTED)

public class EmployeeSubselect {
    @Id
    @Column(name = "DEPARTMENT_ID")
    private Short departmentId;

    @Column(name = "dept_sal")
    private BigDecimal deptSal;
}
