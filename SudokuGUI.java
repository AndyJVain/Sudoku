// Sudoku Project
// November 2017
// Andy Vainauskas

//
// SudokuGUI creates the main GUI for the game. The class manages
// the data stored and updated in the textFields for the puzzle, and
// also handles the button actions, score keeping, and file saving.
//
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SudokuGUI extends JFrame {
    private JTextField textFields[][] = new JTextField[9][9];
    private JPanel panels[][] = new JPanel[3][3];
    private JPanel board = new JPanel();
    private JPanel sidePanel;
    private int[][] unsolvedPuzzle = new int[9][9];
    private int[][] solution = new int[9][9];
    private SudokuTimer timer;
    private SudokuScores scores;
    private String currentDifficulty;
    private boolean paused;

    // Creates the GUI (menuBar, Sudoku grid, and buttons)
        public SudokuGUI(){
            super("Sudoku Puzzle");
            scores = new SudokuScores();
            currentDifficulty = "";
            paused = false;

            readFile();

            setupBoard();
            sidePanel = createSolverSidePanel();
            add(sidePanel);

            board.setLayout(new GridLayout(3,3));
            board.setPreferredSize(new Dimension(500, 500));
            setJMenuBar(createMenuBar());
            setLayout(new GridBagLayout());
            setSize(700, 700);
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        // Initializes the textField array and adds them to the correct panels
        public void setupBoard() {
            // Sets up the textFields in the textField array and limits the input to one integer 1-9
            for(int i = 0; i <= 8; ++i){
                for(int j = 0; j <= 8; ++j){
                    textFields[i][j] = new JTextField(1);
                    textFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                    textFields[i][j].setBackground(Color.lightGray);
                    ((AbstractDocument) textFields[i][j].getDocument()).setDocumentFilter(new SudokuDocumentFilter());
                }
            }

            // Sets up the panels for the 3x3 sub-grid panels
            for(int i = 0; i <= 2; ++i){
                for(int j = 0; j <= 2; ++j){
                    panels[i][j] = new JPanel(new GridLayout(3,3));
                    panels[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 4));
                }
            }

            // Adds the textFields to the 3x3 panels
            for(int i = 0; i <= 2; ++i){
                for(int j = 0; j <= 2; ++j){
                    for(int x = 0; x <= 2; ++x){
                        for(int y = 0; y <= 2; ++y){
                            panels[i][j].add(textFields[x+i*3][y+j*3]);
                        }
                    }
                    board.add(panels[i][j]);
                }
            }
            add(board);
        }

        // Creates the menuBar for new game, solver, and scores
        public JMenuBar createMenuBar() {
            JMenuBar menuBar = new JMenuBar();

            JMenu file = new JMenu("File");
            JMenu newGame = new JMenu("New Game");
            JMenu scores = new JMenu("Scores");
            menuBar.add(file);
            file.add(newGame);
            menuBar.add(scores);

            JMenuItem solver = new JMenuItem("Solver");
            JMenuItem basic = new JMenuItem("Basic");
            JMenuItem easy = new JMenuItem("Easy");
            JMenuItem medium = new JMenuItem("Medium");
            JMenuItem hard = new JMenuItem("Hard");
            JMenuItem expert = new JMenuItem("Expert");
            JMenuItem showScores = new JMenuItem("Show High Scores");
            JMenuItem resetScores = new JMenuItem("Reset High Scores");
            file.add(newGame);
            file.add(solver);
            newGame.add(basic);
            newGame.add(easy);
            newGame.add(medium);
            newGame.add(hard);
            newGame.add(expert);
            scores.add(showScores);
            scores.add(resetScores);

            solver.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog
                            (null, "All progress will be lost. Continue?","Warning", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        regenerateBoard(81);
                        remove(sidePanel);
                        sidePanel = createSolverSidePanel();
                        add(sidePanel);
                        revalidate();
                        repaint();
                    }
                }
            });

            basic.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog
                            (null, "All progress will be lost. Continue?","Warning", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        currentDifficulty = "basic";
                        regenerateBoard(2);
                        copyUnsolvedPuzzle();
                        remove(sidePanel);
                        sidePanel = createGameSidePanel();
                        add(sidePanel);
                        revalidate();
                        repaint();
                    }
                }
            });

            easy.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog
                            (null, "All progress will be lost. Continue?","Warning", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        currentDifficulty = "easy";
                        regenerateBoard(30);
                        copyUnsolvedPuzzle();
                        remove(sidePanel);
                        sidePanel = createGameSidePanel();
                        add(sidePanel);
                        revalidate();
                        repaint();
                    }
                }
            });

            medium.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog
                            (null, "All progress will be lost. Continue?","Warning", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        currentDifficulty = "medium";
                        regenerateBoard(40);
                        copyUnsolvedPuzzle();
                        remove(sidePanel);
                        sidePanel = createGameSidePanel();
                        add(sidePanel);
                        revalidate();
                        repaint();
                    }
                }
            });

            hard.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog
                            (null, "All progress will be lost. Continue?","Warning", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        currentDifficulty = "hard";
                        regenerateBoard(50);
                        copyUnsolvedPuzzle();
                        remove(sidePanel);
                        sidePanel = createGameSidePanel();
                        add(sidePanel);
                        revalidate();
                        repaint();
                    }
                }
            });

            expert.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog
                            (null, "All progress will be lost. Continue?","Warning", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        currentDifficulty = "expert";
                        regenerateBoard(55);
                        copyUnsolvedPuzzle();
                        remove(sidePanel);
                        sidePanel = createGameSidePanel();
                        add(sidePanel);
                        revalidate();
                        repaint();
                    }
                }
            });

            showScores.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(getContentPane(), getScores(), "High Scores",JOptionPane.PLAIN_MESSAGE);
                }
            });

            resetScores.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog
                            (null, "ALL HIGH SCORES WILL BE RESET. CONTINE?","Warning", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        resetScores();
                        writeFile();
                    }
                }
            });
            return menuBar;
        }

        // Returns the side panel used for the Sudoku game mode (timer, submit, pause, and clear buttons)
        public JPanel createGameSidePanel() {
            JPanel buttonPanel = new JPanel(new GridLayout(5,1));
            JButton submit = new JButton("Submit");
            JButton pause = new JButton("Pause");
            JButton clear = new JButton("Clear");
            JButton giveUp = new JButton("Give Up");

            timer = new SudokuTimer();
            timer.start();
            JPanel timerPanel = timer.getTimerPanel();

            buttonPanel.add(timerPanel);
            buttonPanel.add(submit);
            buttonPanel.add(pause);
            buttonPanel.add(clear);
            buttonPanel.add(giveUp);

            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean solved = checkBoard();
                    if (solved) {
                        submit.setEnabled(false);
                        pause.setEnabled(false);
                        clear.setEnabled(false);
                        giveUp.setEnabled(false);
                    }
                }
            });

            pause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!paused) {
                        timer.pause();
                        hideBoard();
                        pause.setText("Resume");
                        paused = true;
                        submit.setEnabled(false);
                        clear.setEnabled(false);
                        giveUp.setEnabled(false);
                    } else {
                        timer.resume();
                        showBoard();
                        pause.setText("Pause");
                        paused = false;
                        submit.setEnabled(true);
                        clear.setEnabled(true);
                        giveUp.setEnabled(true);
                    }

                }
            });

            clear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 9; ++i) {
                        for (int j = 0; j < 9; ++j) {
                            if (textFields[i][j].getBackground() == Color.lightGray) {
                                textFields[i][j].setText("");
                            }
                        }
                    }
                }
            });

            giveUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.pause();
                    showSolution();
                    submit.setEnabled(false);
                    pause.setEnabled(false);
                    clear.setEnabled(false);
                    giveUp.setEnabled(false);
                }
            });
            return buttonPanel;
        }

        // Returns the side panel used for the Sudoku solver mode (solve and reset buttons)
        public JPanel createSolverSidePanel() {
            JPanel buttonPanel = new JPanel(new GridLayout(2,1));
            JButton solve = new JButton("Solve");
            JButton reset = new JButton("Reset");

            buttonPanel.add(solve);
            buttonPanel.add(reset);

            solve.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    solveBoard();
                }
            });

            reset.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    resetBoard();
                }
            });
            return buttonPanel;
        }

        // Creates a new puzzle based upon the difficulty that is passed in as an int
        public void regenerateBoard(int difficulty) {
            SudokuGenerator newBoard = new SudokuGenerator();
            solution = newBoard.nextBoard(difficulty);

            // Updates the textFields with the new values of the partial puzzle
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    textFields[i][j].setText("");
                    textFields[i][j].setEditable(true);
                    textFields[i][j].setBackground(Color.white);
                    if (solution[i][j] != 0) {
                        textFields[i][j].setText(Integer.toString(solution[i][j]));
                        textFields[i][j].setEditable(false);
                    } else {
                        textFields[i][j].setBackground(Color.lightGray);
                    }
                }
            }

            // Solves the puzzle and saves the solution in the solution 2D int array
            SudokuSolver solver = new SudokuSolver(solution);
            solver.solve(new SudokuSolver.Cell(0,0));
        }

        // Used in the Sudoku solver mode
        public void solveBoard() {
            copyUnsolvedPuzzle();
            SudokuSolver solver = new SudokuSolver(unsolvedPuzzle);

            // Checks if the user entered a board that has invalid entries
            boolean valid = solver.isValidPuzzle();
            if (!valid) {
                JOptionPane.showMessageDialog
                        (getContentPane(), "You have entered a puzzle with errors", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            // Checks if the board entered is solvable or not
            boolean solved = solver.solve(new SudokuSolver.Cell(0, 0));
            if (!solved) {
                JOptionPane.showMessageDialog
                        (getContentPane(), "The Sudoku puzzle cannot be solved", "Error", JOptionPane.PLAIN_MESSAGE);
                return;
            }

            // Adds the integer values back into the textFields
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    textFields[i][j].setText(Integer.toString(unsolvedPuzzle[i][j]));
                    textFields[i][j].setEditable(false);
                }
            }
        }

        // Used in the Sudoku game mode
        public boolean checkBoard() {
            copyUnsolvedPuzzle();

            // Compares the user submission to the correct solution
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    if (unsolvedPuzzle[i][j] != solution[i][j]) {
                        JOptionPane.showMessageDialog
                                (getContentPane(), "The answer is not correct. Try again.", "Incorrect", JOptionPane.PLAIN_MESSAGE);
                        return false;
                    }
                }
            }
            this.timer.pause();
            saveScore(currentDifficulty, timer);
            writeFile();
            JOptionPane.showMessageDialog
                    (getContentPane(), "Correct! You solved it in " + timer.getTime(),
                            "Correct", JOptionPane.PLAIN_MESSAGE);
            return true;
        }

        // Copies the data from the textFields into a 2D array to save for later comparison
        public void copyUnsolvedPuzzle() {
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    if (textFields[i][j].getText().equals("")) {
                        unsolvedPuzzle[i][j] = 0;
                    } else {
                        unsolvedPuzzle[i][j] = Integer.parseInt(textFields[i][j].getText());
                    }
                }
            }
        }

        // Clears the board to empty spaces
        public void resetBoard() {
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    textFields[i][j].setText("");
                    textFields[i][j].setEditable(true);
                }
            }
        }

        // Saves the timer value to the SudokuScores object
        public void saveScore(String difficulty, SudokuTimer timer) {
            scores.updateHighScore(difficulty, timer);
        }

        // Returns a string of the high scores to display in a dialog box
        public String getScores() {
            String result = scores.getScores();
            return result;
        }

        // Resets high scores
        public void resetScores() {
            scores.reset();
        }

        // Hides the board when the pause button is pressed
        public void hideBoard() {
            copyUnsolvedPuzzle();
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    textFields[i][j].setText("");
                    textFields[i][j].setEditable(false);
                }
            }
        }

        // Shows the board when the resume button is pressed
        public void showBoard() {
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    if (unsolvedPuzzle[i][j] == 0) {
                        textFields[i][j].setText("");
                        textFields[i][j].setEditable(true);
                    } else {
                        textFields[i][j].setText(Integer.toString(unsolvedPuzzle[i][j]));
                        textFields[i][j].setEditable(true);
                    }
                }
            }
        }

        // Used for the "Give Up" button, shows the correct solution
        public void showSolution() {
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    textFields[i][j].setText("");
                    textFields[i][j].setText(Integer.toString(solution[i][j]));
                    textFields[i][j].setEditable(false);
                }
            }
        }

        // Reloads previous high scores from the "sudoku_scores.dat" file into the SudokuScores object
        public void readFile() {
            File f = new File("sudoku_scores.dat");
            if(f.exists() && !f.isDirectory()) {
                ObjectInputStream objectIn = null;
                try {
                    FileInputStream fileIn = new FileInputStream("sudoku_scores.dat");
                    objectIn = new ObjectInputStream(fileIn);
                    scores = (SudokuScores) objectIn.readObject();
                    objectIn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Saves the high scores to the "sudoku_scores.dat" file
        public void writeFile() {
            ObjectOutputStream objectOut = null;
            FileOutputStream fileOut = null;
            try{
                fileOut = new FileOutputStream("sudoku_scores.dat");
                objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(scores);
                objectOut.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static void main(String[] args) {
            SudokuGUI gui = new SudokuGUI();
        }
}