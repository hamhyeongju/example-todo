package example.todo.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @brief 로그인 시 사용되는 dto
 * @field username means member.loginId
 */
@Getter @Setter
public class LoginDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
