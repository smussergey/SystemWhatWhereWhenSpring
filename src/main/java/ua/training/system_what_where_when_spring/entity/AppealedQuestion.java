package ua.training.system_what_where_when_spring.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "appealed_question")
public class AppealedQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "appealed_question_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToOne
    @JoinColumn(name = "appeal_id")
    private Appeal appeal;
}
