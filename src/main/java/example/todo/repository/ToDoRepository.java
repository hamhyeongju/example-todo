package example.todo.repository;

import example.todo.Domain.ToDo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    List<ToDo> findToDoListByMemberIdAndIsCompleted(Long memberId, Boolean isCompleted);

    List<ToDo> findSortByMemberIdAndIsCompleted(Long memberId, Boolean isCompleted, Sort sort);

    @Query("select t from ToDo t where t.member.id = :memberId and t.isCompleted = :isCompleted order by t.dueDate nulls last, t.createdDateTime nulls last")
    List<ToDo> findJPQLByMemberIdAnAndIsCompleted(@Param("memberId") Long memberId, @Param("isCompleted") Boolean isCompleted);
}
