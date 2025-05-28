package ru.nat.table;

import ru.nat.entity.Player;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PlayerTable extends AbstractTableModel {
    private final String[] columnNames = {"ID","Nickname","Level","Cash balance","Date enter"};
    private final List<Player> players;

    public PlayerTable(List<Player> players){
        this.players = players;
    }

    @Override
    public int getRowCount() {
        return players.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Player g = players.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> g.getId();
            case 1 -> g.getNickname();
            case 2 -> g.getLevel();
            case 3 -> g.getCashBalance();
            case 4 -> g.getDate();
            default -> null;
        };
    }
}
