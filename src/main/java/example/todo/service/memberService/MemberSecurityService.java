package example.todo.service.memberService;

import example.todo.Domain.Member;
import example.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @brief Spring-Security 전용 Member 관련 기능 담당
 * @field BCryptPasswordEncoder : Spring-Security가 제공하는 암호화 객체
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSecurityService  implements MemberService {

    private final MemberRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * @brief Member.password 암호화하여 Member 저장
     * @return member_id
     */
    @Override
    @Transactional
    public Long save(Member member) {
        member.setEncodingPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        // loginId 중복 체크
        return repository.existsByLoginId(member.getLoginId()) ?  null : repository.save(member).getId();
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
