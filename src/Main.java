import javax.swing.*;
import java.awt.*;
import java.util.Random;

class TicTacToe extends JFrame {
    JLabel displayLabel;
    JPanel displayPanel;
    JPanel buttonPanel;
    JButton[][] buttons;
    String playerX = "X";
    String playerO = "O";
    Random random = new Random();
    boolean gameOver = false;
    int moveCount = 0;

    TicTacToe() {
        setTitle("TicTacToe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/TicTac.png"));
        setIconImage(icon.getImage());

        displayLabel = new JLabel();
        displayLabel.setBackground(new Color(109,223,223));
        displayLabel.setForeground(Color.BLACK);
        displayLabel.setFont(new Font("Serif", Font.BOLD, 45));
        displayLabel.setHorizontalAlignment(JLabel.CENTER);
        displayLabel.setBorder(BorderFactory.createLineBorder(new Color(6,102,127),5));
        displayLabel.setText("Welcome to TicTacToe!");
        displayLabel.setOpaque(true);

        displayPanel = new JPanel();
        displayPanel.add(displayLabel);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0,198,201,255));
        buttonPanel.setLayout(new GridLayout(3,3));

        buttons = new JButton[3][3];

         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(new Color(4,235,152));
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 120));
                buttons[i][j].setForeground(Color.WHITE);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(new Color(0,137,89),5));
                buttons[i][j].setFocusable(false);

                buttons[i][j].addActionListener(e -> {
                    playerMove((JButton) e.getSource());
                });

                buttonPanel.add(buttons[i][j]);
            }
        }

        add("Center", buttonPanel);
        add("North",displayPanel);
        setVisible(true);
    }

    public void playerMove(JButton button) {
        if (!gameOver && button.getText().equals("")) {
            button.setText(playerX);
            moveCount++;

            if (winCheck(playerX)) {
                displayLabel.setText("You Won!");
                endGame();
                restartGame();

            }else {
                if (moveCount == 9) {
                    displayLabel.setText("It's a tie!");
                    restartGame();
                }else {
                    bot();
                    displayLabel.setText("Bot is Thinking...");
                }
            }
        }
    }

    public void bot() {
        if (gameOver) {
            return;
        }

        Timer timer = new Timer(1000, e -> { //Delaying the Execution of Bot
            int index1, index2;
            do {
                index1 = random.nextInt(3);
                index2 = random.nextInt(3);
            } while (!buttons[index1][index2].getText().equals(""));

            buttons[index1][index2].setText(playerO);
            displayLabel.setText("Your Turn");
            moveCount++;

            if (winCheck(playerO)) {
                displayLabel.setText("Bot Won");
                endGame();
                restartGame();
                return;
            }

            if (moveCount == 9) {
                displayLabel.setText("It's a tie!");
                restartGame();
            }
        });

        timer.setRepeats(false);
        timer.start();
    }


    private boolean winCheck(String player) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(player) && buttons[i][1].getText().equals(player) && buttons[i][2].getText().equals(player)) {
                buttons[i][0].setBackground(new Color(102,255,102));
                buttons[i][1].setBackground(new Color(102,255,102));
                buttons[i][2].setBackground(new Color(102,255,102));
                return true;
            }
            if (buttons[0][i].getText().equals(player) && buttons[1][i].getText().equals(player) && buttons[2][i].getText().equals(player)) {
                buttons[0][i].setBackground(new Color(102,255,102));
                buttons[1][i].setBackground(new Color(102,255,102));
                buttons[2][i].setBackground(new Color(102,255,102));
                return true;
            }
        }

        if (buttons[0][0].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][2].getText().equals(player)) {
            buttons[0][0].setBackground(new Color(102,255,102));
            buttons[1][1].setBackground(new Color(102,255,102));
            buttons[2][2].setBackground(new Color(102,255,102));
            return true;
        }
        if (buttons[0][2].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][0].getText().equals(player)) {
            buttons[0][2].setBackground(new Color(102,255,102));
            buttons[1][1].setBackground(new Color(102,255,102));
            buttons[2][0].setBackground(new Color(102,255,102));
            return true;
        }

        return false;
    }

    public void endGame() {
        gameOver = true;

        for(JButton[] row : buttons){
            for(JButton button : row){
                button.setEnabled(false);
            }
        }
    }

    private void restartGame() {
        int response = JOptionPane.showConfirmDialog(this, "Game over! Do you want to play again?", "Restart Game", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            for (JButton[] row : buttons) {
                for (JButton button : row) {
                    button.setText("");
                    button.setEnabled(true);
                    button.setBackground(new Color(4,235,152));
                }
            }
            moveCount = 0;
            gameOver = false;
            displayLabel.setText("Welcome to TicTacToe!");
        } else {
            System.exit(0);
        }
    }

}

public class Main {
    public static void main(String[] args) {
        new TicTacToe();
    }
}