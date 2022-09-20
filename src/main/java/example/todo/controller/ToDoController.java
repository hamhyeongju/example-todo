package example.todo.controller;

import example.todo.Domain.Member;
import example.todo.Domain.ToDo;
import example.todo.controller.dto.ToDoDto;
import example.todo.service.memberService.MemberService;
import example.todo.service.todoService.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @ModelAttribute("toDoDtos")
    public List<ToDoDto> toDoDtos(HttpServletRequest request) {
        return getToDoDtos(getSessionMember(request), false);
    }

    @ModelAttribute("completedDtos")
    public List<ToDoDto> completedDtos(HttpServletRequest request) {
        return getToDoDtos(getSessionMember(request), true);
    }

    private static Member getSessionMember(HttpServletRequest request) {
        return (Member) request.getSession().getAttribute("loginMember");
    }

    private List<ToDoDto> getToDoDtos(Member loginMember, Boolean isCompleted) {
        List<ToDo> list = toDoService.findToDoListByIsCompleted(loginMember.getId(), isCompleted);
        return list.stream().map(toDo ->
                        new ToDoDto(toDo.getId(), toDo.getTitle(), toDo.getDescription(),
                                toDo.getIsCompleted(), toDo.getCreatedDate(), toDo.getDueDate()))
                .collect(Collectors.toList());
    }

    @GetMapping("/todo")
    public String todo(@ModelAttribute("toDoDto") ToDoDto toDoDto) {
        return "/todo/main";
    }

    @GetMapping("/todo/add")
    public String addForm(@ModelAttribute("toDoDto") ToDoDto toDoDto) {
        return "/todo/add";
    }

    @PostMapping("/todo/add")
    public String addToDo(@Validated @ModelAttribute("toDoDto") ToDoDto toDoDto, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "/todo/add";
        }

        Optional<Member> findMember = memberService.findById(getSessionMember(request).getId());
        Optional<ToDo> createToDo = findMember.map(member -> ToDo.createToDo(
                toDoDto.getTitle(), toDoDto.getDescription(), toDoDto.getDueDate(),
                member));
        createToDo.ifPresent(toDo -> toDoService.save(toDo));

        return "redirect:/todo";
    }

    @PostMapping("/todo/update")
    public String update(@Validated @ModelAttribute("toDoDto") ToDoDto toDoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/todo/update";
        }

        toDoService.update(toDoDto.getId(), toDoDto.getTitle(), toDoDto.getDescription(), toDoDto.getDueDate());
        return "redirect:/todo";
    }

    @PostMapping("/todo/change")
    public String change(@Validated @ModelAttribute("toDoDto") ToDoDto toDoDto) {

        toDoService.changeStatus(toDoDto.getId());
        return "redirect:/todo";
    }

    @PostMapping("/main/delete")
    public String delete(@ModelAttribute("toDoDto") ToDoDto toDoDto) {
        toDoService.findById(toDoDto.getId()).ifPresent(toDo -> toDoService.delete(toDo));
        return "redirect:/todo";
    }
}
