// Sudoku Project
// November 2017
// Andy Vainauskas

// Reference: https://github.com/SomeKittens/Sudoku-Project/blob/master/SudokuGenerator.java

//
// SudokuGenerator uses an algorithm to generate a new Sudoku
// puzzle depending on the desired difficulty level. The difficulty is
// based upon the number of holes that is generated in the makeHoles() method.
//
import java.util.*;

public class SudokuGenerator {
    int[][] board;

    public SudokuGenerator() {
        board = new int[9][9];
    }

    // Creates and returns a 2D int array for a new Sudoku puzzle
    public int[][] nextBoard(int difficulty)
    {
        board = new int[9][9];
        nextCell(0,0);
        makeHoles(difficulty);
        return board;
    }

    // Recursive method that cycles through every possible number to place in a cell.
    public boolean nextCell(int x, int y)
    {
        int nextX = x;
        int nextY = y;
        int[] toCheck = {1,2,3,4,5,6,7,8,9};
        Random random = new Random();
        int tmp = 0;
        int current = 0;
        int top = toCheck.length;

        // Randomly selects numbers 1-9 to enter into the puzzle
        for(int i = top-1; i > 0; --i)
        {
            current = random.nextInt(i);
            tmp = toCheck[current];
            toCheck[current] = toCheck[i];
            toCheck[i] = tmp;
        }

        for(int i = 0; i < toCheck.length; ++i)
        {
            if(legalMove(x, y, toCheck[i]))
            {
                board[x][y] = toCheck[i];
                if(x == 8)
                {
                    if(y == 8)
                        return true;
                    else
                    {
                        nextX = 0;
                        nextY = y + 1;
                    }
                } else {
                    nextX = x + 1;
                }
                if(nextCell(nextX, nextY))
                    return true;
            }
        }
        board[x][y] = 0;
        return false;
    }

    // Determines if the value "current" is allowed to be placed in the puzzle based on the coordinates x and y
    private boolean legalMove(int x, int y, int current) {
        for(int i=0;i<9;i++) {
            if(current == board[x][i])
                return false;
        }

        for(int i=0;i<9;i++) {
            if(current == board[i][y])
                return false;
        }

        int cornerX = 0;
        int cornerY = 0;
        if(x > 2)
            if(x > 5)
                cornerX = 6;
            else
                cornerX = 3;

        if(y > 2)
            if(y > 5)
                cornerY = 6;
            else
                cornerY = 3;

        for(int i = cornerX; i < 10 && i < cornerX+3; ++i)
            for(int j = cornerY;j < 10 && j < cornerY+3; ++j)
                if(current == board[i][j])
                    return false;
        return true;
    }

    // Randomly assigns a variable amount of blank spaces (int holesToMake) to the board.
    public void makeHoles(int holesToMake)
    {
		/* We define difficulty as follows:
			Easy: 32+ clues (49 or fewer holes)
			Medium: 27-31 clues (50-54 holes)
			Hard: 26 or fewer clues (54+ holes)
		*/
        double remainingSquares = 81;
        double remainingHoles = (double)holesToMake;

        for(int i = 0; i < 9; ++i)
            for(int j = 0; j < 9; ++j)
            {
                double holeChance = remainingHoles/remainingSquares;
                if(Math.random() <= holeChance)
                {
                    board[i][j] = 0;
                    remainingHoles--;
                }
                remainingSquares--;
            }
    }
}