package com.guen.program.qdslstudy;

import com.guen.program.qdslstudy.model.dto.TestDTO3;
import com.guen.program.qdslstudy.repository.UnionRepo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class QueryDslUnionTest {


    @Autowired
    UnionRepo unionRepo;

    /***************************************************************************
     * union 실습
     **************************************************************************/
    @Test
    public void findCustormer(){
        List<Tuple> custormer = unionRepo.findCustormer();
        custormer.forEach(c->{
            System.out.println(
                    "id :" + c.get(Expressions.numberPath(Integer.class, "id"))
                            + " firstNm :" + c.get(Expressions.stringPath("firstNm"))
                            + " lastNm :" + c.get(Expressions.stringPath("lastNm"))
            );
        });
    }

    @Test
    public void findUnion(){
        List<TestDTO3> emp = unionRepo.findUnion();
        emp.forEach(employee->{
            System.out.println(
                    "id :" + employee.getId()
                    + " total :" + employee.getTotal()
            );
        });
    }

}
