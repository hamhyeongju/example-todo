package example.todo.service.todoService;

import example.todo.Domain.ToDo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ToDoService {

    Long save(ToDo toDo);

    Optional<ToDo> findById(Long id);

    List<ToDo> findAll();

    void delete(ToDo toDo);

}
