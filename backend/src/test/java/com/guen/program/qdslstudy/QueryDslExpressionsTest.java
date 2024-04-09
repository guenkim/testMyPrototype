package com.guen.program.qdslstudy;

import com.guen.program.qdslstudy.model.dto.TestDTO4;
import com.guen.program.qdslstudy.model.dto.TestDto;
import com.guen.program.qdslstudy.model.dto.TestDto2;
import com.guen.program.qdslstudy.repository.ExpressionsRepo;
import com.guen.program.qdslstudy.repository.SubQueryRepo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class QueryDslExpressionsTest {


    @Autowired
    ExpressionsRepo expressionsRepo;

    /***************************************************************************
     * expressions 실습
     **************************************************************************/


    @Test
    public void espression연습(){
        List<TestDTO4> custormer = expressionsRepo.findTop3Dept4();
        custormer.forEach(c->{
            System.out.println(
                    "id :" + c.getId()
                            + " deptNm :" + c.getDeptNm()
                            + " deptSal :" + c.getDeptSal()
                            + " divide :" + c.getDivide()
                            + " salary_level :" + c.getSalary_level()
                            + " salary_info :" + c.getSalary_info()

            );
        });
    }

    @Test
    public void decode_caseExpressionTest(){
        expressionsRepo.findCaseDecodeGender()
                .forEach(
                        customerDTO -> {
                            System.out.println(
                                    "customer id :" + customerDTO.getCustomerId()
                                    +"customer name:" + customerDTO.getFirstNm()
                                    +"decode gender:" + customerDTO.getDecodeGender()
                                    +" case gender:" + customerDTO.getCaseGender()
                            );
                        }
                );
    }

}
