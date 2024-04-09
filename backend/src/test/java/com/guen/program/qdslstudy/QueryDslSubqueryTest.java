package com.guen.program.qdslstudy;

import com.guen.program.qdslstudy.model.dto.EmployeeDTO;
import com.guen.program.qdslstudy.model.dto.TestDTO4;
import com.guen.program.qdslstudy.model.dto.TestDto2;
import com.guen.program.qdslstudy.repository.EmployeeRepo;
import com.guen.program.qdslstudy.repository.SubQueryRepo;
import com.guen.program.qdslstudy.model.dto.TestDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class QueryDslSubqueryTest {


    @Autowired
    SubQueryRepo subQueryRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    /***************************************************************************
     * subquery 실습
     **************************************************************************/
    @Test
    public void findJoinInfo(){
        List<Tuple> joinInfo = subQueryRepo.findJoinInfo();
        joinInfo.forEach((tuple)->{
            System.out.println("tuple {} :" + tuple);
        });
    }

    @Test
    public void findSubQeury(){
        List<Tuple> tuples = subQueryRepo.findSubQeury();
        tuples.forEach(tuple ->{
            System.out.println("tuple {} : " + tuple);
        });
    }

    @Test
    public void findSubQeury2(){
        List<Tuple> tuples = subQueryRepo.findSubQeury2();
        tuples.forEach(tuple ->{
            System.out.println(tuple.get(Expressions.numberPath(Integer.class, "employee_id")));
            System.out.println(tuple.get(Expressions.stringPath( "names")));
            System.out.println(tuple.get(Expressions.numberPath(BigDecimal.class, "salary")));
            System.out.println(tuple.get(Expressions.numberPath(Double.class, "avgs")));
            System.out.println(tuple.get(Expressions.numberPath(BigDecimal.class, "maxs")));
        });
    }

    @Test
    public void findProjectionSubQeury(){
        List<TestDto> tuples = subQueryRepo.findProjectionSubQeury();
        tuples.forEach(t ->{
            System.out.println(t.getEmployee_id());
            System.out.println(t.getNames());
            System.out.println(t.getSalary());
            System.out.println(t.getAvgs());
            System.out.println(t.getMaxs());
        });
    }

    @Test
    public void updateSalaryById(){
        String salary = subQueryRepo.updateSalaryById();
        System.out.println("값 비교 :"+ salary);
    }

    @Test
    public void findTop3Dept1(){
        List<TestDto2> custormer = subQueryRepo.findTop3Dept1();
        custormer.forEach(c->{
            System.out.println(
                  "id :" + c.getId()
                + " deptNm :" + c.getDepartmentName()
                + " deptSal :" + c.getSalary()
            );
        });
    }

    @Test
    public void findTop3Dept2(){
        List<TestDto2> custormer = subQueryRepo.findTop3Dept2();
        custormer.forEach(c->{
            System.out.println(
                    "id :" + c.getId()
                            + " deptNm :" + c.getDepartmentName()
                            + " deptSal :" + c.getSalary()
            );
        });
    }
    @Test
    public void findTop3Dept3(){
        List<Tuple> custormer = subQueryRepo.findTop3Dept3();
        custormer.forEach(c->{
            System.out.println(
                    "id :" + c.get(Expressions.numberPath(Short.class, "id"))
                            + " deptNm :" + c.get(Expressions.stringPath("deptNm"))
                            + " deptSal :" + c.get(Expressions.numberPath(BigDecimal.class,"deptSal"))
            );
        });
    }


    @Test
    public void findSubselect(){
        List<TestDto2> custormer = subQueryRepo.findSubselect();
        custormer.forEach(c->{
            System.out.println(
                    "id :" + c.getId()
                            + " deptNm :" + c.getDepartmentName()
                            + " deptSal :" + c.getSalary()
            );
        });
    }

    @Test
    public void nativeQuery(){
        List<EmployeeDTO> testDto2s = employeeRepo.findDepartmentInfo();
        testDto2s.forEach(c->{
            System.out.println(
                   "id :" + c.getId()
                + " deptNm :" + c.getDepartmentName()
                + " deptSal :" + c.getSalary()
            );
        });
    }

}
