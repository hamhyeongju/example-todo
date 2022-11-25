package example.todo.configuration.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private final UserDetailsService service;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // DB 에서 사용자 정보가 실제로 유효한지 확인 후 인증된 Authentication 리턴
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication; // AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        String username = token.getName();
        String password = (String) token.getCredentials(); // UserDetailsService를 통해 DB에서 아이디로 사용자 조회

        UserDetailsImpl loginMember = (UserDetailsImpl) service.loadUserByUsername(username);

        if (!bCryptPasswordEncoder.matches(password, loginMember.getPassword())) {
            throw new BadCredentialsException(loginMember.getUsername() + " 비밀번호를 확인해주세요.");
        }

        return new UsernamePasswordAuthenticationToken(loginMember, password, loginMember.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

