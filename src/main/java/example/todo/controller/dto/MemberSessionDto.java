package example.todo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSessionDto {
    private String loginId;
    private String password;
    private String name;
}
