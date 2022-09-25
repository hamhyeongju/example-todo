package example.todo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
public class ToDoDto {

    private Long id;
    @NotEmpty
    private String title;
    private String description;
    private Boolean isCompleted = false;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    public ToDoDto(Long id, String title, String description, Boolean isCompleted, LocalDate createdDate, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
    }
}
