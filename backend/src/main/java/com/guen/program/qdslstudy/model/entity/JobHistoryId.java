package com.guen.program.qdslstudy.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class JobHistoryId implements Serializable {
    private static final long serialVersionUID = 124814276955964760L;
    @NotNull
    @Column(name = "EMPLOYEE_ID", nullable = false)
    private Integer employeeId;

    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JobHistoryId entity = (JobHistoryId) o;
        return Objects.equals(this.employeeId, entity.employeeId) &&
                Objects.equals(this.startDate, entity.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, startDate);
    }

}