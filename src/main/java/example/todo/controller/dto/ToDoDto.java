package example.todo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class ToDoDto {

    private Long id;
    @NotEmpty
    private String title;
    @Length(max = 100, message = "최대 100자까지 가능합니다")
    private String description;
    private Boolean isCompleted;
    private LocalDateTime createdDateTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd") @FutureOrPresent(message = "오늘 이전의 날짜는 선택할 수 없습니다.")
    private LocalDate dueDate;

    public ToDoDto(Long id, String title, String description, Boolean isCompleted, LocalDateTime createdDateTime, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.createdDateTime = createdDateTime;
        this.dueDate = dueDate;
    }
}
