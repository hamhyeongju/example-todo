package example.todo.repository;

import example.todo.Domain.Member;
import example.todo.Domain.ToDo;
import example.todo.service.memberService.MemberServiceImpl;
import example.todo.service.todoService.ToDoServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(value = false)
class ToDoRepositoryTest {

    @Autowired
    MemberServiceImpl memberService;
    @Autowired
    ToDoServiceImpl toDoService;

    @Test
    @Transactional
    public void save() throws Exception {
        //given
        Member member1 = new Member("123", "456", "member1");
        ToDo toDo = ToDo.createToDo("title", "description", null, member1);

        //when
        memberService.save(member1);
        Long savedToDoId = toDoService.save(toDo);
        Optional<ToDo> findToDo = toDoService.findById(savedToDoId);
        Member findMember = findToDo.get().getMember();

        //then
        assertThat(savedToDoId).isEqualTo(toDo.getId());
        assertThat(findMember).isEqualTo(member1);
    }

    @Test
    public void delete() throws Exception {
        //given
        Member member1 = new Member("123", "456", "member1");
        ToDo toDo = ToDo.createToDo("title", "description", null, member1);
        memberService.save(member1);
        Long savedToDoId = toDoService.save(toDo);

        //when
        toDoService.delete(toDo);

        //then
        Optional<ToDo> findToDo = toDoService.findById(savedToDoId);
        assertThat(findToDo.isEmpty()).isTrue();
    }

}