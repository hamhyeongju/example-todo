package example.todo.controller;

import example.todo.Domain.Member;
import example.todo.service.LoginService.LoginService;
import example.todo.service.memberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller("/login")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping
    public String loginForm() {
        return "/form";
    }

    @PostMapping
    public String login(@ModelAttribute LoginDto loginDto, BindingResult bindingResult,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/form";
        }

        Optional<Member> loginMember = loginService.login(loginDto.getLoginId(), loginDto.getPassword());

        if (loginMember.isEmpty()) {
            bindingResult.reject("loginFail", "id password 에러");
            return "/form";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        return "redirect:/main";
    }
}
