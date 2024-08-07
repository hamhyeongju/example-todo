package example.todo.controller;

import example.todo.Domain.Member;
import example.todo.Domain.ToDo;
import example.todo.controller.dto.ToDoDto;
import example.todo.service.memberService.MemberService;
import example.todo.service.securityService.UserDetailsImpl;
import example.todo.service.todoService.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @brief "/todo/**" 경로의 요청을 처리하는 컨트롤러
 * @details ToDo CRUD 관련 요청 처리
 */
@Controller
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;
    private final MemberService memberService;

    /**
     * @brief 요청한 member의 todo를 list로 조회 후 dto 변환
     */
    private List<ToDoDto> getToDoDtos(Member loginMember, Boolean isCompleted) {
        List<ToDo> list = toDoService.findJPQLByMemberIdAndIsCompleted(loginMember.getId(), isCompleted);
        return list.stream().map(toDo ->
                        new ToDoDto(toDo.getId(), toDo.getTitle(), toDo.getDescription(),
                                toDo.getIsCompleted(), toDo.getCreatedDateTime(), toDo.getDueDate()))
                .collect(Collectors.toList());
    }

    @GetMapping("/todo")
    public String todo(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("membername", userDetails.getMember().getName());
        model.addAttribute("toDoDtos", getToDoDtos(userDetails.getMember(), false));
        model.addAttribute("completedDtos", getToDoDtos(userDetails.getMember(), true));

        return "todo/main";
    }

    @GetMapping("/todo/add")
    public String addForm(@ModelAttribute("toDoDto") ToDoDto toDoDto) {
        return "todo/add";
    }

    /**
     * @brief ToDo Create
     */
    @PostMapping("/todos")
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
    public String editForm(@PathVariable("id") Long id,  Model model) {
        if (id != null) {
            Optional<ToDo> todo = toDoService.findById(id);
            todo.ifPresent(toDoDto -> model.addAttribute("toDoDto", toDoDto));
        } else {
            return "redirect:/todo";
        }

        return "todo/edit";
    }

    /**
     * @brief ToDo Update
     */
    @PutMapping("/todos/{id}")
    public String update(@PathVariable("id") Long id, @Validated @ModelAttribute("toDoDto") ToDoDto toDoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "todo/edit";

        if (id != null)
            toDoService.update(id, toDoDto.getTitle(), toDoDto.getDescription(), toDoDto.getDueDate());
        return "redirect:/todo";
    }

    /**
     * ToDo 완료 여부 변경
     */
    @PatchMapping("/todos/{id}")
    public String change(@PathVariable("id") Long id) {
        if (id != null) toDoService.changeStatus(id);
        return "redirect:/todo";
    }

    /**
     * ToDo Delete
     */
    @DeleteMapping("/todos/{id}")
    public String delete(@PathVariable("id") Long id) {
        if (id != null) toDoService.delete(id);
        return "redirect:/todo";
    }
}
