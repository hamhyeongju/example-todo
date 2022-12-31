package example.todo.configuration;

import example.todo.configuration.interceptor.ToDoInterceptor;
import example.todo.service.todoService.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @brief 설정 정보 담당 클래스
 */
@Configuration
@RequiredArgsConstructor
public class Configurer implements WebMvcConfigurer {

    private final ToDoService toDoService;

    /**
     * @brief Spring-Security 설정
     * @details 필수 인증 URL 설정, 로그인 페이지 및 성공 시 redirect 경로 설정,
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/add", "/error").permitAll()
                .antMatchers("/todo/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/todo")
                .and()
                .logout()
                .logoutSuccessUrl("/");

        return http.build();
    }

    /**
     * @brief Spring-Security Filter 를 거치지 않는 경로 설정
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**");
    }

    /**
     * @brief 비밀번호 암호화 할때 사용할 BCrypthPasswordEncoder 를 빈으로 등록
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @brief 인터셉터 등록
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ToDoInterceptor(toDoService))
                .order(2)
                .addPathPatterns("/todo/update/**", "/todos/{id}");
    }
}
