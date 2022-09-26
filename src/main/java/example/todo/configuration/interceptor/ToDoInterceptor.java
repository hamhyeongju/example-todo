package example.todo.configuration.interceptor;

import example.todo.service.todoService.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class ToDoInterceptor implements HandlerInterceptor {

    private final ToDoService toDoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        int pos = requestURI.lastIndexOf("/");
        Long toDoId = Long.parseLong(requestURI.substring(pos + 1));
        if (toDoService.findById(toDoId).isEmpty()) {
            response.sendRedirect("/todo");
            return false;
        }
        return true;
    }
}
