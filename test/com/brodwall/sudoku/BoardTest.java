package com.brodwall.sudoku;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class BoardTest {
    private SudokuBoard board = new SudokuBoard();

    @Test
    public void shouldReturnAllOptionsWhenNothingBlocksCell() {
        assertThat(board.getOptionsForCell(0,0)).containsExactly(1,2,3,4,5,6,7,8,9);
        assertThat(board.getOptionsForCell(4,0)).containsExactly(1,2,3,4,5,6,7,8,9);
        assertThat(board.getOptionsForCell(0,4)).containsExactly(1,2,3,4,5,6,7,8,9);
        assertThat(board.getOptionsForCell(4,4)).containsExactly(1,2,3,4,5,6,7,8,9);
    }

    @Test
    public void shouldDisallowOptionsInSameRow() throws Exception {
        int row = 4;
        board.setValueInCell(1, row, 5);
        board.setValueInCell(2, row, 8);
        board.setValueInCell(3, row+1, 5);
        assertThat(board.getOptionsForCell(row, 0))
                .excludes(1,2).contains(3);
    }

    @Test
    public void shouldDisallowOptionsInSameColumn() throws Exception {
        int col = 6;
        board.setValueInCell(1, 4, col);
        board.setValueInCell(2, 5, col);
        board.setValueInCell(3, 5, col+1);
        assertThat(board.getOptionsForCell(1, col))
                .excludes(1,2).contains(3);
    }

    @Test
    public void shouldFindBox() throws Exception {
        assertThat(board.getBoxStart(0)).isEqualTo(board.getBoxStart(2)).isEqualTo(0);

        int row = 7;
        int col = 3;
        assertThat(board.getBoxStart(row)).isEqualTo(board.getBoxStart(row+1)).isEqualTo(6);
        assertThat(board.getBoxStart(col)).isEqualTo(board.getBoxStart(col+1)).isEqualTo(3);
    }

    @Test
    public void shouldEliminateOptionsInBox() throws Exception {
        board.setValueInCell(1, 7,3);
        board.setValueInCell(2, 8,4);
        board.setValueInCell(3, 5,3);
        board.setValueInCell(4, 7,6);

        assertThat(board.getOptionsForCell(6,5)).excludes(1,2).contains(3,4);
    }

    @Test
    public void filledCellShouldOnlyAllowFilledOption() throws Exception {
        board.setValueInCell(1, 1,1);
        assertThat(board.getOptionsForCell(1,1)).containsOnly(1);
    }

    @Test
    public void shouldBeEmptyWhenNotFilled() throws Exception {
                                      board.setValueInCell(2, 0,1); board.setValueInCell(3, 0,2);
        board.setValueInCell(4, 1,0); board.setValueInCell(5, 1,1); board.setValueInCell(6, 1,2);
        board.setValueInCell(7, 2,0); board.setValueInCell(8, 2,1); board.setValueInCell(9, 2,2);
        assertThat(board.getOptionsForCell(0,0)).containsOnly(1);

        assertThat(board.isEmpty(0,0)).isTrue();
    }

    @Test
    public void shouldNotBeEmptyWhenFilled() throws Exception {
        board.setValueInCell(1, 0,0);
        assertThat(board.getOptionsForCell(0,0)).containsOnly(1);

        assertThat(board.isEmpty(0,0)).isFalse();
    }

    @Test
    public void clearingValueShouldOpenOptions() throws Exception {
        board.setValueInCell(1, 0,0);
        board.clearValueInCell(0,0);
        assertThat(board.getOptionsForCell(0,0)).hasSize(board.getSize());
        assertThat(board.isEmpty(0,0)).isTrue();

    }
}
