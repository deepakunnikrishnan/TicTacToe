package com.androidnerds.tictactoe.game;

import com.androidnerds.tictactoe.game.model.Cell;
import com.androidnerds.tictactoe.game.model.CellState;
import com.androidnerds.tictactoe.game.model.EvaluationResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.androidnerds.tictactoe.game.GameTestUtils.getBoardForPlayerWonByFirstColumn;
import static com.androidnerds.tictactoe.game.GameTestUtils.getMatchedCellsForPlayerWonBy2x2Box;
import static com.androidnerds.tictactoe.game.GameTestUtils.getMatchedCellsForPlayerWonByCorners;
import static com.androidnerds.tictactoe.game.GameTestUtils.getMatchedCellsForPlayerWonByFirstColumn;
import static com.androidnerds.tictactoe.game.GameTestUtils.getMatchedCellsForPlayerWonByFirstRow;
import static com.androidnerds.tictactoe.game.GameTestUtils.getMatchedCellsForPlayerWonByPrincipalDiagonal;
import static com.androidnerds.tictactoe.game.GameTestUtils.getMatchedCellsForPlayerWonBySecondaryDiagonal;
import static org.junit.Assert.*;

public class GameStatusEvaluatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEvaluateForWinByRowShouldReturnEvaluationResultAsTrue() {
        CellState[][] board = GameTestUtils.getBoardForPlayerWonByFirstRow();
        EvaluationResult evaluationResult = GameStatusEvaluator.evaluate(board, 0, 0);
        assertTrue(evaluationResult.hasWon());
        assertNotNull(evaluationResult.getMatchedCells());

        List<Cell> expected = getMatchedCellsForPlayerWonByFirstRow();
        Collections.sort(expected);
        List<Cell> actual = evaluationResult.getMatchedCells();
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testEvaluateForWinByColumnShouldReturnEvaluationResultAsTrue() {
        CellState[][] board = GameTestUtils.getBoardForPlayerWonByFirstColumn();
        EvaluationResult evaluationResult = GameStatusEvaluator.evaluate(board, 0, 0);
        assertTrue(evaluationResult.hasWon());
        assertNotNull(evaluationResult.getMatchedCells());

        List<Cell> expected = getMatchedCellsForPlayerWonByFirstColumn();
        Collections.sort(expected);
        List<Cell> actual = evaluationResult.getMatchedCells();
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testEvaluateForWinByPrincipalDiagonalShouldReturnEvaluationResultAsTrue() {
        CellState[][] board = GameTestUtils.getBoardForPlayerWonByPrincipalDiagonal();
        EvaluationResult evaluationResult = GameStatusEvaluator.evaluate(board, 0, 0);
        assertTrue(evaluationResult.hasWon());
        assertNotNull(evaluationResult.getMatchedCells());

        List<Cell> expected = getMatchedCellsForPlayerWonByPrincipalDiagonal();
        Collections.sort(expected);
        List<Cell> actual = evaluationResult.getMatchedCells();
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testEvaluateForWinBySecondaryDiagonalShouldReturnEvaluationResultAsTrue() {
        CellState[][] board = GameTestUtils.getBoardForPlayerWonBySecondaryDiagonal();
        EvaluationResult evaluationResult = GameStatusEvaluator.evaluate(board, 0, 3);
        assertTrue(evaluationResult.hasWon());
        assertNotNull(evaluationResult.getMatchedCells());

        List<Cell> expected = getMatchedCellsForPlayerWonBySecondaryDiagonal();
        Collections.sort(expected);
        List<Cell> actual = evaluationResult.getMatchedCells();
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testEvaluateForWinByFourCornersShouldReturnEvaluationResultAsTrue() {
        CellState[][] board = GameTestUtils.getBoardForPlayerWonByCorners();
        EvaluationResult evaluationResult = GameStatusEvaluator.evaluate(board, 0, 3);
        assertTrue(evaluationResult.hasWon());
        assertNotNull(evaluationResult.getMatchedCells());

        List<Cell> expected = getMatchedCellsForPlayerWonByCorners();
        Collections.sort(expected);
        List<Cell> actual = evaluationResult.getMatchedCells();
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testEvaluateForWinBy2x2BoxShouldReturnEvaluationResultAsTrue() {
        CellState[][] board = GameTestUtils.getBoardForPlayerWonBy2x2Box();
        EvaluationResult evaluationResult = GameStatusEvaluator.evaluate(board, 1, 1);
        assertTrue(evaluationResult.hasWon());
        assertNotNull(evaluationResult.getMatchedCells());

        List<Cell> expected = getMatchedCellsForPlayerWonBy2x2Box();
        Collections.sort(expected);
        List<Cell> actual = evaluationResult.getMatchedCells();
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

}