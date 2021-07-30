package com.diplomacy.peaceindex.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// 이 클래스가 데이터베이스 연동을 위한 모델 클래스임을 알려준다.
//게터, 세터? 우리는 롬북을 추가해놨다!
@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
}

