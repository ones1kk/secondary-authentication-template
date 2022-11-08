package io.github.ones1kk.authenticationtemplate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "message")
@NoArgsConstructor(access = PROTECTED)
public class Message {

    @Id
    @Column(name = "message_id")
    private String messageId;

    @Column(name = "message_kor_name")
    private String messageKorName;

    @Column(name = "message_eng_name")
    private String messageEngNm;

    public Message(String messageId, String messageKorName, String messageEngNm) {
        this.messageId = messageId;
        this.messageKorName = messageKorName;
        this.messageEngNm = messageEngNm;
    }
}
