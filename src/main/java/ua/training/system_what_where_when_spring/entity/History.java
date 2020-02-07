package ua.training.system_what_where_when_spring.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

//TODO add constraints
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "history_id")
    private Long id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "first_player_name_ua")
    private String firstPlayerNameUa;
    @Column(name = "first_player_name_en")
    private String firstPlayerNameEn;
    @Column(name = "second_player_name_ua")
    private String secondPlayerNameUa;
    @Column(name = "second_player_name_en")
    private String secondPlayerNameEn;
    @Column(name = "scores")
    private String scores;
    @Column(name = "appeal_stage")
    private String appealStage;
}
