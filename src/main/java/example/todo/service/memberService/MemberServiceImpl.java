package example.todo.service.memberService;

import example.todo.Domain.Member;
import example.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @brief Spring-Security 도입 전 사용하던 Member 관련 서비스
 * @deprecated Spring-Security 도입으로 MemberSecurityService 사용
 */
//@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    @Override
    @Transactional
    public Long save(Member member) {
        // loginId 중복 체크
        return repository.findByLoginId(member.getLoginId()).isEmpty() ? repository.save(member).getId() : null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return repository.findByLoginId(loginId);
    }
}
