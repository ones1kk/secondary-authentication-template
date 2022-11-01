package io.github.ones1kk.authenticationtemplate.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "id", unique = true)
    private String userId;

    @Column(name = "password")
    private String password;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
