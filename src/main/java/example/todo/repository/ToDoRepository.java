package example.todo.repository;

import example.todo.Domain.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    List<ToDo> findToDoListByMemberIdAndIsCompleted(Long memberId, Boolean isCompleted);

    List<ToDo> findSortByMemberIdAndIsCompleted(Long memberId, Boolean isCompleted, Sort sort);
}
