package example.todo.configuration.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @brief 로그인 사용자 인증 인터셉터
 * @deprecated Spring-Security 도입으로 기능 위임
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // 비로그인 사용자는 로그인 페이지로 이동
        if (session == null || session.getAttribute("loginMember") == null) {
            response.sendRedirect("/login?redirectURL="+ request.getRequestURI());
            return false;
        }
        return true;
    }

}
