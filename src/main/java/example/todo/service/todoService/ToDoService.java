package example.todo.service.todoService;

import example.todo.Domain.ToDo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ToDoService {

    Long save(ToDo toDo);

    Optional<ToDo> findById(Long id);

    List<ToDo> findAll();

    void update(Long id, String title, String description, LocalDate dueDate);

    void changeStatus(Long id);

    void delete(ToDo toDo);

    List<ToDo> findToDoListByMemberIdAndIsCompleted(Long id, Boolean isCompleted);

    List<ToDo> findSortByMemberIdAndIsCompleted(Long id, Boolean isCompleted);

    List<ToDo> findJPQLByMemberIdAndIsCompleted(Long id, Boolean isCompleted);


}
