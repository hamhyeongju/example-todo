package example.todo.controller;

import example.todo.Domain.Member;
import example.todo.controller.dto.MemberDto;
import example.todo.service.memberService.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @brief "/", "/add" 경로의 요청을 처리하는 컨트롤러
 * @details 첫 화면과 회원 가입 화면, 회원 가입 요청 처리
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    /**
     * @details 로그인 사용자라면 화면에서 로그아웃 버튼을 출력하기 위해 UserDetails 포함
     */
    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) model.addAttribute("loginStatus", true);
        return "home";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute("memberDto") MemberDto memberDto) {
        return "member/add";
    }

    /**
     * @brief 회원 가입 기능
     * @details Bean Validation 으로 1차 검증 후 password.equals(checkPassword), loginId 중복 여부 검증
     * @return
     */
    @PostMapping("/add")
    public String save(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult) {

        if (!memberDto.getPassword().equals(memberDto.getCheckPassword()))
            bindingResult.rejectValue("checkPassword", "wrong");

        if (bindingResult.hasErrors()) return "member/add";

        Member member = new Member(memberDto.getLoginId(), memberDto.getPassword(), memberDto.getName().strip());
        if (memberService.save(member) == null) {
            bindingResult.reject("duplication");
            return "member/add";
        }
        return "redirect:/";
    }


    /**
     * @brief str이 영소문자나 숫자로 구성되면 true
     * @deprecated Bean Validation @Pattern으로 대체
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
     * @brief str이 영소문자나 한글 숫자 공백으로 구성되면 true
     * @deprecated Bean Validation @Pattern으로 대체
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
