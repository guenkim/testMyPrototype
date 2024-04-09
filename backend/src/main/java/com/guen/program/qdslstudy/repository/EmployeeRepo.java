package com.guen.program.qdslstudy.repository;

import com.guen.program.qdslstudy.model.dto.EmployeeDTO;
import com.guen.program.qdslstudy.model.dto.TestDto2;
import com.guen.program.qdslstudy.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

    @Query(
            value="" +
                    "SELECT\n" +
                    "             a.department_id  as id ,\n" +
                    "             b.department_name as departmentName,\n" +
                    "             a.dept_sal  as salary \n" +
                    "     FROM (\n" +
                    "             SELECT\n" +
                    "             department_id,\n" +
                    "             SUM(salary) dept_sal\n" +
                    "             FROM HR.employees\n" +
                    "             GROUP BY department_id\n" +
                    "             ORDER BY sum(salary) DESC\n" +
                    "     ) a,\n" +
                    "     HR.departments b\n" +
                    "     WHERE a.department_id = b.department_id\n" +
                    "     AND ROWNUM <= 3"
            , nativeQuery=true
    )
    List<EmployeeDTO> findDepartmentInfo();

}
