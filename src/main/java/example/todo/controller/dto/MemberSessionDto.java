package example.todo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @brief Session에 저장을 위해 사용하는 dto
 * @deprecated Spring-Security 도입으로 기능 위임
 */
@Getter
@AllArgsConstructor
public class MemberSessionDto {
    private String loginId;
    private String password;
    private String name;
}
