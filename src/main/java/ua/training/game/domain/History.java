package ua.training.game.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
