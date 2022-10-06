package example.todo.service.LoginService;

import example.todo.Domain.Member;

import java.util.Optional;

public interface LoginService {

    Optional<Member> login(String loginId, String password);
}
