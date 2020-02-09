package ua.training.system_what_where_when_spring.entity;

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
@Table(name = "appeal")
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "appeal_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "appeal_stage", nullable = false)
    private AppealStage appealStage;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "appeal", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<AppealedQuestion> appealedQuestions = new ArrayList<>();

    public void addAppealedQuestions(List<AppealedQuestion> appealedQuestions) {
        this.appealedQuestions.addAll(appealedQuestions);
        appealedQuestions.forEach(answeredQuestion -> answeredQuestion.setAppeal(this));

    }
}
