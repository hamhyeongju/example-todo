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
    public String addForm(@ModelAttribute MemberDto memberDto) {
        return "/member/add";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "/member/add";

        Member member = new Member(memberDto.getLoginId(), memberDto.getPassword(), memberDto.getName());
        return memberService.save(member) != null ? "redirect:/" : "/member/add";
    }
}
