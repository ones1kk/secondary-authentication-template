package io.github.ones1kk.authenticationtemplate.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table(name = "message")
public class Message {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "message_kor_name")
    private String messageKorName;

    @Column(name = "message_eng_name")
    private String messageEngNm;
}
