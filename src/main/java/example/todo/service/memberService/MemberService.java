package example.todo.service.memberService;

import example.todo.Domain.Member;
import example.todo.controller.dto.MemberDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    Long save(Member member);

    List<Member> findAll();

    Optional<Member> findById(Long id);

    Optional<Member> findByLoginId(String loginId);
}
