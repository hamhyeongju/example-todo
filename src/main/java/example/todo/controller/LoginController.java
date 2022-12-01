package example.todo.controller;

import example.todo.Domain.Member;
import example.todo.controller.dto.LoginDto;
import example.todo.controller.dto.MemberSessionDto;
import example.todo.service.LoginService.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginDto") LoginDto loginDto,
                            @AuthenticationPrincipal UserDetails userDetails) {
        return (userDetails == null) ? "login/form" : "redirect:/";
    }


//    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginDto loginDto, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/todo") String redirectURL,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/form";
        }

        Optional<Member> loginMember = loginService.login(loginDto.getUsername(), loginDto.getPassword());

        if (loginMember.isEmpty()) {
            bindingResult.reject("loginFail");
            return "login/form";
        }

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", new MemberSessionDto(
                loginMember.get().getLoginId(),
                loginMember.get().getPassword(),
                loginMember.get().getName()
        ));

        return "redirect:" + redirectURL;
    }

//    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loginMember") != null) session.invalidate();

        return "redirect:/";
    }
}
