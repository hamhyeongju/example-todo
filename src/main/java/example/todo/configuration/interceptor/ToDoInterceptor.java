package example.todo.configuration.interceptor;

import example.todo.Domain.ToDo;
import example.todo.service.todoService.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
public class ToDoInterceptor implements HandlerInterceptor {

    private final ToDoService toDoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long toDoId = Long.parseLong(request.getParameter("id"));
        if (toDoService.findById(toDoId).isEmpty()) {
            response.sendRedirect("/todo");
            return false;
        }
        return true;
    }
}
