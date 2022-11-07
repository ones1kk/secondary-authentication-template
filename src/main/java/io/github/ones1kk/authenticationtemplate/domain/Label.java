package io.github.ones1kk.authenticationtemplate.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Getter
@Entity
@Table(name = "label")
@NoArgsConstructor(access = PROTECTED)
public class Label {

    @Id
    @Column(name = "label_id")
    private String labelId;

    @Column(name = "label_kor_name")
    private String labelKorName;

    @Column(name = "label_eng_name")
    private String labelEngNm;

    public Label(String labelId, String labelKorName, String labelEngNm) {
        this.labelId = labelId;
        this.labelKorName = labelKorName;
        this.labelEngNm = labelEngNm;
    }
}
