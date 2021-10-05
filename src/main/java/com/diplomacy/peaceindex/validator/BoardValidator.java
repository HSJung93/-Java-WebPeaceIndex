package com.diplomacy.peaceindex.validator;

import com.diplomacy.peaceindex.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

// `Board` 클래스의 어노테이션을 통한 구현 이상을 원할 때, 커스텀 클래스를 만든다.
// Spring Boot Validator를 참고하여 형식을 맞춰 준다.
// DI를 위하여 `@Component`로 등록해준다.
@Component
public class BoardValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    // obj 인풋을 Board로 형변환을 해준 후 필요한 에러를 체크해준다.
    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if(StringUtils.isEmpty(b.getContent())){
            errors.rejectValue("content", "key", "내용을 입력하세요");
        }
    }
}
