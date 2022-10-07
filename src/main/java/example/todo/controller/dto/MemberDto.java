package example.todo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Getter @Setter
public class MemberDto {
    @Length(max = 20, min = 5)
    @Pattern(regexp = "^[a-z0-9]*$")
    private String loginId;
    @Length(max = 20, min = 5)
    @Pattern(regexp = "^[a-z0-9]*$")
    private String password;
    private String checkPassword;
    @Length(max = 20, min = 2)
    @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-z0-9\\s]*$")
    private String name;
}
