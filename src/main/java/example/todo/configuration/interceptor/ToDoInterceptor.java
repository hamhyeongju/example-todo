package example.todo.configuration.interceptor;

import example.todo.Domain.Member;
import example.todo.Domain.ToDo;
import example.todo.service.securityService.UserDetailsImpl;
import example.todo.service.todoService.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
public class ToDoInterceptor implements HandlerInterceptor {

    private final ToDoService toDoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
//        Member loginMember = (Member) request.getSession(false).getAttribute("loginMember");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        Member loginMember = userDetails.getMember();

        int pos = requestURI.lastIndexOf("/");
        Long toDoId = Long.parseLong(requestURI.substring(pos + 1));
        Optional<ToDo> findById = toDoService.findById(toDoId);
        if (findById.isEmpty() || !findById.get().getMember().getId().equals(loginMember.getId())) {
            response.sendRedirect("/todo");
            return false;
        }
        return true;
    }
}
