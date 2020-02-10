package ua.training.game.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "game_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "firstPlayer_user_id", nullable = false)
    private User firstPlayer;

    @ManyToOne
    @JoinColumn(name = "secondPlayer_user_id", nullable = false)
    private User secondPlayer;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    // TODO Check to use CascadeType.All
    private List<Appeal> appeals = new ArrayList<>(); // TODO Check to use Set

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    // TODO Check to use CascadeType.All
    private List<Question> questions = new ArrayList<>(); // TODO Check to use Set

    public void addQuestions(List<Question> questions) {
        this.questions.addAll(questions);
        questions.forEach(answeredQuestion -> answeredQuestion.setGame(this));
    }
}
