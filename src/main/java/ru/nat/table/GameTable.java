package ru.nat.table;

import ru.nat.entity.Game;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class GameTable extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Title", "Genre", "Score", "% achievement", "Cost"};
    private final List<Game> games;

    public GameTable(List<Game> games) {
        this.games = games;
    }

    @Override
    public int getRowCount() {
        return games.size();
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
        Game g = games.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> g.getId();
            case 1 -> g.getName();
            case 2 -> g.getGenre();
            case 3 -> g.getScore();
            case 4 -> g.getPercentageAchievement();
            case 5 -> g.getCost();
            default -> null;
        };
    }
}
