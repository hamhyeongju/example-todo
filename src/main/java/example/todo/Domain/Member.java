package example.todo.Domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief Member Entity
 * @details Member 1:N ToDo
 */
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

    public void setEncodingPassword(String password) {
        this.password = password;
    }

    public Member(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }
}

