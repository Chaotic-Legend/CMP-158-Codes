// Project 2 YouTube Video Link: https://youtu.be/1fW6jxykyv4

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Grid {
    private int rows = 10;          // Grid rows
    private int columns = 10;       // Grid columns
    private int numBombs = 25;      // Number of bombs
    private boolean[][] bombGrid;   // Stores bomb locations
    private int[][] countGrid;      // Stores counts of surrounding bombs
    private JButton[][] buttons;    // Buttons for GUI
    private boolean gameOver = false; // Flag to track game state
    private JFrame frame;           // JFrame for the GUI
    private boolean[][] flaggedGrid; // To track flagged cells

    // Default constructor
    public Grid() {
        flaggedGrid = new boolean[rows][columns];
        bombGrid = new boolean[rows][columns];
        countGrid = new int[rows][columns];
        buttons = new JButton[rows][columns];
        createBombGrid();
        createCountGrid();
    }

    // Overloaded constructor to specify grid size
    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.numBombs = 25;
        bombGrid = new boolean[rows][columns];
        countGrid = new int[rows][columns];
        buttons = new JButton[rows][columns];
        flaggedGrid = new boolean[rows][columns]; // Initialize the flagged grid
        createBombGrid();
        createCountGrid();
    }

    // Overloaded constructor to specify grid size and bomb count
    public Grid(int rows, int columns, int numBombs) {
        this.rows = rows;
        this.columns = columns;
        this.numBombs = numBombs;
        bombGrid = new boolean[rows][columns];
        countGrid = new int[rows][columns];
        buttons = new JButton[rows][columns];
        flaggedGrid = new boolean[rows][columns]; // Initialize the flagged grid
        createBombGrid();
        createCountGrid();
    }

    // Handle right-click (flagging/unflagging)
    private void handleRightClick(int row, int col) {
        if (buttons[row][col].isEnabled()) {
            if (flaggedGrid[row][col]) {
                buttons[row][col].setText("");
                flaggedGrid[row][col] = false;
            } else {
                buttons[row][col].setText("🚩");
                flaggedGrid[row][col] = true;
            }
        }
    }

    // Set up right-click listener for flagging
    private void setupRightClickListener(JButton button, int row, int col) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) { // Right-click
                    handleRightClick(row, col);
                }
            }
        });
    }

    // Private method to create bomb grid
    private void createBombGrid() {
        Random rand = new Random();
        int bombsPlaced = 0;

        while (bombsPlaced < numBombs) {
            int row = rand.nextInt(rows);
            int col = rand.nextInt(columns);
            if (!bombGrid[row][col]) {
                bombGrid[row][col] = true;
                bombsPlaced++;
            }
        }
    }

    // Private method to create count grid based on bomb placement
    private void createCountGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bombGrid[row][col]) {
                    countGrid[row][col] = -1; // -1 represents a bomb
                } else {
                    countGrid[row][col] = countAdjacentBombs(row, col);
                }
            }
        }
    }

    // Count adjacent bombs for a cell
    private int countAdjacentBombs(int row, int col) {
        int count = 0;
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (isInBounds(r, c) && bombGrid[r][c]) {
                    count++;
                }
            }
        }
        return count;
    }

    // Check if a coordinate is within grid bounds
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < columns;
    }

    // Method to start the game and display the GUI
    public void start() {
    // Check if the environment supports GUI (headless check)
    if (GraphicsEnvironment.isHeadless()) {
        System.err.println("Error: The current environment does not support a graphical user interface.");
        System.exit(1);  // Exit if the environment is headless
    }

    // Close the previous frame if it exists
    if (frame != null) {
        frame.dispose();
    }

    frame = new JFrame("Minesweeper By: Isaac D. Hoyos");
    frame.setLayout(new GridLayout(rows, columns));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create buttons for the grid
    for (int row = 0; row < rows; row++) {
        for (int col = 0; col < columns; col++) {
            buttons[row][col] = new JButton();
            buttons[row][col].setPreferredSize(new Dimension(50, 50));
            buttons[row][col].setEnabled(true);
            final int r = row, c = col;
            buttons[row][col].addActionListener((ActionEvent e) -> {
                if (gameOver) return;
                if (bombGrid[r][c]) {
                    revealGrid(); // Reveal all bombs when clicked
                    JOptionPane.showMessageDialog(frame, "Game Over! You clicked on a mine!", "Thank you so much for playing my game!", JOptionPane.INFORMATION_MESSAGE);
                    gameOver = true;
                    promptPlayAgain();
                } else {
                    revealCell(r, c);
                    if (checkWin()) {
                        JOptionPane.showMessageDialog(frame, "Congratulations! You won the Minesweeper game!", "Thank you so much for playing my game!", JOptionPane.INFORMATION_MESSAGE);
                        gameOver = true;
                        promptPlayAgain();
                    }
                }
            });
            setupRightClickListener(buttons[row][col], row, col); // Add right-click listener for flagging
            frame.add(buttons[row][col]);
        }
    }

    frame.pack();
    frame.setLocationRelativeTo(null); // Center the window on the screen
    frame.setVisible(true);
}

    // Reveal the cell when clicked
    private void revealCell(int row, int col) {
        if (isInBounds(row, col) && buttons[row][col].isEnabled()) {
            buttons[row][col].setEnabled(false);
            if (countGrid[row][col] > 0) {
                buttons[row][col].setText(String.valueOf(countGrid[row][col]));
                buttons[row][col].setForeground(getColorForNumber(countGrid[row][col]));
            } else if (countGrid[row][col] == 0) {
                buttons[row][col].setText("");
                // Recursively reveal adjacent cells if count is 0
                revealAdjacentCells(row, col);
            }
        }
    }

    // Recursively reveal adjacent cells with a 0 count
    private void revealAdjacentCells(int row, int col) {
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (isInBounds(r, c) && buttons[r][c].isEnabled() && countGrid[r][c] != -1) {
                    revealCell(r, c);
                }
            }
        }
    }

    // Reveal the entire grid (used when game is over)
    private void revealGrid() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (bombGrid[row][col]) {
                    buttons[row][col].setText("💣");
                    buttons[row][col].setForeground(Color.BLACK);
                } else if (countGrid[row][col] > 0) {
                    buttons[row][col].setText(String.valueOf(countGrid[row][col]));
                    buttons[row][col].setForeground(getColorForNumber(countGrid[row][col]));
                } else {
                    buttons[row][col].setText("");
                }
                buttons[row][col].setEnabled(false);
            }
        }
    }
    
    // Utility method to get the color for a number
    private Color getColorForNumber(int number) {
        return switch (number) {
            case 1 -> Color.BLUE;
            case 2 -> Color.GREEN;
            case 3 -> Color.RED;
            case 4 -> new Color(102, 0, 153);
            case 5 -> new Color(102, 51, 0);
            case 6 -> Color.CYAN;
            case 7 -> Color.ORANGE;
            case 8 -> Color.GRAY;
            default -> Color.BLACK;
        }; // Purple
        // Brown
        // Default fallback color
    }

    // Check if all non-bomb cells are revealed
    private boolean checkWin() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (!bombGrid[row][col] && buttons[row][col].isEnabled()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Prompt the player to play again
    private void promptPlayAgain() {
        int response = JOptionPane.showConfirmDialog(null, "Would you like to play Minesweeper again?", "Game Over! Thank you for playing my game!", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            gameOver = false;  // Reset gameOver flag
            resetGame();       // Reset the game
            start();           // Start a new game
        } else {
            System.exit(0);    // Exit the game
        }
    }

    // Reset the game to its initial state
    private void resetGame() {
        bombGrid = new boolean[rows][columns];
        countGrid = new int[rows][columns];
        buttons = new JButton[rows][columns];
        createBombGrid();
        createCountGrid();
    }

    // Getter methods
    public int getNumRows() {
        return rows;
    }

    public int getNumColumns() {
        return columns;
    }

    public int getNumBombs() {
        return numBombs;
    }

    @SuppressWarnings("ManualArrayToCollectionCopy")
    public boolean[][] getBombGrid() {
        boolean[][] copy = new boolean[bombGrid.length][bombGrid[0].length];
        for (int i = 0; i < bombGrid.length; i++) {
            for (int j = 0; j < bombGrid[i].length; j++) {
                copy[i][j] = bombGrid[i][j];
            }
        }
        return copy;
    }

    @SuppressWarnings("ManualArrayToCollectionCopy")
    public int[][] getCountGrid() {
        int[][] copy = new int[countGrid.length][countGrid[0].length];
        for (int i = 0; i < countGrid.length; i++) {
            for (int j = 0; j < countGrid[i].length; j++) {
            copy[i][j] = countGrid[i][j];
            }
        }
        return copy;
    }

    public boolean isBombAtLocation(int row, int column) {
        return bombGrid[row][column];
    }

    public int getCountAtLocation(int row, int column) {
        return countGrid[row][column];
    }

    public boolean[][] getFlaggedGrid() {
        return flaggedGrid;
    }

    public void setFlaggedGrid(boolean[][] flaggedGrid) {
        this.flaggedGrid = flaggedGrid;
    }
    public static void main(String[] args) {
        // Create an instance of the Grid class and start the game
        Grid game = new Grid();
        game.start();
    }
}