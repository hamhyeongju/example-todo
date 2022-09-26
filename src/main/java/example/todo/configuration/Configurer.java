package example.todo.configuration;

import example.todo.configuration.interceptor.LoginInterceptor;
import example.todo.configuration.interceptor.ToDoInterceptor;
import example.todo.service.todoService.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class Configurer implements WebMvcConfigurer {

    private final ToDoService toDoService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/add", "/error");

        registry.addInterceptor(new ToDoInterceptor(toDoService))
                .order(2)
                .addPathPatterns("/todo/update/**", "/todo/change/**", "/todo/delete/**");
    }
}
