package example.todo.service.memberService;

import example.todo.Domain.Member;
import example.todo.controller.dto.MemberDto;
import example.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public Long save(Member member) {
        // loginId 중복 체크
        return repository.findByLoginId(member.getLoginId()).isEmpty() ? repository.save(member).getId() : null;
    }

    @Override
    @Transactional
    public Long saveBySecurity(MemberDto memberDto) {
        return repository.findByLoginId
                (memberDto.getLoginId()).isEmpty() ? repository.save(
                new Member(
                        memberDto.getLoginId(),
                        bCryptPasswordEncoder.encode(memberDto.getPassword()),
                        memberDto.getName().strip())).getId() : null;
    }

    @Override
    public List<Member> findAll() {
        return repository.findAll();
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
