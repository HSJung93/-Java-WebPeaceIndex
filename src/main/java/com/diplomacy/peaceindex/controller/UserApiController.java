package com.diplomacy.peaceindex.controller;

import com.diplomacy.peaceindex.model.Board;
import com.diplomacy.peaceindex.model.QUser;
import com.diplomacy.peaceindex.model.User;
import com.diplomacy.peaceindex.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    Iterable<User> all(@RequestParam(required = false) String method, @RequestParam(required = false) String text ) {
        Iterable<User> users = null;

          // `all()` 메소드에서 LAZY 옵션을 확인하도록 Log.debug()를 사용한다. 로그를 보기 위하여 application.properties의 설정도 수정한다.
//        log.debug("getBoards().size() 호출 전");
//        log.debug("getBoards().size() : {}", users.get(0).getBoards().size()); // board가 사용될 떄 사용자 데이터만 조회(LAZY 설정 )
//        log.debug("getBoards().size() 호출 후");

        if ("query".equals(method)){
            users = repository.findByUsernameQuery(text);
        } else if ("nativeQuery".equals(method)){
            users = repository.findByUsernameNativeQuery(text);
        }else if ("querydsl".equals(method)){
            QUser user = QUser.user;
            Predicate predicate = user.username.contains(text);
            users = repository.findAll(predicate);

            // 조건문을 거는 경우
//            BooleanExpression b =user.username.contains(text);
//            if(true){
//                b = b.and(user.username.eq("HI"));
//            }
//            users = repository.findAll(b);
        }else if ("querydslCustom".equals(method)){
            users = repository.findByUsernameCustom(text);
        }else if ("jdbc".equals(method)){
            users = repository.findByUsernameJdbc(text);
        }else{
            users = repository.findAll();
        }
        return users;
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());
//                    user.setContent(newUser.getContent());

                    // User 클래스에서 `@OneToMany` 매핑을 할 경우 UserApiController로도 boards를 수정할 수 있다.
                    // 그런데 `user.setBoards(newUser.getBoards());`로 값을 저장할 수 없다!
                    // User 클래스의 boards에 추가적인 설정이 필요하다.
//                    user.setBoards(newUser.getBoards());

                    // `user.getBoards().clear();` 이후 `user.getBoards().addAll(newUser.getBoards());`로 기존의 데이터를 삭제한 후 데이터를 새롭게 넣어주는 코드로 변경한다.
                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());

                    // 설정 후 user.getBoards()안의 board에 user 값을 넣어주면 마리아 DB에 정상적으로 저장이 된다.
                    for(Board board : user.getBoards()) {
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}