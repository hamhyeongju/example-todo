package example.todo.controller;

import example.todo.Domain.Member;
import example.todo.Domain.ToDo;
import example.todo.controller.dto.ToDoDto;
import example.todo.service.memberService.MemberService;
import example.todo.service.securityService.UserDetailsImpl;
import example.todo.service.todoService.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;
    private final MemberService memberService;

    @ModelAttribute("userDetails")
    public UserDetailsImpl userDetails(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails;
    }

    @ModelAttribute("toDoDtos")
    public List<ToDoDto> toDoDtos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return getToDoDtos(userDetails.getMember(), false);
    }

    @ModelAttribute("completedDtos")
    public List<ToDoDto> completedDtos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return getToDoDtos(userDetails.getMember(), true);
    }

//    private static Member getSessionMember(HttpServletRequest request) {
//        return (Member) request.getSession().getAttribute("loginMember");
//    }

    private List<ToDoDto> getToDoDtos(Member loginMember, Boolean isCompleted) {
        List<ToDo> list = toDoService.findSortByMemberIdAndIsCompleted(loginMember.getId(), isCompleted);
        return list.stream().map(toDo ->
                        new ToDoDto(toDo.getId(), toDo.getTitle(), toDo.getDescription(),
                                toDo.getIsCompleted(), toDo.getCreatedDateTime(), toDo.getDueDate()))
                .collect(Collectors.toList());
    }

    @GetMapping("/todo")
    public String todo() {
        return "todo/main";
    }

    @GetMapping("/todo/add")
    public String addForm(@ModelAttribute("toDoDto") ToDoDto toDoDto) {
        return "todo/add";
    }

    @PostMapping("/todo/add")
    public String addToDo(@Validated @ModelAttribute("toDoDto") ToDoDto toDoDto, BindingResult bindingResult,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (bindingResult.hasErrors()) return "todo/add";

        Optional<Member> findMember = memberService.findById(userDetails.getMember().getId());
        Optional<ToDo> createToDo = findMember.map(member -> ToDo.createToDo(
                toDoDto.getTitle(), toDoDto.getDescription(), toDoDto.getDueDate(),
                member));
        createToDo.ifPresent(toDo -> toDoService.save(toDo));

        return "redirect:/todo";
    }

    @GetMapping("/todo/update/{id}")
    public String editForm(@PathVariable Long id,  Model model) {
        if (id != null) {
            Optional<ToDo> todo = toDoService.findById(id);
            todo.ifPresent(toDoDto -> model.addAttribute("toDoDto", toDoDto));
        } else {
            return "redirect:/todo";
        }

        return "todo/edit";
    }

    @PostMapping("/todo/update/{id}")
    public String update(@PathVariable Long id, @Validated @ModelAttribute("toDoDto") ToDoDto toDoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "todo/edit";

        if (id != null)
            toDoService.update(id, toDoDto.getTitle(), toDoDto.getDescription(), toDoDto.getDueDate());
        return "redirect:/todo";
    }

    @PostMapping("/todo/change/{id}")
    public String change(@PathVariable Long id) {
        if (id != null) toDoService.changeStatus(id);
        return "redirect:/todo";
    }

    @PostMapping("/todo/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (id != null) toDoService.findById(id).ifPresent(toDo -> toDoService.delete(toDo));
        return "redirect:/todo";
    }
}
