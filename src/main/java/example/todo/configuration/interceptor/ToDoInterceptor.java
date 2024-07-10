package example.todo.configuration.interceptor;

import example.todo.Domain.Member;
import example.todo.Domain.ToDo;
import example.todo.service.securityService.UserDetailsImpl;
import example.todo.service.todoService.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @brief 자신의 ToDo에만 관여할 수 있도록 하는 인터셉터
 * @details member의 pk와 todo의 fk가 일치하는 경우에만 수정, 삭제 가능
 */
@RequiredArgsConstructor
public class ToDoInterceptor implements HandlerInterceptor {

    private final ToDoService toDoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
//        Member loginMember = (Member) request.getSession(false).getAttribute("loginMember");

        // SecurityContextHolder로 부터 사용자 정보를 얻음
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        Member loginMember = userDetails.getMember();

        // 요청 uri에서 id 값을 파싱
        int pos = requestURI.lastIndexOf("/");
        Long toDoId = Long.parseLong(requestURI.substring(pos + 1));

        Optional<ToDo> findById = toDoService.findById(toDoId);

        // 파싱한 id 값의 ToDo가 없거나 member의 pk != todo의 fk 일 경우 false 반환
        if (findById.isEmpty() || !findById.get().getMember().getId().equals(loginMember.getId())) {
            response.sendRedirect("/todo");
            return false;
        }
        return true;
    }
}
