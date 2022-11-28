package example.todo.configuration;

import example.todo.configuration.interceptor.LoginInterceptor;
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

@Configuration
@RequiredArgsConstructor
public class Configurer implements WebMvcConfigurer {

    private final ToDoService toDoService;

    /**
     * spring security
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable().authorizeRequests()
                .antMatchers("/", "/login", "/add", "/error", "/css/**", "/js/**").permitAll()
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers();
    }

    @Bean // 비밀번호 암호화 할때 사용할 BCrypthPasswordEncoder 를 빈으로 등록
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/login", "/logout", "/add", "/error", "/css/**", "/js/**");

        registry.addInterceptor(new ToDoInterceptor(toDoService))
                .order(2)
                .addPathPatterns("/todo/update/**", "/todo/change/**", "/todo/delete/**");
    }
}
