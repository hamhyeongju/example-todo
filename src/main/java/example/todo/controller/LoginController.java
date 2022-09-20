package example.todo.controller;

import example.todo.Domain.Member;
import example.todo.controller.dto.LoginDto;
import example.todo.service.LoginService.LoginService;
import example.todo.service.memberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto) {
        return "/login/form";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginDto loginDto, BindingResult bindingResult,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/login/form";
        }

        Optional<Member> loginMember = loginService.login(loginDto.getLoginId(), loginDto.getPassword());

        if (loginMember.isEmpty()) {
            bindingResult.reject("loginFail", "id password 에러");
            return "/login/form";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember.get());

        return "redirect:/todo";
    }
}
