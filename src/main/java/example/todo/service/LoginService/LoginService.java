package example.todo.service.LoginService;

import example.todo.Domain.Member;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
public interface LoginService {

    Optional<Member> login(String loginId, String password);
}
