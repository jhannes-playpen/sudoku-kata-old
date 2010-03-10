package com.brodwall.sudoku;

import java.util.List;

public class SudokuSolver {
    private static final int SIZE = 9;

    public boolean findSolution(Board board) {
        return findSolution(board, 0);
    }

    private boolean findSolution(Board board, int cell) {
        if (cell == SIZE*SIZE) return true;

        boolean wasEmpty = board.isEmpty(row(cell), col(cell));
        for (Integer value : board.getOptionsForCell(row(cell), col(cell))) {
            board.setValueInCell(value, row(cell), col(cell));
            if (findSolution(board, cell+1)) return true;
        }
        if (wasEmpty) board.clearValueInCell(row(cell), col(cell));

        return false;
    }

    private int col(int cell) {
        return cell%SIZE;
    }

    private int row(int cell) {
        return cell/SIZE;
    }
}
