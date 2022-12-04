package example.todo.Domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @brief ToDo Entity
 * @details ToDo N:1 Member
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToDo {

    @Id @GeneratedValue @Column(name = "todo_id")
    private Long id;

    private String title;
    private String description;

    private Boolean isCompleted = false;

    private LocalDateTime createdDateTime;
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static ToDo createToDo(String title, String description, LocalDate dueDate, Member member) {
        ToDo toDo = new ToDo();
        toDo.title = title;
        toDo.description = description;
        toDo.createdDateTime = LocalDateTime.now();
        toDo.dueDate = dueDate;
        if (member != null) toDo.configMember(member);

        return toDo;
    }

    public void changeStatus() {
        this.isCompleted = !this.isCompleted;
    }

    public void update(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    /**
     * 연관관계 설정 메서드
     * @param member
     */
    private void configMember(Member member) {
        this.member = member;
        member.getToDoList().add(this);
    }

}

