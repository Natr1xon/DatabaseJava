package ru.nat.entity;

import io.ebean.Model;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "game")
public class Game extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private int score;

    @Column(name = "percentage_achievement", nullable = false)
    private double percentageAchievement;

    @Column(name = "game_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal cost;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    public Game() {}

    public Game(String name, String genre,
                int score, double percentageAchievement,
                BigDecimal cost, Player player){
        this.name = name;
        this.genre = genre;
        this.score = score;
        this.percentageAchievement = percentageAchievement;
        this.cost = cost;
        this.player = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getPercentageAchievement() {
        return percentageAchievement;
    }

    public void setPercentageAchievement(double percentageAchievement) {
        this.percentageAchievement = percentageAchievement;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
