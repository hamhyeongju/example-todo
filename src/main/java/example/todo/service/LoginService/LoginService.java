package example.todo.service.LoginService;

import example.todo.Domain.Member;

import java.util.Optional;

/**
 * @brief 로그인 기능을 담당
 * @deprecated Spring-Security를 도입하며 로그인 기능을 위임,
 */
public interface LoginService {

    Optional<Member> login(String loginId, String password);
}
