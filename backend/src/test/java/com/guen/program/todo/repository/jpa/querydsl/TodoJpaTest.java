package com.guen.program.todo.repository.jpa.querydsl;

import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoJpaTest {
//
//    @Autowired
//    TodoJpa todoJpa;
//
//
//    @Test
//    @Order(1)
//    public void  todo목록조회(){
//        List<Todo> all = todoJpa.findAll();
//        //Assertions.assertThat(all.size()).isEqualTo(9);
//    }
//
//
//    @Test
//    @Order(2)
//    public void  todo조회(){
//        Todo todo = todoJpa.findById(58L)
//                .orElseGet(() -> {
//                    Todo newTodo = new Todo("제목", "없음", Complete.FALSE);
//                    todoJpa.save(newTodo);
//                    return newTodo;
//                });
//
//        Assertions.assertThat(todo.getBody()).isEqualTo("본문6");
//    }

}