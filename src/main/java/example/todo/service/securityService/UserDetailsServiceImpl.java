package example.todo.service.securityService;


import example.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @brief Spring-Security가 제공하는 로그인 관련 서비스
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository repository;

    /**
     * @brief username으로 Member 조회, 로그인 관련 로직에서 사용됨
     * @param username username means member.loginId
     * @return UserDetails(UserDetailsImpl) has Member
     * @throws UsernameNotFoundException
     */
    // id로 1차 조회 (2차는 provider에서 암호화된 비밀번호)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(repository.findByLoginId(username).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + username)));
    }

}
