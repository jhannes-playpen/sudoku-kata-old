package com.brodwall.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements Board {
    private static final int SIZE = 9;
    private Integer[][] cells = new Integer[SIZE][SIZE];

    public List<Integer> getOptionsForCell(int row, int col) {
        if (!isEmpty(row,col)) return Arrays.asList(cells[row][col]);
        List<Integer> result = allOptions();
        removeAllInRow(result, row);
        removeAllInCol(result, col);
        removeAllInBox(result, row, col);
        return result;
    }

    public int getSize() {
        return SIZE;
    }

    private void removeAllInBox(List<Integer> result, int row, int col) {
        for (int rowOffset = 0; rowOffset<3; rowOffset++) {
            for (int colOffset = 0; colOffset<3; colOffset++) {
                result.remove(getCellValue(getBoxStart(row)+rowOffset, getBoxStart(col) + colOffset));
            }
        }
    }

    private void removeAllInRow(List<Integer> result, int row) {
        for (int col=0; col<getSize(); col++)
            result.remove(getCellValue(row, col));
    }

    private void removeAllInCol(List<Integer> result, int col) {
        for (int row=0; row<getSize(); row++)
            result.remove(getCellValue(row, col));
    }

    private Integer getCellValue(int row, int col) {
        return cells[row][col];
    }

    private List<Integer> allOptions() {
        return new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    }

    public void setValueInCell(int value, int row, int col) {
        cells[row][col] = value;
    }

    public void clearValueInCell(int row, int col) {
        cells[row][col] = null;
    }

    public boolean isEmpty(int row, int col) {
        return cells[row][col] == null;
    }

    int getBoxStart(int colOrRow) {
        return colOrRow - colOrRow%3;
    }
}
