package appCenter.guCoding.todoList.domain.user;

import appCenter.guCoding.todoList.domain.BaseTimeEntity;
import appCenter.guCoding.todoList.domain.task.Task;
import appCenter.guCoding.todoList.dto.user.UserReqDto.UserEditReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "user_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 20)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Task> taskList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role;

    @Builder
    public User(Long id, String username, String password, String email, List<Task> taskList, UserEnum role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.taskList = taskList;
        this.role = role;
    }

    public void changeField(UserEditReqDto userEditReqDto, String encPassword) {
        this.username = userEditReqDto.getUsername();
        this.password = encPassword;
        this.email = userEditReqDto.getEmail();
    }
}
