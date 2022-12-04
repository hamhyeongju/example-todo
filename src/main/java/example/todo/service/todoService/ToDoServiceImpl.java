package example.todo.service.todoService;

import example.todo.Domain.ToDo;
import example.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

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

    /**
     * @brief ToDo 수정
     * @param id todo pk
     * @param title modified title
     * @param description modified description
     * @param dueDate modified dueDate
     */
    @Override
    @Transactional
    public void update(Long id, String title, String description, LocalDate dueDate) {
        Optional<ToDo> toDo = repository.findById(id);
        toDo.ifPresent(t -> t.update(title, description, dueDate));
    }

    /**
     * @brief ToDo 완료 상태 변경
     * @param id
     */
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

    /**
     * @brief 회원이 가진 ToDo를 list로 조회
     * @deprecated 정렬 기능이 포함된 findSortByMemberIdAndIsCompleted() 로 대체
     */
    @Override
    public List<ToDo> findToDoListByMemberIdAndIsCompleted(Long id, Boolean isCompleted) {
        return repository.findToDoListByMemberIdAndIsCompleted(id, isCompleted);
    }

    /**
     * @brief 회원이 가진 ToDo를 정렬하여 list로 조회
     * @deprecated Spring Data JPA 쿼리 메서드 사용 시 Join 발생하여 findJPQLByMemberIdAndIsCompleted() 로 대체
     */
    @Override
    public List<ToDo> findSortByMemberIdAndIsCompleted(Long id, Boolean isCompleted) {
        return repository.findSortByMemberIdAndIsCompleted(id, isCompleted,
                Sort.by(Sort.Direction.ASC, "dueDate", "createdDateTime"));
    }

    /**
     * @brief 회원이 가진 ToDo를 정렬하여 JPQL로 조회
     */
    @Override
    public List<ToDo> findJPQLByMemberIdAndIsCompleted(Long id, Boolean isCompleted) {
        return repository.findJPQLByMemberIdAnAndIsCompleted(id, isCompleted);
    }
}
