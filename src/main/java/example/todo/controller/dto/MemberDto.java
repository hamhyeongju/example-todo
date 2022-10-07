package example.todo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Getter @Setter
public class MemberDto {
    @Length(max = 20, min = 5, message = "5~20자의 영문 소문자, 숫자만 사용 가능합니다. 공백은 허용되지 않습니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "5~20자의 영문 소문자, 숫자만 사용 가능합니다. 공백은 허용되지 않습니다.")
    private String loginId;
    @Length(max = 20, min = 5, message = "5~20자의 영문 소문자, 숫자만 사용 가능합니다. 공백은 허용되지 않습니다.")
    @Pattern(regexp = "^[a-z0-9]*$", message = "5~20자의 영문 소문자, 숫자만 사용 가능합니다. 공백은 허용되지 않습니다.")
    private String password;
    private String checkPassword;
    @Length(max = 20, min = 2, message = "2~20자의 영문 소문자, 숫자, 한글만 사용 가능합니다. 앞뒤의 공백은 제거됩니다.")
    @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-z0-9\\s]*$", message = "2~20자의 영문 소문자, 숫자, 한글만 사용 가능합니다. 앞뒤의 공백은 제거됩니다.")
    private String name;
}
