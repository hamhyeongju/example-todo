package example.todo.service.memberService;

import example.todo.Domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
public interface MemberService {

    Long save(Member member);

    List<Member> findAll();

    Optional<Member> findById(Long id);
}
