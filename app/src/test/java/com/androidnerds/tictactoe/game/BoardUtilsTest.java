package com.androidnerds.tictactoe.game;

import com.androidnerds.tictactoe.game.model.CellState;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.androidnerds.tictactoe.game.GameTestUtils.SPAN_COUNT;
import static com.androidnerds.tictactoe.game.GameTestUtils.getBoard;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardUtilsTest {



    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCalculateRowPositionShouldReturnZeroOrAbove() {
        assertEquals(0, BoardUtils.calculateRowPosition(0, 4));
        assertEquals(1, BoardUtils.calculateRowPosition(5, 4));
    }

    @Test
    public void testCalculateRowPositionShouldReturnNegativeValue() {
        assertEquals(-1, BoardUtils.calculateRowPosition(0, 0));
        assertEquals(-1, BoardUtils.calculateRowPosition(5, 0));
    }

    @Test
    public void testCalculateColumnPositionReturnZeroOrAbove() {
        assertEquals(0, BoardUtils.calculateColumnPosition(0, 4));
        assertEquals(1, BoardUtils.calculateColumnPosition(5, 4));
        assertEquals(0, BoardUtils.calculateColumnPosition(8, 4));
    }

    @Test
    public void testCalculateAdapterPosition() {
        assertEquals(0, BoardUtils.calculateAdapterPosition(0, 0, 4));
        assertEquals(5, BoardUtils.calculateAdapterPosition(1, 1, 4));
    }

    @Test
    public void testGetTotalCellsOnBoard() {
        assertEquals(9, BoardUtils.getTotalCellsOnBoard(3));
        assertEquals(16, BoardUtils.getTotalCellsOnBoard(4));
        assertEquals(25, BoardUtils.getTotalCellsOnBoard(5));
    }

    @Test
    public void testIsIndexOnBoardForValidIndicesShouldReturnTrue() {
        CellState[][] board = getBoard();
        assertTrue(BoardUtils.isIndexOnBoard(board, 0, 0));
        assertTrue(BoardUtils.isIndexOnBoard(board, 0, 3));
        assertTrue(BoardUtils.isIndexOnBoard(board, 3, 0));
        assertTrue(BoardUtils.isIndexOnBoard(board, 3, 3));
    }

    @Test
    public void testIsIndexOnBoardForInvalidIndicesShouldReturnFalse() {
        CellState[][] board = getBoard();
        assertFalse(BoardUtils.isIndexOnBoard(board, -1, -1));
        assertFalse(BoardUtils.isIndexOnBoard(board, 4, 4));
        assertFalse(BoardUtils.isIndexOnBoard(board, 4, 4));
        assertFalse(BoardUtils.isIndexOnBoard(board, 0, 4));
    }

    @Test
    public void testIsIndexOnBoardWhenBoardIsNullShouldReturnFalse() {
        assertFalse(BoardUtils.isIndexOnBoard(null, 0, 0));
    }

    @Test
    public void tesIsIndexInPrincipalDiagonalForValidIndicesShouldReturnTrue() {
        assertTrue(BoardUtils.isIndexInPrincipalDiagonal(3, 3));
    }

    @Test
    public void tesIsIndexInPrincipalDiagonalForInvalidIndicesShouldReturnFalse() {
        assertFalse(BoardUtils.isIndexInPrincipalDiagonal(0, 1));
        assertFalse(BoardUtils.isIndexInPrincipalDiagonal(1, 0));
    }

    @Test
    public void testIsIndexOnSecondaryDiagonalForValidIndicesShouldReturnTrue() {
        assertTrue(BoardUtils.isIndexOnSecondaryDiagonal(0, 3, SPAN_COUNT));
        assertTrue(BoardUtils.isIndexOnSecondaryDiagonal(2, 1, SPAN_COUNT));

        assertTrue(BoardUtils.isIndexOnSecondaryDiagonal(0, 4, 5));
        assertTrue(BoardUtils.isIndexOnSecondaryDiagonal(2, 2, 5));
    }

    @Test
    public void testIsIndexOnSecondaryDiagonalForInvalidIndicesShouldReturnFalse() {
        assertFalse(BoardUtils.isIndexOnSecondaryDiagonal(0, 0, SPAN_COUNT));
        assertFalse(BoardUtils.isIndexOnSecondaryDiagonal(1, 1, SPAN_COUNT));

        assertFalse(BoardUtils.isIndexOnSecondaryDiagonal(2, 1, 5));
        assertFalse(BoardUtils.isIndexOnSecondaryDiagonal(3, 2, 5));
    }

    @Test
    public void testIsIndexOnCornerOfBoardForValidIndicesShouldReturnTrue() {
        CellState[][] board = getBoard();
        assertTrue(BoardUtils.isIndexOnCornerOfBoard(board, 0, 0));
        assertTrue(BoardUtils.isIndexOnCornerOfBoard(board, 3, 0));
        assertTrue(BoardUtils.isIndexOnCornerOfBoard(board, 0, 3));
        assertTrue(BoardUtils.isIndexOnCornerOfBoard(board, 3, 3));
    }

    @Test
    public void testIsIndexOnCornerOfBoardForInvalidIndicesShouldReturnFalse() {
        CellState[][] board = getBoard();
        assertFalse(BoardUtils.isIndexOnCornerOfBoard(board, 1, 1));
        assertFalse(BoardUtils.isIndexOnCornerOfBoard(board, 2, 1));
        assertFalse(BoardUtils.isIndexOnCornerOfBoard(board, 3, 1));
        assertFalse(BoardUtils.isIndexOnCornerOfBoard(board, 2, 3));

    }

}