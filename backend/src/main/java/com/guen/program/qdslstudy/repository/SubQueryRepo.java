package com.guen.program.qdslstudy.repository;

import com.guen.program.qdslstudy.model.dto.TestDTO4;
import com.guen.program.qdslstudy.model.dto.TestDto2;
import com.guen.program.qdslstudy.model.entity.*;
import com.guen.program.qdslstudy.model.dto.TestDto;
import com.guen.program.qdslstudy.model.entity.subselect.EmployeeSubselect;
import com.guen.program.qdslstudy.model.entity.subselect.QEmployeeSubselect;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubQueryRepo {

    private JPAQueryFactory jqf;

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        jqf = new JPAQueryFactory(entityManager);
        this.entityManager = entityManager;
    }


    /**
     SELECT
         E.EMPLOYEE_ID,
         E.FIRST_NAME,
         E.LAST_NAME,
         E.EMAIL,
         JH.START_DATE,
         JH.END_DATE,
         JH.JOB_ID,
         D.DEPARTMENT_NAME,
         L.CITY,
         L.STATE_PROVINCE
     FROM HR.EMPLOYEES E
     JOIN HR.JOB_HISTORY JH ON E.EMPLOYEE_ID = JH.EMPLOYEE_ID
     JOIN HR.DEPARTMENTS D ON E.DEPARTMENT_ID = D.DEPARTMENT_ID
     JOIN HR.LOCATIONS L ON D.LOCATION_ID = L.LOCATION_ID
     WHERE E.EMPLOYEE_ID = 102;
    **/

    public List<Tuple> findJoinInfo() {

        List<Tuple> fetch = jqf
                .select(
                        QEmployee.employee.firstName,
                        QEmployee.employee.lastName,
                        QEmployee.employee.email,
                        QJobHistory.jobHistory.id.startDate,
                        QJobHistory.jobHistory.endDate,
                        QJobHistory.jobHistory.job.jobId,
                        QDepartment.department.departmentName,
                        QLocation.location.city,
                        QLocation.location.stateProvince
                )
                .from(QEmployee.employee)
                .innerJoin(QJobHistory.jobHistory)
                .on(QEmployee.employee.id.eq(QJobHistory.jobHistory.employee.id))
                .innerJoin(QDepartment.department)
                .on(QDepartment.department.id.eq(QEmployee.employee.department.id))
                .innerJoin(QLocation.location)
                .on(QLocation.location.id.eq(QDepartment.department.location.id))
                .where(QEmployee.employee.id.eq(102))
                .fetch();
        return fetch;
    }

    /**
     SELECT
             E.EMPLOYEE_ID,
             E.FIRST_NAME,
             E.LAST_NAME,
             E.EMAIL,
             JH.START_DATE,
             JH.END_DATE,
             JH.JOB_ID,
             D.DEPARTMENT_NAME,
             L.CITY,
             L.STATE_PROVINCE
     FROM
     (
         SELECT *
         FROM HR.EMPLOYEES
         WHERE EMPLOYEE_ID = 102
     ) E
     JOIN HR.JOB_HISTORY JH ON E.EMPLOYEE_ID = JH.EMPLOYEE_ID
     JOIN HR.DEPARTMENTS D ON E.DEPARTMENT_ID = D.DEPARTMENT_ID
     JOIN HR.LOCATIONS L ON D.LOCATION_ID = L.LOCATION_ID;
       **/


    public List<Tuple> findSubQeury() {
        QEmployee qEmployee = QEmployee.employee;
        QJobHistory qJobHistory = QJobHistory.jobHistory;
        QLocation qLocation = QLocation.location;
        QDepartment qDepartment = QDepartment.department;

        // 첫 번째 쿼리: EMPLOYEES 테이블에서 EMPLOYEE_ID = 102인 직원의 정보를 가져옵니다.
        Employee employee = jqf.selectFrom(qEmployee)
                .where(qEmployee.id.eq(102)).fetchOne();

        // 두 번째 쿼리: 첫 번째 쿼리에서 가져온 직원의 정보를 사용하여 JOB_HISTORY, DEPARTMENTS, LOCATIONS 테이블과 조인을 수행합니다.
        List<Tuple> tuples = jqf.select(
                        qEmployee.id,
                        qEmployee.firstName,
                        qEmployee.lastName,
                        qEmployee.email,
                        qJobHistory.id.startDate,
                        qJobHistory.endDate,
                        qJobHistory.job.jobId,
                        qDepartment.departmentName,
                        qLocation.city,
                        qLocation.stateProvince
                ).from(qJobHistory)
                .join(qDepartment)
                .on(qJobHistory.employee.department.id.eq(qDepartment.id))
                .join(qLocation)
                .on(qDepartment.location.id.eq(qLocation.id))
                .where(qJobHistory.employee.id.eq(employee.getId()))
                .fetch();

        return tuples;
    }

    /**
     SELECT
            a.employee_id,
            a.first_name || ' ' || a.last_name names,
            a.salary,
            ROUND(b.avgs), b.maxs
     FROM HR.employees a,
     (
        SELECT
                AVG(salary) avgs,
                 MAX(salary) maxs
         FROM HR.employees
     ) b
     WHERE a.salary BETWEEN b.avgs AND b.maxs
     ORDER BY a.salary DESC;
     **/

    public List<Tuple> findSubQeury2() {
        QEmployee qEmployee = QEmployee.employee;

        //employee 테이블에서 salary의 평균과 최대값을 구합니다.
        Tuple tuple = jqf.select(
                        qEmployee.salary.avg().as("avg"),
                        qEmployee.salary.max().as("max")
                ).from(qEmployee)
                .fetchOne();

        Double avg = tuple.get(Expressions.numberPath(Double.class, "avg"));
        Double roundedAvg = (double) Math.round(avg);
        BigDecimal max = tuple.get(Expressions.numberPath(BigDecimal.class, "max"));

        //employee 테이블에서 salary가 평균과 최대값 사이인 직원의 정보를 가져옵니다.
        List<Tuple> tuples = jqf.select(
                        qEmployee.id.as("employee_id"),
                        qEmployee.firstName.concat(" ").concat(qEmployee.lastName).as("names"),
                        qEmployee.salary.as("salary"),
                        ExpressionUtils.as(Expressions.constant(roundedAvg), "avgs"),
                        ExpressionUtils.as(Expressions.constant(max), "maxs")
                )
                .from(qEmployee)
                .where(qEmployee.salary.between(avg, max))
                .fetch();

        tuples.forEach(t -> {
            System.out.println(t.get(Expressions.numberPath(Integer.class, "employee_id")));
            System.out.println(t.get(Expressions.stringPath("names")));
            System.out.println(t.get(Expressions.numberPath(BigDecimal.class, "salary")));
            System.out.println(t.get(Expressions.numberPath(Double.class, "avgs")));
            System.out.println(t.get(Expressions.numberPath(BigDecimal.class, "maxs")));
        });
        return tuples;
    }


    /**
     SELECT a.employee_id, a.first_name || ' ' || a.last_name names, a.salary,
     ROUND(b.avgs), b.maxs
     FROM HR.employees a,
     ( SELECT AVG(salary) avgs,
     MAX(salary) maxs
     FROM HR.employees ) b
     WHERE a.salary BETWEEN b.avgs AND b.maxs
     ORDER BY a.salary DESC;
     **/

    public List<TestDto> findProjectionSubQeury() {
        QEmployee qEmployee = QEmployee.employee;

        //employee 테이블에서 salary의 평균과 최대값을 구합니다.
        Tuple tuple = jqf.select(
                        qEmployee.salary.avg().as("avg"),
                        qEmployee.salary.max().as("max")
                ).from(qEmployee)
                .fetchOne();

        Double avg = tuple.get(Expressions.numberPath(Double.class, "avg"));
        Double roundedAvg = (double) Math.round(avg);
        BigDecimal max = tuple.get(Expressions.numberPath(BigDecimal.class, "max"));

        //employee 테이블에서 salary가 평균과 최대값 사이인 직원의 정보를 가져옵니다.
        List<TestDto> tuples = jqf.select(
                        Projections.constructor(
                                TestDto.class,
                                qEmployee.id.as("employee_id"),
                                qEmployee.firstName.concat(" ").concat(qEmployee.lastName).as("names"),
                                qEmployee.salary.as("salary"),
                                ExpressionUtils.as(Expressions.constant(roundedAvg), "avgs"),
                                ExpressionUtils.as(Expressions.constant(max), "maxs")
                        )

                )
                .from(qEmployee)
                .where(qEmployee.salary.between(avg, max))
                .fetch();

        tuples.forEach(t -> {
            System.out.println(t.getEmployee_id());
            System.out.println(t.getNames());
            System.out.println(t.getSalary());
            System.out.println(t.getAvgs());
            System.out.println(t.getMaxs());
        });
        return tuples;
    }

    /**
     update HR.EMPLOYEES
     set SALARY = (
     select max(salary)+1
     from HR.EMPLOYEES
     )
     where employee_id=131;
     */
    @Transactional
    public String updateSalaryById() {
        QEmployee qEmployee = QEmployee.employee;

        BigDecimal beforeSalary = jqf.select(qEmployee.salary.max()).from(qEmployee).where(qEmployee.id.eq(131)).fetchOne();
        BigDecimal add = beforeSalary.add(new BigDecimal(1));

        long execute = jqf.update(qEmployee)
                .set(qEmployee.salary, add)
                .where(qEmployee.id.eq(131))
                .execute();

        entityManager.flush();
        entityManager.clear();

        BigDecimal afterSalary =
                jqf.select(qEmployee.salary.max()).from(qEmployee).where(qEmployee.id.eq(131)).fetchOne();

        System.out.println("beforeSalary :" + beforeSalary);
        System.out.println("afterSalary :" + afterSalary);
        return beforeSalary.equals(afterSalary) ? "equal" : "different";
    }

    /**
     SELECT
         b.department_name,
         a.department_id,
         a.dept_sal
     FROM (
         SELECT
             department_id,
             SUM(salary) dept_sal
         FROM HR.employees
         GROUP BY department_id
         ORDER BY sum(salary) DESC
     ) a,
     HR.departments b
     WHERE a.department_id = b.department_id
     AND ROWNUM <= 3;
     **/
    /** 두개의 쿼리로 나눠서 처리 : N+1 문제 발생*/
    public List<TestDto2> findTop3Dept1(){
        QEmployee emp = QEmployee.employee;
        QDepartment dprtmnt = QDepartment.department;

        // 첫 번째 쿼리 실행
        List<TestDto2> salaries = jqf.select(
                    Projections.constructor(
                            TestDto2.class,
                            emp.department.id,
                            emp.salary.sum()
                    )
                )
                .from(emp)
                .groupBy(emp.department.id)
                .orderBy(emp.salary.sum().desc())
                .fetch();

        List<TestDto2> result = new ArrayList<>();
        for (TestDto2 salary : salaries) {
            // 두 번째 쿼리 실행
            String departmentName = jqf.select(dprtmnt.departmentName)
                    .from(dprtmnt)
                    .where(dprtmnt.id.eq(salary.getId()))
                    .fetchOne();

            result.add(new TestDto2(salary.getId(), salary.getSalary() , departmentName));

            // 결과가 3개가 되면 순회를 중단
            if (result.size() == 3) {
                break;
            }
        }
        return result;
    }

    /**
     SELECT
         b.department_name,
         a.department_id,
         a.dept_sal
     FROM (
         SELECT
             department_id,
             SUM(salary) dept_sal
         FROM HR.employees
         GROUP BY department_id
         ORDER BY sum(salary) DESC
     ) a,
     HR.departments b
     WHERE a.department_id = b.department_id
     AND ROWNUM <= 3;
     **/
    /** 두개의 쿼리로 나눠서 처리 */
    public List<TestDto2> findTop3Dept2(){
        QEmployee emp = QEmployee.employee;
        QDepartment dprtmnt = QDepartment.department;

        // 첫 번째 쿼리 실행 : 부서별 급여 합계를 구합니다.
        List<TestDto2> salaries = jqf.select(
                        Projections.constructor(
                                TestDto2.class,
                                emp.department.id,
                                emp.salary.sum()
                        )
                )
                .from(emp)
                .groupBy(emp.department.id)
                .orderBy(emp.salary.sum().desc())
                .fetch();

        // salaries에서 departmentId 가져오기
        List<Short> departmentIds = new ArrayList<>();
        for (TestDto2 salary : salaries) {
            departmentIds.add(salary.getId());
        }

        // 두번째 쿼리 실행 : 부서별 아이디, 이름을 가져옵니다.
        List<TestDto2> depts = jqf.select(
                        Projections.constructor(
                                TestDto2.class,
                                dprtmnt.id,
                                dprtmnt.departmentName
                        )
                )
                .from(dprtmnt)
                .where(dprtmnt.id.in
                        (departmentIds)
                ).fetch();


        // depts와 salaries를 조합하여 결과를 만듭니다.
        List<TestDto2> result = new ArrayList<>();
        for(TestDto2 salary : salaries) {
            for(TestDto2 dept : depts) {
                if (salary.getId().equals(dept.getId())) {
                    result.add(new TestDto2(salary.getId(), salary.getSalary(), dept.getDepartmentName()));
                }
            }
            if (result.size() == 3) {
                break;
            }
        }
        return result;
    }


    /**
     SELECT
         b.department_name,
         a.department_id,
         a.dept_sal
     FROM (
         SELECT
             department_id,
             SUM(salary) dept_sal
         FROM HR.employees
         GROUP BY department_id
         ORDER BY sum(salary) DESC
     ) a,
     HR.departments b
     WHERE a.department_id = b.department_id
     AND ROWNUM <= 3;
     **/

    // join으로 변경

    /**
     SELECT
     *
     FROM (
         SELECT
             d.department_name,
             e.department_id,
             SUM(e.salary) as dept_sal
         FROM
         HR.employees e
         JOIN
         HR.departments d ON e.department_id = d.department_id
         GROUP BY
         e.department_id, d.department_name
         ORDER BY
         SUM(e.salary) DESC
     )
     WHERE ROWNUM <= 3;
     **/

    /*subquery를 join으로 변경 해서 해결*/
    public List<Tuple> findTop3Dept3(){
        QEmployee emp = QEmployee.employee;
        QDepartment dprtmnt = QDepartment.department;

        List<Tuple> tuples = jqf.select(
                        dprtmnt.id.as("id"),
                        dprtmnt.departmentName.as("deptNm"),
                        emp.salary.sum().as("deptSal")
                )
                .from(emp)
                .groupBy(dprtmnt.id , dprtmnt.departmentName)
                .orderBy(emp.salary.sum().desc())
                .limit(3)
                .join(dprtmnt)
                .on(emp.department.id.eq(dprtmnt.id))
                .fetch();
        return tuples;
    }


    /**
     SELECT
             b.department_name,
             a.department_id,
             a.dept_sal
     FROM (
             SELECT
             department_id,
             SUM(salary) dept_sal
             FROM HR.employees
             GROUP BY department_id
             ORDER BY sum(salary) DESC
     ) a,
     HR.departments b
     WHERE a.department_id = b.department_id
     AND ROWNUM <= 3;
     **/
    /** @Subselect로 별도의 entity로 처리 */
    public List<TestDto2> findSubselect(){
        QDepartment dprtmnt = QDepartment.department;
        QEmployeeSubselect employeeSubselect = QEmployeeSubselect.employeeSubselect;

        List<TestDto2> depts = jqf.select(
                        Projections.constructor(
                                TestDto2.class,
                                dprtmnt.id,
                                employeeSubselect.deptSal,
                                dprtmnt.departmentName
                        )
                )
                .from(employeeSubselect)
                .limit(3)
                .join(dprtmnt)
                .where(employeeSubselect.departmentId.eq(dprtmnt.id))
                .fetch();

        return depts;
    }


}