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
    @Column(name = "history_id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "first_player_name_ua", nullable = false)
    private String firstPlayerNameUa;
    @Column(name = "first_player_name_en", nullable = false)
    private String firstPlayerNameEn;
    @Column(name = "second_player_name_ua", nullable = false)
    private String secondPlayerNameUa;
    @Column(name = "second_player_name_en", nullable = false)
    private String secondPlayerNameEn;
    @Column(name = "scores", nullable = false)
    private String scores;
    @Column(name = "appeal_stage", nullable = false)
    private String appealStage;
}
