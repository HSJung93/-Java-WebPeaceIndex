package com.diplomacy.peaceindex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // `List<User> users`를 가져올 때 `@ManyToMany(mappedBy="roles")` 로 유저 클래스에서 이미 설정해둔 컬럼명인 roles를 지정하여 양방향 매핑을 한다.
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore // api get 요청 시에 재귀적 호출을 막는다
    private List<User> users;
}
