package appCenter.guCoding.todoList.domain.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // select * from task where title = : title
    Optional<Task> findByTitle(String title);

    @Query("select t from Task t where t.title = :title")
    List<Task> findByTitle2(String title);

    @Query("select t from Task t where t.user.username = :username and t.title = :title")
    List<Task> findTask(@Param("username") String username, @Param("title") String title);






}
