package com.guen.program.qdslstudy.repository;

import com.guen.program.qdslstudy.model.dto.CustomerDTO;
import com.guen.program.qdslstudy.model.dto.TestDTO4;
import com.guen.program.qdslstudy.model.dto.TestDto;
import com.guen.program.qdslstudy.model.dto.TestDto2;
import com.guen.program.qdslstudy.model.entity.*;
import com.guen.program.qdslstudy.model.entity.oe.QCustomer;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpressionsRepo {

    private JPAQueryFactory jqf;

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        jqf = new JPAQueryFactory(entityManager);
        this.entityManager = entityManager;
    }

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
    /*Expression 실습 포함*/
    public List<TestDTO4> findTop3Dept4(){
        QEmployee emp = QEmployee.employee;
        QDepartment dprtmnt = QDepartment.department;

        // 복잡한 수학 연산 사용하기
        NumberExpression<BigDecimal> divideExpression = Expressions.numberTemplate(BigDecimal.class,
                "( {0} / {1} )", emp.salary, emp.salary.sum()).as("divide");

        //  CASE 문 사용하기
        StringExpression caseExpression = new CaseBuilder()
                .when(emp.salary.lt(30000)).then("적어")
                .when(emp.salary.between(30000, 40000)).then("적당해")
                .otherwise("많아").as("salary_level");

        // 문자열 연결
        StringExpression stringExpression = Expressions.stringTemplate("CONCAT({0}, ' ', {1})", emp.salary, emp.salary.sum()).as("salary_info");

        List<TestDTO4> tuples = jqf.select(
                        Projections.constructor
                                (
                                        TestDTO4.class,
                                        dprtmnt.id.castToNum(Integer.class).as("id"),
                                        dprtmnt.departmentName.as("deptNm"),
                                        emp.salary.sum().castToNum(Integer.class).as("deptSal"),
                                        // 복잡한 수학 연산 사용하기
                                        divideExpression,
                                        // CASE 문 사용하기
                                        caseExpression,
                                        // 문자열 연결
                                        stringExpression
                                )
                )
                .from(emp)
                .groupBy(dprtmnt.id , dprtmnt.departmentName , emp.salary)
                .orderBy(emp.salary.sum().desc())
                .limit(3)
                .join(dprtmnt)
                .on(emp.department.id.eq(dprtmnt.id))
                .fetch();
        return tuples;
    }

    /**
     SELECT
         customer_id,
         cust_first_name,
         DECODE(gender, 'M', '남성', 'F', '여성') decode_gender,
         CASE gender WHEN 'M' THEN '남성'
                    WHEN 'F' THEN '여성'
         ELSE '' END case_gender
     FROM OE.customers;
     **/
    public List<CustomerDTO> findCaseDecodeGender(){
        QCustomer  csmr = QCustomer.customer;

        StringExpression decodeGender = Expressions.stringTemplate("DECODE({0},'M' ,'남성','F' , '여성')", csmr.gender).as("decodeGender");
        StringExpression caseGender = new CaseBuilder()
                .when(csmr.gender.eq("M")).then("남성")
                .otherwise("여성").as("caseGender");

       return jqf.select(
                       Projections.constructor(
                               CustomerDTO.class,
                               csmr.id,
                               csmr.custFirstName,
                               decodeGender,
                               caseGender
                       )
                ).from(csmr)
                .fetch();

    }


}