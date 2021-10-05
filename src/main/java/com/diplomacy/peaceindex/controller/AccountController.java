package com.diplomacy.peaceindex.controller;

import com.diplomacy.peaceindex.model.User;
import com.diplomacy.peaceindex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    @GetMapping("/register")
    public String register(){
        return "account/register";
    }

    // form을 받기 이전에
    // 1. user와 role을 받을 모델 클래스가 내부적으로 필요하다.
    // -> User, Role 모델 클래스와 UserRepository를 구현한 후 User를 인자로 받는다.
    // 2. 암호화와 권한 관리 등의 서비스를 처리할 패키지가 필요하다.
    // -> UserService를 `@AutoWired`로 선언한 후, userService의 save메소드로 user를 저장한다.
    @PostMapping("/register")
    // User를 인자로 받고 있기 때문에 User 클래스에서 선언한 값 username 과 password 에 맞춰서 값을 받게 된다.
    public String register(User user){
        userService.save(user);
        return "redirect:/";
    }


}
