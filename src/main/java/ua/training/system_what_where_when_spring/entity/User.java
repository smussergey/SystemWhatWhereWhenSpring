package ua.training.system_what_where_when_spring.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//TODO add constraints
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name_ua")
    private String nameUa;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

//    @Setter(AccessLevel.PRIVATE)
//    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
//    private List<Game> games = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }
}
