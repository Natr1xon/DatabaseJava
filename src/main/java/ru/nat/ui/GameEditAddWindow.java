package ru.nat.ui;

import ru.nat.entity.Game;
import ru.nat.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

import static ru.nat.ui.MainWindow.GAME_SERVICE;

public class GameEditAddWindow extends JDialog {
    private JTextField nameField;
    private JTextField genreField;
    private JTextField scoreField;
    private JTextField achievementField;
    private JTextField costField;

    private JButton saveButton;
    private JButton closeButton;

    private Game editingGame = null;
    private Player associatedPlayer;

    private MainWindow mainWindow;

    public GameEditAddWindow(MainWindow parent, Player player) {
        super(parent, "Add Game", true);
        this.mainWindow = parent;
        this.associatedPlayer = player;
        initFields();
        initComponents(false);
    }

    public GameEditAddWindow(MainWindow parent, Game game) {
        super(parent, "Edit Game", true);
        this.mainWindow = parent;
        this.editingGame = game;
        this.associatedPlayer = game.getPlayer();
        initFieldsWithGame(game);
        initComponents(true);
    }

    private void initFields() {
        nameField = new JTextField(20);
        genreField = new JTextField(20);
        scoreField = new JTextField(20);
        achievementField = new JTextField(20);
        costField = new JTextField(20);
    }

    private void initFieldsWithGame(Game game) {
        nameField = new JTextField(game.getName(), 20);
        genreField = new JTextField(game.getGenre(), 20);
        scoreField = new JTextField(String.valueOf(game.getScore()), 20);
        achievementField = new JTextField(String.valueOf(game.getPercentageAchievement()), 20);
        costField = new JTextField(String.valueOf(game.getCost()), 20);
    }

    private void initComponents(boolean editMode) {
        setMinimumSize(new Dimension(400, 200));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 5, 5));

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String genre = genreField.getText();
                int score = Integer.parseInt(scoreField.getText());
                double achievement = Double.parseDouble(achievementField.getText());
                BigDecimal cost = new BigDecimal(costField.getText());

                if (editMode && editingGame != null) {
                    editingGame.setName(name);
                    editingGame.setGenre(genre);
                    editingGame.setScore(score);
                    editingGame.setPercentageAchievement(achievement);
                    editingGame.setCost(cost);
                    GAME_SERVICE.updateEntity(editingGame);
                    JOptionPane.showMessageDialog(this, "Game '" + name + "' successfully updated!");
                } else {
                    Game newGame = new Game(name, genre, score, achievement, cost, associatedPlayer);
                    GAME_SERVICE.createEntity(newGame);
                    JOptionPane.showMessageDialog(this, "Game '" + name + "' successfully created!");
                }

                mainWindow.refreshPlayersTable();

                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при сохранении: " + ex.getMessage());
            }
        });

        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Genre:"));
        add(genreField);
        add(new JLabel("Score:"));
        add(scoreField);
        add(new JLabel("Achievement %:"));
        add(achievementField);
        add(new JLabel("Cost:"));
        add(costField);
        add(saveButton);
        add(closeButton);
    }
}
