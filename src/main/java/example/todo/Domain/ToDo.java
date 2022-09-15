package example.todo.Domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToDo {

    @Id @GeneratedValue @Column(name = "todo_id")
    private Long id;

    private String title;
    private String description;

    private Boolean isCompleted = false;

    private LocalDate createdDate;
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static ToDo createToDo(String title, String description, LocalDate dueDate, Member member) {
        ToDo toDo = new ToDo();
        toDo.title = title;
        toDo.description = description;
        toDo.createdDate = LocalDate.now();
        toDo.dueDate = dueDate;
        if (member != null) toDo.configMember(member);

        return toDo;
    }

    public void changeStatus() {
        this.isCompleted = !this.isCompleted;
    }

    private void configMember(Member member) {
        this.member = member;
        member.getToDoList().add(this);
    }

}

