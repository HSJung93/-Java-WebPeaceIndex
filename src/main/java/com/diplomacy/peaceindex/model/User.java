package com.diplomacy.peaceindex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @JsonIgnore
    // JPA를 이용하여 조인을 하여 ManyToMany 매핑을 한다.
    // 사용자를 저장할때 권한까지 저장하는게 바람직하지 않아서 `cascade` 옵션은 주지 않는다.
    @ManyToMany
    // `JoinTable`로 "user_role"에 연결될 두 컬럼명을 적어준다.
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    // `List<Role> roles`로 멤버변수를 선언한다. 이후 `UserRepository`로 불러온다.
    // 서비스를 구현한 뒤, `roles`를 선언할 때, null 예외처리를 피하기 위해 `ArrayList`로 선언해준다.
    private List<Role> roles = new ArrayList<>();

    /* 사용자는 글을 안 쓰거나 여러 개를 쓸 수 있다. 따라서 글은 한 사용자로부터 매칭 받는다.
       @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGAR)처럼 매핑 관련 3옵션과 FetchType관련 1 옵션이 존재한다.
       1. board 클래스에서 정의해놓은 @ManyToOne user 변수를 사용한다. 서로가 서로의 클래스를 가지게 되어 양방향 매핑이 성립되었다.
       2. `cascade = CascadeType.ALL`는 Hibernate의 ALL, REMOVE 등의 메소드를 지정해줄 수 있다. REMOVE 메소드를 사용하면, user값을 제거할 때 연쇄적으로 boards가 먼저 삭제가 되고 user가 삭제가된다.
       3. `orphanRemoval = true`로 api로 데이터를 수정하면서 db에 저장된 기존의 user의 다른 데이터를 자동으로 삭제할 수 있다. 기본값이 false이다.
       4. `fetch = FetchType.EAGAR` 옵션은 사용자 조회 시에 boards 클래스에 대한 데이터를 같이 가져올 지(EAGAR) 아니면, 나중에 필요할 때 가져올지(LAZY) 에 대한 설정이다.
       `@OneToOne`과 `@ManyToOne` 기본값이 EAGAR이며, `@OneToMany`와 `@ManyToMany`의 기본값은 LAZY이다. 자동으로 불러와야할 컬럼이 하나인지 여러개 인지에 따라서 성능상의 부하를 고려한 기본값이다.
       LAZY로 설정하고 `UserApiController`의 `all()` 메소드를 사용하면 모든 보드(N)에 유저(1)까지 호출을 하는 N+1 문제가 발생하지 않는다.
       데이터를 따로 가져오는 LAZY 방식의 문제점은 UserRepository에서 해결한다.
    */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Board> boards = new ArrayList<>();
}
