import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game extends JFrame {
    private static final String[] MAZE_STR = {
        "11111111111111111111111110000011111111111111111111111",
        "10000001000000001000000010111010000010000000010000001",
        "10111101011111101011111010111010111010111111010111101",
        "P0000000000000000000000000G0G000000000000000000000001",
        "10111101011111101011111010111010111010111111010111101",
        "10000001000000001000000010000010000010000000010000001",
        "11111111111111111111111110000011111111111111111111111"
    };
    private char[][] maze;
    private int pacmanRow = 3;
    private int pacmanCol = 0;
    private int score = 0;
    private final int CELL_SIZE = 20;

    public Game() {
        maze = new char[MAZE_STR.length][MAZE_STR[0].length()];
        for (int i = 0; i < MAZE_STR.length; i++) {
            maze[i] = MAZE_STR[i].toCharArray();
        }

        setTitle("GitHub Contribution Pac-Man - Score: 0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        GamePanel panel = new GamePanel();
        add(panel);
        pack();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int r = pacmanRow;
                int c = pacmanCol;
                int code = e.getKeyCode();

                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    if (r > 0 && maze[r-1][c] != '1') r--;
                } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    if (r < maze.length - 1 && maze[r+1][c] != '1') r++;
                } else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
                    if (c > 0 && maze[r][c-1] != '1') c--;
                } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
                    if (c < maze[0].length - 1 && maze[r][c+1] != '1') c++;
                }

                if (maze[r][c] == '0') {
                    maze[r][c] = ' ';
                    score += 10;
                    setTitle("GitHub Contribution Pac-Man - Score: " + score);
                }

                pacmanRow = r;
                pacmanCol = c;
                panel.repaint();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class GamePanel extends JPanel {
        public GamePanel() {
            setPreferredSize(new Dimension(MAZE_STR[0].length() * CELL_SIZE, MAZE_STR.length * CELL_SIZE));
            setBackground(Color.decode("#0d1117"));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (int r = 0; r < maze.length; r++) {
                for (int c = 0; c < maze[0].length; c++) {
                    int x = c * CELL_SIZE;
                    int y = r * CELL_SIZE;

                    g2d.setColor(Color.decode("#161b22"));
                    g2d.fillRect(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);

                    char cell = maze[r][c];
                    if (cell == '1') {
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    } else if (cell == '0') {
                        g2d.setColor(Color.decode("#ffdf00"));
                        g2d.fillOval(x + 8, y + 8, CELL_SIZE - 16, CELL_SIZE - 16);
                    } else if (cell == 'G') {
                        g2d.setColor(Color.decode("#ff5555"));
                        g2d.fillOval(x + 2, y + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                    }
                }
            }

            int px = pacmanCol * CELL_SIZE;
            int py = pacmanRow * CELL_SIZE;
            g2d.setColor(Color.decode("#ffdf00"));
            g2d.fillArc(px + 2, py + 2, CELL_SIZE - 4, CELL_SIZE - 4, 30, 300);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}