package com.guen.program.todo.repository.jpa.pure;

import com.guen.program.todo.model.entity.Todo;
import com.guen.program.todo.model.enumclass.Complete;
import com.guen.program.todo.model.request.TodoReq;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.*;
import org.springframework.transaction.annotation.Transactional;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoPureRepositoryTest {
//
//    @Autowired
//    TodoPureRepository pureRepo;
//
//    /**
//    @BeforeEach
//    public void before() {
//        IntStream.range(0,10)
//                .mapToObj(i->new Todo("제목"+i,"본문"+i, Complete.FALSE))
//                .peek(t-> System.out.println( t.getCompleted()))
//                .forEach(
//                        t -> pureRepo.save(t)
//                );
//    }
//    **/
//
//    @Test
//    @Order(1)
//    public void  todo목록조회(){
//        List<Todo> all = pureRepo.findAll();
//        //Assertions.assertThat(all.size()).isEqualTo(10);
//    }
//
//
//    @Test
//    @Order(2)
//    public void  todo조회(){
//        Optional<Todo> optionalTodo = pureRepo.findById(58L);
//        if(optionalTodo.isPresent()){
//            Assertions.assertThat(optionalTodo.get().getBody()).isEqualTo("본문6");
//        }else{
//            Todo todo = optionalTodo.orElse(new Todo( "제목", "없음", Complete.FALSE));
//            Assertions.assertThat(todo.getBody()).isEqualTo("없음");
//        }
//    }
//
//
//    @Test
//    @Order(3)
//    public void  todo저장(){
//        Todo todo = new Todo("일정 추가 함","본문",Complete.FALSE);
//        pureRepo.save(todo);
//
//    }
//
//    @Test
//    @Order(4)
//    public void  todo수정(){
//        Todo todo = new Todo(57L,"제목 수정","본문 수정",Complete.TRUE);
//        pureRepo.updateById(todo);
//    }
//
//    @Test
//    @Order(5)
//    public void  todo삭제(){
//        Optional<Todo> optionalTodo = pureRepo.findById(61L);
//        if(optionalTodo.isPresent()){
//           pureRepo.remove(optionalTodo.get());
//        }
//    }
//
//    @Test
//    @Order(6)
//    public void  todo완료여부변경(){
//        pureRepo.updateCompleteById(59L,Complete.TRUE);
//    }


}