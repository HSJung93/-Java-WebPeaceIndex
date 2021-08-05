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
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // board 클래스에서 정의해놓은 @ManyToOne user 변수 사용 서로가 서로의 클래스를 가지게 되었다. 양방향 매핑
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // boards 클래스 데이터를 EAGER로 가져올 떄 같이 가져올 것인가(One으로 끝남), 사용할 때 조회할 것인가(Many로 끝남)
//    @JsonIgnore
    private List<Board> boards = new ArrayList<>();
}
