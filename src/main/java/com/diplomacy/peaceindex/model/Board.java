package com.diplomacy.peaceindex.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// 이 클래스가 데이터베이스 연동을 위한 모델 클래스임을 알려준다.
// 롬복에서 간단한 어노테이션으로 게터와 세터를 만들 수 있다.
// @OneToOne join 없는 관계라 간단한 설정이 가능한 경우(One X Many 4가지 경우 가능)
@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2, max=30, message = "제목은 2자 이상 30자 이하입니다.")
    private String title;
    private String content;

    //Board 클래스에 User 정보를 넣어서 @ManyToOne 관계 만들기
    @ManyToOne // ManyToOne 쪽에서 컬럼을 적어주고 One 쪽에서는 mappedBy로 양방향 매칭
    @JoinColumn(name="user_id") // , referencedColumnName = "id" User에 @Id 있어서 생략가능
    @JsonIgnore
    private User user;
}

