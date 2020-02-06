package ua.training.system_what_where_when_spring.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO add constraints
@Data
@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "game_id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "firstPlayer_user_id")
    private User firstPlayer;

    @ManyToOne
    @JoinColumn(name = "secondPlayer_user_id")
    private User secondPlayer;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Appeal> appeals = new ArrayList<>();

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public void addQuestions(List<Question> questions) {
        this.questions.addAll(questions);
        questions.forEach(answeredQuestion -> answeredQuestion.setGame(this));
    }
}
