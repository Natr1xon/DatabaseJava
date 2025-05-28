package ru.nat.ui;

import ru.nat.entity.Game;
import ru.nat.entity.Player;
import ru.nat.service.GameService;
import ru.nat.service.PlayerService;
import ru.nat.table.GameTable;
import ru.nat.table.PlayerTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MainWindow extends JFrame {
    private static final int MAX_SZ = GroupLayout.DEFAULT_SIZE;
    private JPanel playerPanel;
    private JPanel gamePanel;

    private JMenuBar menuBar;

    private JTable tablePlayers;
    private JTable tableGames;

    public static final PlayerService PLAYER_SERVICE = new PlayerService();
    public static final GameService GAME_SERVICE = new GameService();

    public MainWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 500));

        playerPanel = new JPanel();
        playerPanel.setBackground(Color.white);
        gamePanel = new JPanel();
        gamePanel.setBackground(Color.white);
        setupLayout();

        tableGames = new JTable();
        tablePlayers = new JTable();

        menuBar = new JMenuBar();
        JMenu file = getJMenu();
        menuBar.add(file);
        setJMenuBar(menuBar);
    }

    public void refreshPlayersTable() {
        List<Player> players = PLAYER_SERVICE.getAllEntities();
        tablePlayers.setModel(new PlayerTable(players));
        attachPlayerContextMenu(tablePlayers);
        loadStyleTable(tablePlayers, playerPanel);
        refreshGamesTable(null);
    }

    public void refreshGamesTable(Player player) {
        if (player == null) {
            tableGames.setModel(new GameTable(List.of()));
        } else {
            tableGames.setModel(new GameTable(player.getGames()));
        }
        attachGameContextMenu(tableGames);
        loadStyleTable(tableGames, gamePanel);
    }

    private void attachPlayerContextMenu(JTable table) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem addGame = new JMenuItem("Add game");
        JMenuItem edit = new JMenuItem("Edit");
        JMenuItem delete = new JMenuItem("Delete");

        addGame.addActionListener(e->{
            int row = table.getSelectedRow();
            if (row >= 0) {
                Long id = (Long) table.getValueAt(row, 0);
                Player p = PLAYER_SERVICE.getEntityById(id);
                if (p != null) {
                    GameEditAddWindow newGame = new GameEditAddWindow(this,p);
                    newGame.setVisible(true);
                }
            }
        });

        edit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Long id = (Long) table.getValueAt(row, 0);
                Player p = PLAYER_SERVICE.getEntityById(id);
                if (p != null) {
                    PlayerEditAddWindow editWindow = new PlayerEditAddWindow(this, p);
                    editWindow.setVisible(true);
                }
            }
        });

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Long id = (Long) table.getValueAt(row, 0);
                PLAYER_SERVICE.deleteEntity(id);
                refreshPlayersTable();
            }
        });

        menu.add(addGame);
        menu.add(edit);
        menu.add(delete);

        table.addMouseListener(new PopupMouseAdapter(table, menu));
    }

    private void attachGameContextMenu(JTable table) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem edit = new JMenuItem("Edit");
        JMenuItem delete = new JMenuItem("Delete");

        edit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Long id = (Long) table.getValueAt(row, 0);
                Game g = GAME_SERVICE.getEntityById(id);
                if (g != null) {
                    GameEditAddWindow editWindow = new GameEditAddWindow(this, g);
                    editWindow.setVisible(true);
                }
            }
        });

        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Long id = (Long) table.getValueAt(row, 0);
                GAME_SERVICE.deleteEntity(id);
                refreshPlayersTable();
            }
        });

        menu.add(edit);
        menu.add(delete);

        table.addMouseListener(new PopupMouseAdapter(table, menu));
    }

    private JMenu getJMenu() {
        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem add = new JMenuItem("Add");

        load.addActionListener(l -> {
            refreshPlayersTable();

            tablePlayers.getSelectionModel().addListSelectionListener(e -> {
                int selectedRow = tablePlayers.getSelectedRow();
                if (selectedRow >= 0 && selectedRow < tablePlayers.getRowCount()) {
                    Long playerId = (Long) tablePlayers.getValueAt(selectedRow,0);
                    Player player = PLAYER_SERVICE.getEntityById(playerId);
                    if(player != null){
                        refreshGamesTable(player);
                    }
                } else {
                    refreshGamesTable(null);
                }
            });
        });

        add.addActionListener(l -> {
            PlayerEditAddWindow newPlayer = new PlayerEditAddWindow(this);
            newPlayer.setVisible(true);
        });

        file.add(load);
        file.add(add);
        return file;
    }

    private void loadStyleTable(JTable table, JPanel panel) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowSelectionAllowed(true);
        table.setSelectionBackground(Color.LIGHT_GRAY);

        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    private void setupLayout() {
        GroupLayout gl = new GroupLayout(getContentPane());
        getContentPane().setLayout(gl);

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(10)
                .addComponent(playerPanel, MAX_SZ, MAX_SZ, MAX_SZ)
                .addGap(10)
                .addComponent(gamePanel, MAX_SZ, MAX_SZ, MAX_SZ)
                .addGap(10)
        );

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addGap(10)
                .addGroup(gl.createParallelGroup()
                        .addGap(10)
                        .addComponent(playerPanel, MAX_SZ, MAX_SZ, MAX_SZ)
                        .addGap(10)
                        .addComponent(gamePanel, MAX_SZ, MAX_SZ, MAX_SZ)
                )
                .addGap(10)
        );
    }

    private static class PopupMouseAdapter extends MouseAdapter {
        private final JTable table;
        private final JPopupMenu menu;

        public PopupMouseAdapter(JTable table, JPopupMenu menu) {
            this.table = table;
            this.menu = menu;
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < table.getRowCount()) {
                    table.setRowSelectionInterval(row, row);
                    menu.show(table, e.getX(), e.getY());
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }
    }

}
