package appCenter.guCoding.todoList.dto.user;

import appCenter.guCoding.todoList.domain.user.User;
import lombok.Getter;
import lombok.Setter;

public class UserRespDto {

    @Getter
    @Setter
    public static class JoinRespDto {
        private Long id;
        private String username;


        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }
    }

    @Getter
    @Setter
    public static class UserEditRespDto {

        private String username;
        private String password;
        private String email;

        public UserEditRespDto(User user) {
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.email = user.getEmail();
        }
    }
}
