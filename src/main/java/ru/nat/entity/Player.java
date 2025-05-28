package ru.nat.entity;

import io.ebean.Model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "player")
public class Player extends Model{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int level;

    @Column(name = "cash_balance", nullable = false, precision = 12, scale = 2)
    private BigDecimal cashBalance;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_enter", nullable = false)
    private Date dateEnter;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> games;

    public Player() {}

    public Player(String nickname, BigDecimal cashBalance, int level) {
        this.nickname = nickname;
        this.cashBalance = cashBalance;
        this.level = level;
        dateEnter = new Date();
    }

    @PrePersist
    protected void onCreate() {
        this.dateEnter = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public Date getDate(){
        return dateEnter;
    }

    public List<Game> getGames(){
        return games;
    }
}
