package com.brodwall.sudoku;

import com.brodwall.sudoku.BoardIO;
import com.brodwall.sudoku.SudokuBoard;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class BoardIOTest {
    private BoardIO boardIO = new BoardIO();

    @Test
    public void shouldReadEmptyBoardInSingleLineFormat() {
        StringBuilder emptyBoard = BoardIO.repeat('.', 9*9);
        SudokuBoard board = boardIO.readBoardAsSingleLine(emptyBoard.toString());
        for (int row=0; row<board.getSize(); row++) {
            for (int col=0; col<board.getSize(); col++) {
                assertThat(board.isEmpty(row, col)).isTrue();
            }
        }
    }

    @Test
    public void shouldReadPositionsOnBoardInSingleLineFormat() throws Exception {
        StringBuilder boardWithThreeValues = BoardIO.repeat('.', 9*9);
        boardWithThreeValues.replace(0*9 + 0, 1, "3");
        boardWithThreeValues.replace(3*9 + 7, (3*9 + 7) + 1, "2");
        boardWithThreeValues.replace(8*9 + 8, (8*9 + 8) + 1, "1");
        SudokuBoard board = boardIO.readBoardAsSingleLine(boardWithThreeValues.toString());

        assertThat(board.isEmpty(1,1)).isTrue();
        assertThat(board.getOptionsForCell(0, 0)).containsOnly(3);
        assertThat(board.getOptionsForCell(3, 7)).containsOnly(2);
        assertThat(board.getOptionsForCell(8, 8)).containsOnly(1);
    }

    @Test
    public void shouldPrintOneLinePerRow() throws Exception {
        SudokuBoard board = new SudokuBoard();
        String[] lines = boardIO.printBoard(board).split("\n");
        assertThat(lines).hasSize(9);
        assertThat(lines[0]).isEqualTo(lines[1]).isEqualTo(lines[8])
                .isEqualTo(BoardIO.repeat('.', 9).toString());
    }

    @Test
    public void shouldPrintValuesInRow() throws Exception {
        SudokuBoard board = new SudokuBoard();
        board.setValueInCell(1, 0,0);
        board.setValueInCell(2, 8,8);
        board.setValueInCell(3, 4,7);

        String[] lines = boardIO.printBoard(board).split("\n");
        assertThat(lines[0].charAt(0)).isEqualTo('1');
        assertThat(lines[8].charAt(8)).isEqualTo('2');
        assertThat(lines[4].charAt(7)).isEqualTo('3');        
    }
}
