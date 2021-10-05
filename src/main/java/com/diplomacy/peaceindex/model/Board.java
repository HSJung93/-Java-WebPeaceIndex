package com.diplomacy.peaceindex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// @Entity는 이 클래스가 데이터베이스 연동을 위한 모델 클래스임을 알려준다. 컨트롤러에서 Model를 파라매터에서 불러오면 그 때 속성에 넣어줄 수 있다.
// 롬복의 @Data 어노테이션으로 게터와 세터를 만들 수 있다.
@Entity
@Data
public class Board {

    // pk선언과 자동증가
    // `IDENTITY` 말고 `SEQUENCE`를 사용하면 성능이 보다 좋지만 추가 작업이 필요한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // BoardController의 `BindingResult.hasErrors()`로 `Board`에서 `@NotNull` `@Size` 어노테이션으로 제한한 사항이 맞는지 체크한다.
    @NotNull
    @Size(min=2, max=30, message = "제목은 2자 이상 30자 이하입니다.")
    private String title;
    private String content;

    // 글이 써졌다면 그 글을 쓴 사용자는 하나이다. 따라서 사용자 하나는 많은 글과 매칭된다.
    // Board 클래스에 User 정보를 넣어서 @ManyToOne 관계 만들기
    // ManyToOne 쪽에서 컬럼을 적어주고 One 쪽에서는 mappedBy로 양방향 매칭을 한다.
    @ManyToOne
    /* `JoinColumn()` 어노테이션으로 마리아 DB의 테이블들을 연결해준다.
       이를 위하여 DB의 board 테이블에 user_id 컬럼을 추가하고 user 테이블의 id값과 외래키로 연결해준다.
       이 user_id 키값을 이용해서 user의 사용자 이름을 가져오는 로직을 작성하게 된다.
       통상적으로 양방향 매핑의 경우 `@ManyToOne`에 키에 대한 설정을 적어둔다.
       `name = "user_id"`와 `referencedColumnName = "id"`로 마리아 DB의 테이블과 컬럼을 지정해줄 수 있다.
       다만 id의 경우 User 클래스에서 `@Id` 어노테이션 지정을 해주었기 때문에 생략할 수 있다.
    */
    @JoinColumn(name="user_id")
    // `@JsonIgnore`로 api 요청 시에 재귀적으로 users가 표시되지 않도록 한다.
    @JsonIgnore
    private User user;
}

