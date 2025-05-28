package ru.nat.ui;

import ru.nat.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

import static ru.nat.ui.MainWindow.PLAYER_SERVICE;

public class PlayerEditAddWindow extends JDialog {
    private JTextField nicknameField;
    private JTextField levelField;
    private JTextField cashBalanceField;

    private JButton saveButton;
    private JButton closeButton;

    private Player editingPlayer = null;

    private MainWindow mainWindow;

    public PlayerEditAddWindow(MainWindow parent){
        super(parent,"Add player", true);
        this.mainWindow = parent;
        initFields();
        initComponents(false);
    }

    public PlayerEditAddWindow(MainWindow parent, Player player){
        super(parent,"Edit player", true);
        this.mainWindow = parent;
        this.editingPlayer = player;
        initFieldsWithPlayer(player);
        initComponents(true);
    }

    private void initFields() {
        nicknameField = new JTextField(20);
        levelField = new JTextField(20);
        cashBalanceField = new JTextField(20);
    }

    private void initFieldsWithPlayer(Player player) {
        nicknameField = new JTextField(player.getNickname(), 20);
        levelField = new JTextField(String.valueOf(player.getLevel()), 20);
        cashBalanceField = new JTextField(String.valueOf(player.getCashBalance()), 20);
    }

    private void initComponents(boolean editMode) {
        setMinimumSize(new Dimension(300,150));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 5, 5));

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                String nickname = nicknameField.getText();
                int level = Integer.parseInt(levelField.getText());
                BigDecimal cash = new BigDecimal(cashBalanceField.getText());

                if (editMode && editingPlayer != null) {
                    editingPlayer.setNickname(nickname);
                    editingPlayer.setLevel(level);
                    editingPlayer.setCashBalance(cash);
                    PLAYER_SERVICE.updateEntity(editingPlayer);
                    JOptionPane.showMessageDialog(this,"Player '" + nickname + "' successfully updated!");
                } else {
                    Player newPlayer = new Player(nickname, cash, level);
                    PLAYER_SERVICE.createEntity(newPlayer);
                    JOptionPane.showMessageDialog(this,"Player '" + nickname + "' successfully created!");
                }

                mainWindow.refreshPlayersTable();

                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при сохранении: " + ex.getMessage());
            }
        });

        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        add(new JLabel("Nickname: "));
        add(nicknameField);
        add(new JLabel("Level: "));
        add(levelField);
        add(new JLabel("Cash balance: "));
        add(cashBalanceField);
        add(saveButton);
        add(closeButton);
    }
}
