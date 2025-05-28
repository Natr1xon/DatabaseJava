package ru.nat.service;

import io.ebean.DB;
import ru.nat.entity.Player;

import java.math.BigDecimal;
import java.util.List;

public class PlayerService implements Service<Player>{
    @Override
    public void createEntity(Player player) {
        player.save();
    }

    @Override
    public void updateEntity(Player player) {
        if (player != null) {
            player.update();
        }
    }

    @Override
    public void deleteEntity(Long id) {
        Player player = DB.find(Player.class, id);
        if (player != null) {
            player.delete();
        }
    }

    @Override
    public List<Player> getAllEntities() {
        return DB.find(Player.class).findList();
    }

    @Override
    public Player getEntityById(Long id) {
        return DB.find(Player.class, id);
    }

    public void updatePlayer(Long id, String nickname, int level, BigDecimal cashBalance) {
        Player player = DB.find(Player.class, id);
        if (player != null) {
            player.setNickname(nickname);
            player.setLevel(level);
            player.setCashBalance(cashBalance);
            player.update();
        }
    }

    public List<Player> findPlayersByNickname(String keyword) {
        return DB.find(Player.class)
                .where()
                .ilike("nickname", "%" + keyword + "%")
                .findList();
    }
}
