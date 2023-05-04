package appCenter.guCoding.todoList.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserEnum {
    ADMIN("관리자"), USER("사용자");
    private String value;
}
