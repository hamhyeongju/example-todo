package example.todo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @brief ToDo 생성, 수정 시 사용되는 dto
 * @details Bean Validation 으로 값 검증
 */
@Getter @Setter
public class ToDoDto {

    private Long id;
    @Length(max = 20, min = 1, message = "{required.toDoDto.title}")
    private String title;
    @Length(max = 100, message = "{required.toDoDto.description}")
    private String description;
    private Boolean isCompleted;
    private LocalDateTime createdDateTime;

    /**
     * @FutureOrPresent : 과거의 날짜는 선택할 수 없음
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd") @FutureOrPresent(message = "{required.toDoDto.dueDate}")
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
