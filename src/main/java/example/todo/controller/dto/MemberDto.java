package example.todo.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Pattern;

/**
 * @breif 회원 가입 시 사용되는 dto
 * @details Bean Validation 으로 값 검증
 */
@Getter @Setter
public class MemberDto {
    /**
     * 5~20 길이 제한, 영소문자, 숫자만 허용
     */
    @Length(max = 20, min = 5)
    @Pattern(regexp = "^[a-z0-9]*$")
    private String loginId;

    /**
     * 5~20 길이 제한, 영소문자, 숫자만 허용
     */
    @Length(max = 20, min = 5)
    @Pattern(regexp = "^[a-z0-9]*$")
    private String password;
    private String checkPassword;

    /**
     * 2~20 길이 제한, 한글, 영소문자, 숫자만 허용
     */
    @Length(max = 20, min = 2)
    @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣a-z0-9\\s]*$")
    private String name;
}
