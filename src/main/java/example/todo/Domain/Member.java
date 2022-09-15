package example.todo.Domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;

    @OneToMany(mappedBy = "member")
    private List<ToDo> toDoList = new ArrayList<>();

    public Member(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }
}

