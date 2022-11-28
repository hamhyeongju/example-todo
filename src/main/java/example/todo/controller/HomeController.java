package example.todo.controller;

import example.todo.Domain.Member;
import example.todo.controller.dto.MemberDto;
import example.todo.service.memberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        return "member/add";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult) {

        if (!memberDto.getPassword().equals(memberDto.getCheckPassword()))
            bindingResult.rejectValue("checkPassword", "wrong");

        if (bindingResult.hasErrors()) return "member/add";

        if (memberService.saveBySecurity(memberDto) == null) {
            bindingResult.reject("duplication");
            return "member/add";
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
