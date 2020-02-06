package ua.training.systemwww.entity;

import lombok.Data;

import javax.persistence.*;

//TODO add constraints
@Data
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userWhoGotPoint;
}
