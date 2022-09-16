package example.todo.service.todoService;

import example.todo.Domain.ToDo;
import example.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService{

    private final ToDoRepository repository;

    @Override
    @Transactional
    public Long save(ToDo toDo) {
        return repository.save(toDo).getId();
    }

    @Override
    public Optional<ToDo> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ToDo> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(ToDo toDo) {
        repository.delete(toDo);
    }
}
