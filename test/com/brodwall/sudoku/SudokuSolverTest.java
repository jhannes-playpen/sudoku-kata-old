package com.brodwall.sudoku;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SudokuSolverTest {
    private SudokuSolver solver = new SudokuSolver();
    private Board board = mock(Board.class);

    @Before
    public void allCellsHaveSingleOption() {
        when(board.getOptionsForCell(anyInt(), anyInt())).thenReturn(singleOption());
    }

    @Test
    public void shouldAnswerTrueWhenAllCellsHaveSingleOptions() {
        assertThat(solver.findSolution(board)).isTrue();
    }

    @Test
    public void shouldAnswerFalseWhenOneCellAsNoOption() throws Exception {
        when(board.getOptionsForCell(8, 8)).thenReturn(noOptions());
        assertThat(solver.findSolution(board)).isFalse();
    }

    @Test
    public void shouldSetValueWhenSingleOption() throws Exception {
        when(board.getOptionsForCell(1,1)).thenReturn(moreOptions(8));
        solver.findSolution(board);
        verify(board).setValueInCell(8, 1,1);
    }

    @Test
    public void shouldBacktrackWhenNoMoreOptions() throws Exception {
        when(board.getOptionsForCell(8, 7)).thenReturn(moreOptions(1, 2));
        when(board.getOptionsForCell(8, 8))
                .thenReturn(noOptions())
                .thenReturn(singleOption());

        assertThat(solver.findSolution(board)).isTrue();
        InOrder order = inOrder(board);
        order.verify(board).setValueInCell(1, 8,7);
        order.verify(board).setValueInCell(2, 8,7);
    }

    @Test
    public void shouldResetWhenBacktracking() throws Exception {
        when(board.isEmpty(8,7)).thenReturn(true);
        when(board.getOptionsForCell(8, 7)).thenReturn(moreOptions(1, 2));
        when(board.getOptionsForCell(8, 8)).thenReturn(noOptions());

        solver.findSolution(board);
        InOrder order = inOrder(board);
        order.verify(board).setValueInCell(1, 8,7);
        order.verify(board).setValueInCell(2, 8,7);
        order.verify(board).clearValueInCell(8,7);
    }

    @Test
    public void shouldOnlyClearEmptyCells() throws Exception {
        when(board.isEmpty(7,7)).thenReturn(false);
        when(board.getOptionsForCell(8,8))
                .thenReturn(noOptions())
                .thenReturn(singleOption());
        solver.findSolution(board);
        verify(board, never()).clearValueInCell(7,7);
    }

    private List<Integer> moreOptions(Integer... options) {
        return Arrays.asList(options);
    }

    private List<Integer> noOptions() {
        return new ArrayList<Integer>();
    }

    private List<Integer> singleOption() {
        return Arrays.asList(1);
    }

}
