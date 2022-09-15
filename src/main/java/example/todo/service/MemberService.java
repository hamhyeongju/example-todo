package example.todo.service;

import example.todo.Domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    Long save(Member member);

    List<Member> findAll();

    Member findById(Long id);
}
