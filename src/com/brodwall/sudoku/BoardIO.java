package com.brodwall.sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BoardIO {

    public SudokuBoard readBoardAsSingleLine(String boardAsSingleLine) {
        SudokuBoard board = new SudokuBoard();
        int index = 0;
        for (int row=0; row < board.getSize(); row++) {
            for (int col=0; col < board.getSize(); col++) {
                String value = "" + boardAsSingleLine.charAt(index++);
                if (!value.equals(".")) {
                    board.setValueInCell(Integer.parseInt(value), row, col);
                }
            }
        }
        return board;
    }

    public String printBoard(SudokuBoard board) {
        StringBuilder result = new StringBuilder();
        for (int row=0; row < board.getSize(); row++) {
            for (int col=0; col < board.getSize(); col++) {
                result.append(board.isEmpty(row, col) ? "." : board.getOptionsForCell(row, col).get(0).toString());
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static StringBuilder repeat(char c, int repetitions) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<repetitions; i++) builder.append(c);
        return builder;
    }

    public static void main(String[] args) throws IOException {
        BoardIO boardIO = new BoardIO();
        for (String file : args) {
            long start = System.currentTimeMillis();
            int puzzles = boardIO.solveFile(new BufferedReader(new FileReader(file)));
            long duration = System.currentTimeMillis() - start;
            System.out.println("Solved " + puzzles + " puzzles in " + file + " in " + (duration/1000.0) + "s");
        }
    }

    private int solveFile(BufferedReader bufferedReader) throws IOException {
        SudokuSolver solver = new SudokuSolver();
        String line;
        int puzzles = 0;
        while ((line = bufferedReader.readLine()) != null) {
            SudokuBoard board = new SudokuBoard();
            if (!solver.findSolution(board)) {
                System.err.println("Failed to find solution for " + line);
            }
            puzzles++;
        }
        return puzzles;
    }
}
