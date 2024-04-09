package com.guen.program.qdslstudy.repository;

import com.guen.program.qdslstudy.model.dto.TestDTO3;
import com.guen.program.qdslstudy.model.dto.TestDto2;
import com.guen.program.qdslstudy.model.entity.QDepartment;
import com.guen.program.qdslstudy.model.entity.QEmployee;
import com.guen.program.qdslstudy.model.entity.QJob;
import com.guen.program.qdslstudy.model.entity.QLocation;
import com.guen.program.qdslstudy.model.entity.oe.QCustomer;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UnionRepo {

    private JPAQueryFactory jqf;

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        jqf = new JPAQueryFactory(entityManager);
        this.entityManager = entityManager;
    }

     /**
     SELECT
            emp.employee_id,
            emp.first_name,
            emp.last_name
     FROM HR.employees emp,
     HR.departments dep
     WHERE emp.department_id = dep.department_id
     AND dep.location_id in
      (
        SELECT
                loc.location_id
        FROM HR.locations loc
        WHERE loc.country_id = 'DE'
      )
     UNION
     SELECT
            customer_id,
            cust_first_name,
            cust_last_name
     FROM OE.customers
     WHERE nls_territory = 'AMERICA'
     ORDER BY 2;
     **/
    public List<Tuple> findCustormer(){
        QEmployee emp = QEmployee.employee;
        QDepartment dprtmnt = QDepartment.department;
        QLocation lctn = QLocation.location;
        QCustomer cstmr = QCustomer.customer;

        List<Tuple> retTuples = new ArrayList<>();

        // DE에 사는 직원을 구하라
        List<Tuple> emplys = jqf.select(
                        emp.id.as("id"),
                        emp.firstName.as("firstNm"),
                        emp.lastName.as("lastNm")
                )
                .from(emp)
                .join(dprtmnt)
                .on(emp.department.id.eq(dprtmnt.id))
                .where(dprtmnt.location.id.in(
                        JPAExpressions.select(lctn.id)
                                .from(lctn)
                                .where(lctn.country.countryId.eq("DE"))
                )).fetch();
        retTuples.addAll(emplys);

        // america에 사는 고객을 구하라.
        List<Tuple> cstrms = jqf
                .select(
                        cstmr.id.as("id"),
                        cstmr.custFirstName.as("firstNm"),
                        cstmr.custLastName.as("lastNm")
                )
                .from(cstmr)
                .where(cstmr.nlsTerritory.eq("AMERICA"))
                //.orderBy(cstmr.custFirstName.asc())
                .fetch();
        retTuples.addAll(cstrms);
        //firstNm 기준으로 오름차순 정렬
        retTuples.sort((o1, o2) ->
                {
                   return  o1.get(Expressions.stringPath("firstNm"))
                           .compareTo(o2.get(Expressions.stringPath("firstNm")));
                }
        );
        return retTuples;
    }
    /**
     SELECT
         to_char(job.job_id) AS id,
         SUM(employee.salary) AS total
     FROM
     HR.Employees employee
     JOIN
     HR.Jobs job ON employee.job_id = job.job_id
     GROUP BY
     job.job_id

     UNION

     SELECT
         to_char(department.department_id) AS id,
         COUNT(employee.employee_id) AS total
     FROM
     HR.Employees employee
     JOIN
     HR.DEPARTMENTS department ON employee.department_id = department.department_id
     GROUP BY
     department.department_id
     ORDER BY
     id;
     **/

    public List<TestDTO3> findUnion(){
        QEmployee emp = QEmployee.employee;
        QJob job = QJob.job;
        QDepartment dept = QDepartment.department;

        List<TestDTO3> retRes = new ArrayList<>();

        // 첫번째 쿼리 실행
        List<TestDTO3> fetch1 = jqf.select(
                        Projections.constructor(
                                TestDTO3.class,
                                job.jobId,
                                emp.salary.sum().castToNum(BigInteger.class)
                        )
                ).from(emp)
                .join(job)
                .on(emp.job.jobId.eq(job.jobId))
                .groupBy(job.jobId)
                .fetch();

        // 두번째 쿼리 실행
        List<TestDTO3> fetch2 = jqf.select(
                        Projections.constructor(
                                TestDTO3.class,
                                dept.id.stringValue(),
                                emp.id.count().castToNum(BigInteger.class)
                        )
                ).from(emp)
                .join(dept)
                .on(emp.department.id.eq(dept.id))
                .groupBy(dept.id)
                .fetch();

        //컬렉션에 추가한다.
        retRes.addAll(fetch1);
        retRes.addAll(fetch2);

        // id 기준 올림차순
        retRes.sort((o1,o2)->{
            return o1.getId().compareTo(o2.getId());
        });

        return retRes;
    }
}
