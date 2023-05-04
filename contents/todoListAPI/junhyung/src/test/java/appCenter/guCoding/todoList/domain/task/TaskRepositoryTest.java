package appCenter.guCoding.todoList.domain.task;

import appCenter.guCoding.todoList.domain.user.User;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@SpringBootTest
@Transactional
@Rollback(value = false)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void jpaRepository_test() throws Exception {
        // taskRepositoryorg.springframework.data.jpa.repository.support.SimpleJpaRepository@63e81ac6
        System.out.println("테스트 : taskRepository" + taskRepository);
    }

    private Task setUp() {
        User mockUser = User.builder()
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .build();

        Task task = Task.builder()
                .title("타이틀")
                .deadline(LocalDateTime.now())
                .description("본문")
                .isCompleted(false)
                .user(mockUser)
                .build();
        return task;
    }


}