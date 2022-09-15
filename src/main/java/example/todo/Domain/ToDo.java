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

    public ToDo (String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.createdDate = LocalDate.now();
        this.dueDate = dueDate;
    }

    public void changeStatus() {
        this.isCompleted = !this.isCompleted;
    }

}

