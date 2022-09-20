package example.todo.controller;

import example.todo.Domain.Member;
import example.todo.Domain.ToDo;
import example.todo.controller.dto.ToDoDto;
import example.todo.service.todoService.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

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
}
