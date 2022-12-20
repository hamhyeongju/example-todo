package example.todo.repository;

import example.todo.Domain.Member;
import example.todo.Domain.ToDo;
import example.todo.service.memberService.MemberService;
import example.todo.service.memberService.MemberServiceImpl;
import example.todo.service.todoService.ToDoServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Rollback(value = false)
class ToDoRepositoryTest {

    @Autowired
    MemberService memberService;
    @Autowired
    ToDoServiceImpl toDoService;

    @Test
    @Transactional
    public void save() throws Exception {
        //given
        Member member1 = new Member("111", "456", "member1");
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
        Member member1 = new Member("222", "456", "member1");
        ToDo toDo = ToDo.createToDo("title", "description", null, member1);
        memberService.save(member1);
        Long savedToDoId = toDoService.save(toDo);

        //when
        toDoService.delete(toDo.getId());

        //then
        Optional<ToDo> findToDo = toDoService.findById(savedToDoId);
        assertThat(findToDo.isEmpty()).isTrue();
    }

    @Test
    public void update() throws Exception {
        //given
        Member member1 = new Member("333", "456", "member1");
        ToDo toDo = ToDo.createToDo("title", "description", null, member1);
        memberService.save(member1);
        Long savedToDoId = toDoService.save(toDo);

        //when
        toDoService.update(savedToDoId, "new title", "new description",
                LocalDate.of(2023, 11, 9));
        toDoService.changeStatus(savedToDoId);

        //then
        Optional<ToDo> findToDo = toDoService.findById(savedToDoId);
        assertThat(findToDo.map(t -> t.getTitle()).orElse("")).isEqualTo("new title");
        assertThat(findToDo.map(t -> t.getDescription()).orElse("")).isEqualTo("new description");
        assertThat(findToDo.map(t -> t.getDueDate()).orElse(null)).isEqualTo(
                LocalDate.of(2023, 11, 9)
        );

        assertThat(findToDo.map(t -> t.getIsCompleted()).orElse(false)).isTrue();
    }

}