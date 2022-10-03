package example.todo.controller;

import example.todo.Domain.Member;
import example.todo.controller.dto.MemberDto;
import example.todo.service.memberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("memberDto") MemberDto memberDto) {
        return "/member/add";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult) {

        if (!bindingResult.hasFieldErrors("name") && !validateKorean(memberDto.getName()))
            bindingResult.rejectValue("name", "name", "2~20자의 영문 소문자, 숫자, 한글만 사용 가능합니다. 앞뒤의 공백은 제거됩니다.");

        if (!bindingResult.hasFieldErrors("loginId") && !validateString(memberDto.getLoginId()))
            bindingResult.rejectValue("loginId", "loginId", "5~20자의 영문 소문자, 숫자만 사용 가능합니다. 공백은 허용되지 않습니다.");

        if (!bindingResult.hasFieldErrors("password")) {
            if (!validateString(memberDto.getPassword()))
                bindingResult.rejectValue("password", "password", "5~20자의 영문 소문자, 숫자만 사용 가능합니다. 공백은 허용되지 않습니다.");
            else if (!memberDto.getPassword().equals(memberDto.getCheckPassword()))
                bindingResult.rejectValue("checkPassword", "", "비밀번호가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) return "/member/add";

        Member member = new Member(memberDto.getLoginId(), memberDto.getPassword(), memberDto.getName().strip());
        if (memberService.save(member) == null) {
            bindingResult.rejectValue("loginId", "duplication", "이미 존재하는 ID 입니다.");
            return "/member/add";
        }
        return "redirect:/";

    }


    /**
     * str이 영소문자나 숫자로 구성되면 true
     */
    private boolean validateString(String str) {
        String temp = "";
        for (int i = 0; i < str.length(); i++) {
            if (!String.valueOf(str.charAt(i)).matches("[^a-z0-9]")) { // 영소문자나 숫자면
                temp += str.charAt(i);
            }
        }
        // ture면 검증 완료
        return str.equals(temp) ? true : false;
    }

    /**
     * str이 영소문자나 한글 숫자 공백으로 구성되면 true
     */
    private boolean validateKorean(String str) {
        String temp = "";
        for (int i = 0; i < str.length(); i++) {
            if (!String.valueOf(str.charAt(i)).matches("[^ㄱ-ㅎㅏ-ㅣ가-힣a-z0-9\\s]")) { //소문자거나 한글이면 공백이면
                temp += str.charAt(i);
            }
        }
        return str.equals(temp) ? true : false;
    }
}
