package ua.training.system_what_where_when_spring.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO add constraints
@Data
@Entity
@Table(name = "appeal")
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "appeal_id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "appeal_stage")
    private AppealStage appealStage;


    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}

