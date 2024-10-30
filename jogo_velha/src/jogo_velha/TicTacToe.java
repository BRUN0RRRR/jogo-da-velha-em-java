import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame {

    private JButton[][] buttons = new JButton[3][3]; // Botões do tabuleiro 3x3
    private boolean playerXTurn = true; // Controla a vez dos jogadores
    private JLabel statusLabel; // Exibe o status do jogo

    public TicTacToe() {
        // Configurações da janela principal
        setTitle("Jogo da Velha");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel do tabuleiro
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));

        // Cria os botões do tabuleiro
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                boardPanel.add(buttons[i][j]);

                // Adiciona ação aos botões do tabuleiro
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttonClicked((JButton) e.getSource());
                    }
                });
            }
        }

        // Rótulo de status
        statusLabel = new JLabel("Vez do Jogador X");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Botão de reiniciar
        JButton resetButton = new JButton("Reiniciar Jogo");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        // Adiciona os componentes à janela
        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(resetButton, BorderLayout.SOUTH);
    }

    // Método para tratar o clique nos botões do tabuleiro
    private void buttonClicked(JButton button) {
        if (!button.getText().equals("")) return; // Ignora se o botão já estiver marcado

        if (playerXTurn) {
            button.setText("X");
            statusLabel.setText("Vez do Jogador O");
        } else {
            button.setText("O");
            statusLabel.setText("Vez do Jogador X");
        }

        playerXTurn = !playerXTurn;

        checkForWin(); // Verifica se há um vencedor após cada jogada
    }

    // Método para verificar se há um vencedor ou empate
    private void checkForWin() {
        String winner = "";

        // Verifica linhas, colunas e diagonais
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][1].getText().equals(buttons[i][2].getText()) &&
                !buttons[i][0].getText().equals("")) {
                winner = buttons[i][0].getText();
            }
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[1][i].getText().equals(buttons[2][i].getText()) &&
                !buttons[0][i].getText().equals("")) {
                winner = buttons[0][i].getText();
            }
        }

        // Verifica diagonais
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][2].getText()) &&
            !buttons[0][0].getText().equals("")) {
            winner = buttons[0][0].getText();
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][0].getText()) &&
            !buttons[0][2].getText().equals("")) {
            winner = buttons[0][2].getText();
        }

        if (!winner.equals("")) {
            statusLabel.setText("Jogador " + winner + " vence!");
            disableBoard();
        } else if (isBoardFull()) {
            statusLabel.setText("Empate!");
            disableBoard();
        }
    }

    // Método para desativar os botões do tabuleiro após o fim do jogo
    private void disableBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    // Método para verificar se o tabuleiro está cheio (empate)
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para reiniciar o jogo
    private void resetGame() {
        playerXTurn = true;
        statusLabel.setText("Vez do Jogador X");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.setVisible(true);
        });
    }
}
