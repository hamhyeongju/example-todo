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

    @ModelAttribute("userDetails")
    public UserDetailsImpl userDetails(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails;
    }

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
    @PostMapping("/todo")
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

    /**
     * @brief ToDo Update
     */
    @PutMapping("/todo/{id}")
    public String update(@PathVariable Long id, @Validated @ModelAttribute("toDoDto") ToDoDto toDoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "todo/edit";

        if (id != null)
            toDoService.update(id, toDoDto.getTitle(), toDoDto.getDescription(), toDoDto.getDueDate());
        return "redirect:/todo";
    }

    /**
     * ToDo 완료 여부 변경
     */
    @PostMapping("/todo/change/{id}")
    public String change(@PathVariable Long id) {
        if (id != null) toDoService.changeStatus(id);
        return "redirect:/todo";
    }

    /**
     * ToDo Delete
     */
    @DeleteMapping("/todo/{id}")
    public String delete(@PathVariable Long id) {
        if (id != null) toDoService.findById(id).ifPresent(toDo -> toDoService.delete(toDo));
        return "redirect:/todo";
    }
}
