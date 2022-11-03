package io.github.ones1kk.authenticationtemplate.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Table(name = "label")
public class Label {

    @Id
    @Column(name = "label_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "label_kor_name")
    private String labelKorName;

    @Column(name = "label_eng_name")
    private String labelEngNm;
}
