package appCenter.guCoding.todoList.domain.task;

import appCenter.guCoding.todoList.domain.user.User;
import appCenter.guCoding.todoList.dto.task.TaskReqDto;
import appCenter.guCoding.todoList.dto.task.TaskReqDto.TaskEditReqDto;
import appCenter.guCoding.todoList.handler.ex.CustomApiException;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    private String description;

    @Column(nullable = false)
    private Boolean isCompleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Builder
    public Task(Long id, String title, String description, Boolean isCompleted, User user, LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.user = user;
        this.deadline = deadline;
    }

    public void checkOwner(Long userId) {
        if (user.getId().longValue() != userId.longValue()) {
            throw new CustomApiException("할 일 소유자가 아닙니다.");
        }
    }


    public void changeField(TaskEditReqDto taskEditReqDto) {
        this.title = taskEditReqDto.getTitle();
        this.description = taskEditReqDto.getDescription();
        this.isCompleted = taskEditReqDto.getIsCompleted();
        this.deadline = taskEditReqDto.toEntity().getDeadline();
    }
}
