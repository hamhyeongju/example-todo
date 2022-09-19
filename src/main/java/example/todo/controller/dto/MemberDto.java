package example.todo.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class MemberDto {
    @NotNull
    private String loginId;
    @NotNull
    private String password;
    @NotNull
    private String checkPassword;
    @NotNull
    private String name;
}
