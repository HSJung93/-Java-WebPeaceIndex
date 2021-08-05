package com.diplomacy.peaceindex.service;

import com.diplomacy.peaceindex.model.Role;
import com.diplomacy.peaceindex.model.User;
import com.diplomacy.peaceindex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//비지니스 로직 생성, 이 클래스만 가지고 유닛 테스트 작성하기도 유용
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        return userRepository.save(user);

    }

}
