package example.todo.service.LoginService;

import example.todo.Domain.Member;
import example.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final MemberRepository repository;

    @Override
    public Optional<Member> login(String loginId, String password) {
        Optional<Member> findMember = repository.findByLoginId(loginId);
        return findMember.filter(member -> member.getPassword().equals(password));
    }
}
