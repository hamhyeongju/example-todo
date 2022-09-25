package example.todo.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class LoginDto {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
}
