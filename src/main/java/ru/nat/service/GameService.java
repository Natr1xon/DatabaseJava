package ru.nat.service;

import io.ebean.DB;
import ru.nat.entity.Game;
import ru.nat.entity.Player;

import java.math.BigDecimal;
import java.util.List;

public class GameService implements Service<Game>{
    @Override
    public void createEntity(Game game) {
        game.save();
    }

    @Override
    public void updateEntity(Game game) {
        if (game != null) {
            game.update();
        }
    }

    @Override
    public void deleteEntity(Long id) {
        Game game = DB.find(Game.class, id);
        if (game != null) {
            game.delete();
        }
    }

    @Override
    public List<Game> getAllEntities() {
        return DB.find(Game.class).findList();
    }

    @Override
    public Game getEntityById(Long id) {
        return DB.find(Game.class, id);
    }

    public void updateEntity(Long id, String name, String genre, int score,
                           double percentageAchievement, BigDecimal cost) {
        Game game = DB.find(Game.class, id);
        if (game != null) {
            game.setName(name);
            game.setGenre(genre);
            game.setScore(score);
            game.setPercentageAchievement(percentageAchievement);
            game.setCost(cost);
            game.update();
        }
    }

    public List<Game> getGamesByPlayer(Player player) {
        return DB.find(Game.class)
                .where()
                .eq("player", player)
                .findList();
    }

    public List<Game> findGamesByName(String keyword) {
        return DB.find(Game.class)
                .where()
                .ilike("name", "%" + keyword + "%")
                .findList();
    }
}
