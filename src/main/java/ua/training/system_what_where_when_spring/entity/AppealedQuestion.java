package ua.training.system_what_where_when_spring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appealed_question")
public class AppealedQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "appealed_question_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "appeal_id")
    private Appeal appeal;
}
