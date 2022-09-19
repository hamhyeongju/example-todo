package example.todo.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class LoginDto {
    @NotNull
    private String loginId;
    @NotNull
    private String password;
}
