package example.todo.service.todoService;

import example.todo.Domain.ToDo;
import example.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
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
    @Transactional
    public void update(Long id, String title, String description, LocalDate dueDate) {
        Optional<ToDo> toDo = repository.findById(id);
        toDo.ifPresent(t -> t.update(title, description, dueDate));
    }

    @Override
    @Transactional
    public void changeStatus(Long id) {
        Optional<ToDo> findToDo = repository.findById(id);
        findToDo.ifPresent(toDo -> toDo.changeStatus());
    }

    @Override
    @Transactional
    public void delete(ToDo toDo) {
        repository.delete(toDo);
    }
}
