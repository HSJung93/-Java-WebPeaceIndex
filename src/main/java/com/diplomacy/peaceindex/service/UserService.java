package com.diplomacy.peaceindex.service;

import com.diplomacy.peaceindex.model.Role;
import com.diplomacy.peaceindex.model.User;
import com.diplomacy.peaceindex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// UserService는 AccountController에서 선언하여 사용하게 된다.
// @Service 비지니스 로직을 생성한다. 이 클래스만 가지고 유닛 테스트 작성하기도 유용하다.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // `WebSecurityConfig`에서 선언해둔 `PasswordEncoder`를 `@AutoWired`로 선언하여 사용한다.
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
        // `passwordEncoder.encode()`메소드로 암호화한다.
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        // 아이디와 패스워드 뿐만이 아니라 role 또한 저장한다.
        Role role = new Role();
        // 어레이리스트 형태로 불러온 role를 저장하려고 하는데, 데이터베이스에서 또다시 불러오기 위하여 Repository를 또 만들기 보다는, id를 하드코딩을 해서 데이터베이스에 만들어둔 role 값을 불러와서 넣어준다.
        role.setId(1l);
        user.getRoles().add(role);
        return userRepository.save(user);

    }

}
