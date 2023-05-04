package appCenter.guCoding.todoList.handler.ex;

import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException {

    private Map<String, String> errorMap;
    public CustomValidationException(String message, Map errorMap) {
        super(message);
        this.errorMap = errorMap;
    }
}
