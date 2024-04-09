package com.guen.program.todo.repository.jpa.springdata;

import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import org.springframework.transaction.annotation.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(false)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoSpringDataRepositoryTest {
//
//    @Autowired
//    TodoSpringDataRepository springDataRepository;
//
//    @Test
//    @Order(1)
//    public void  todo목록조회(){
//        List<Todo> all = springDataRepository.findAll();
//        //Assertions.assertThat(all.size()).isEqualTo(10);
//    }
//
//
//    @Test
//    @Order(2)
//    public void  todo조회(){
//        Todo todo = springDataRepository.findById(58L)
//                .orElseGet(() -> {
//                    Todo newTodo = new Todo("제목", "없음", Complete.FALSE);
//                    springDataRepository.save(newTodo);
//                    return newTodo;
//                });
//
//        Assertions.assertThat(todo.getBody()).isEqualTo("본문6");
//    }
//
//
//    @Test
//    @Order(3)
//    public void  todo저장(){
//        springDataRepository.save(new Todo("일정 추가 함","본문",Complete.FALSE));
//    }
//
//    @Test
//    @Order(4)
//    public void  todo수정(){
//        springDataRepository.findById(57L)
//                .ifPresentOrElse(
//                        existTodo -> {
//                            // dirty checking
//                            existTodo.setSubject("제목..........");
//                            existTodo.setBody("본문.........");
//                            existTodo.setCompleted(Complete.FALSE);
//                            springDataRepository.save(existTodo);
//                        },
//                        () -> System.out.println("Todo with ID 57 not found.")
//                );
//    }
//
//    @Test
//    @Order(5)
//    public void  todo삭제(){
//        springDataRepository.findAll().stream()
//                .findFirst()
//                .ifPresent(firstTodo -> {
//                    springDataRepository.deleteById(firstTodo.getId());
//                    Assertions.assertThat(springDataRepository.findById(firstTodo.getId())).isEmpty();
//                });
//
//    }
//
//    @Test
//    @Order(6)
//    public void  todo완료여부변경(){
//        springDataRepository.findById(200L)
//                .ifPresent(entity -> {
//                    entity.setCompleted(Complete.TRUE);
//                    springDataRepository.save(entity);
//                });
//    }
}