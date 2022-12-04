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

    /**
     * @brief todo의 fk(member_id)와 isCompleted 여부로 todo list 반환
     * @sort 1. dueDate acs 2. createdDateTime acs, nulls last
     * @param memberId : todo의 fk
     * @param isCompleted : todo 완료 여부, true : 완료됨
     * @return
     */
    @Query("select t from ToDo t where t.member.id = :memberId and t.isCompleted = :isCompleted order by t.dueDate nulls last, t.createdDateTime nulls last")
    List<ToDo> findJPQLByMemberIdAnAndIsCompleted(@Param("memberId") Long memberId, @Param("isCompleted") Boolean isCompleted);
}
