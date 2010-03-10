package com.brodwall.sudoku;

import java.util.List;

public interface Board {
    public List<Integer> getOptionsForCell(int row, int col);

    void setValueInCell(int value, int row, int col);

    void clearValueInCell(int row, int col);

    boolean isEmpty(int row, int col);
}
