package example.todo.service.memberService;

import example.todo.Domain.Member;
import example.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository repository;

    @Override
    @Transactional
    public Long save(Member member) {
        // loginId 중복 체크
        return repository.findByLoginId(member.getLoginId()).isEmpty() ? repository.save(member).getId() : null;
    }

    @Override
    public List<Member> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return repository.findById(id);
    }
}
