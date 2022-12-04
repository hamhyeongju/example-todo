package example.todo.service.memberService;

import example.todo.Domain.Member;

import java.util.Optional;

/**
 * @brief 회원 CRUD 관련 서비스
 * @details 회원 가입, 회원 조회 기능
 */
public interface MemberService {

    /**
     * 회원 가입
     */
    Long save(Member member);

    /**
     * 회원 조회
     * @param id Member pk
     */
    Optional<Member> findById(Long id);

    /**
     * @deprecated loginId로 회원을 조회하는 기능은 Spring-Security가 담당
     */
    Optional<Member> findByLoginId(String loginId);
}
