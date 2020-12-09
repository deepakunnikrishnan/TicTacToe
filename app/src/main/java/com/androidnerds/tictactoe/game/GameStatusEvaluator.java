package com.androidnerds.tictactoe.game;

import androidx.annotation.NonNull;

import com.androidnerds.tictactoe.game.model.Cell;
import com.androidnerds.tictactoe.game.model.CellState;
import com.androidnerds.tictactoe.game.model.EvaluationResult;

import java.util.ArrayList;
import java.util.List;

import static com.androidnerds.tictactoe.game.BoardUtils.isIndexInPrincipalDiagonal;
import static com.androidnerds.tictactoe.game.BoardUtils.isIndexOnBoard;
import static com.androidnerds.tictactoe.game.BoardUtils.isIndexOnCornerOfBoard;
import static com.androidnerds.tictactoe.game.BoardUtils.isIndexOnSecondaryDiagonal;

/**
 * Utility class that performs the rules validation for the Tic-Tac-Toe game.
 */
public final class GameStatusEvaluator {

    private GameStatusEvaluator() {
        //empty constructor
    }

    /**
     * Method evaluates whether the user has won the game after the current move.
     * For evaluation, it applies the below set of rules.
     * Rule#1 - User has selected all the cells in the row as the current selected cell.
     * Rule#2 - User has selected all the cells in the column as the current selected cell.
     * Rule#3 - User has selected all the cells in the principal diagonal.
     * Rule#4 - User has selected all the cells in the secondary diagonal.
     * Rule#5 - User has selected all the cells on the four corners of the board.
     * Rule#6 - User has selected a 2x2 box on the board.
     *
     * @param board  - 2d array representation of the tic-tac-toe board
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     * @return {@link EvaluationResult} object which contains:
     * 1. Status as whether the user has won or not
     * 2. If won, then contains list of cells that were matched.
     */
    @NonNull
    protected static EvaluationResult evaluate(@NonNull CellState[][] board, int row, int column) {
        EvaluationResult evaluationResult = hasPlayerWonTheRow(board, row, column);
        if (evaluationResult.hasWon()) {
            return evaluationResult;
        }

        evaluationResult = hasPlayerWonTheColumn(board, row, column);
        if (evaluationResult.hasWon()) {
            return evaluationResult;
        }

        evaluationResult = hasPlayerWonThePrincipalDiagonal(board, row, column);
        if (evaluationResult.hasWon()) {
            return evaluationResult;
        }

        evaluationResult = hasPlayerWonTheSecondaryDiagonal(board, row, column);
        if (evaluationResult.hasWon()) {
            return evaluationResult;
        }

        evaluationResult = hasPlayerWonAllCorners(board, row, column);
        if (evaluationResult.hasWon()) {
            return evaluationResult;
        }

        return hasPlayerWonASquare(board, row, column);
    }

    /**
     * Method returns whether the player has selected all the cells in the current row.
     *
     * @param board  - 2d array representation of the tic-tac-toe board
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     */
    @NonNull
    private static EvaluationResult hasPlayerWonTheRow(@NonNull CellState[][] board, int row, int column) {
        CellState currentPlayerState = board[row][column];
        EvaluationResult evaluationResult = new EvaluationResult();
        List<Cell> matchedCells = new ArrayList<>();
        int columnIndex = 0;
        while (columnIndex < board[row].length && board[row][columnIndex] == currentPlayerState) {
            matchedCells.add(new Cell(row, columnIndex, true, currentPlayerState));
            columnIndex++;
        }
        if (columnIndex == board[row].length) {
            evaluationResult.setWin(true);
            evaluationResult.setMatchedCells(matchedCells);
        } else {
            evaluationResult.setWin(false);
        }
        return evaluationResult;
    }

    /**
     * Method returns whether the player has selected all the cells in the current column.
     *
     * @param board  - 2d array representation of the tic-tac-toe board
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     */
    @NonNull
    private static EvaluationResult hasPlayerWonTheColumn(@NonNull CellState[][] board, int row, int column) {
        CellState currentPlayerState = board[row][column];
        EvaluationResult evaluationResult = new EvaluationResult();
        List<Cell> matchedCells = new ArrayList<>();
        int rowIndex = 0;
        while (rowIndex < board.length && board[rowIndex][column] == currentPlayerState) {
            matchedCells.add(new Cell(rowIndex, column, true, currentPlayerState));
            rowIndex++;
        }
        if (rowIndex == board.length) {
            evaluationResult.setWin(true);
            evaluationResult.setMatchedCells(matchedCells);
        } else {
            evaluationResult.setWin(false);
        }
        return evaluationResult;
    }

    /**
     * Method returns whether the player has selected all the four corners of the board.
     *
     * @param board  - 2d array representation of the tic-tac-toe board
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     */
    @NonNull
    private static EvaluationResult hasPlayerWonAllCorners(@NonNull CellState[][] board, int row, int column) {
        EvaluationResult evaluationResult = new EvaluationResult();
        if (isIndexOnCornerOfBoard(board, row, column)) {
            CellState playerState = board[row][column];
            if (board[0][0] == playerState &&
                    board[0][board.length - 1] == playerState &&
                    board[board.length - 1][0] == playerState &&
                    board[board.length - 1][board.length - 1] == playerState) {
                evaluationResult.setWin(true);
                List<Cell> matchedCells = new ArrayList<>();
                matchedCells.add(new Cell(0, 0, true, playerState));
                matchedCells.add(new Cell(0, board.length - 1, true, playerState));
                matchedCells.add(new Cell(board.length - 1, 0, true, playerState));
                matchedCells.add(new Cell(board.length - 1, board.length - 1, true, playerState));
                evaluationResult.setMatchedCells(matchedCells);
            }
        } else {
            evaluationResult.setWin(false);
        }
        return evaluationResult;
    }

    /**
     * Method returns whether the player has selected all the cells in the secondary diagonal.
     *
     * @param board  - 2d array representation of the tic-tac-toe board
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     */
    @NonNull
    private static EvaluationResult hasPlayerWonTheSecondaryDiagonal(@NonNull CellState[][] board, int row, int column) {
        EvaluationResult evaluationResult = new EvaluationResult();
        if (isIndexOnBoard(board, row, column) && isIndexOnSecondaryDiagonal(row, column, board.length)) {
            int rowIndex = 0;
            int columnIndex = board.length - 1;
            List<Cell> matchedCells = new ArrayList<>();
            while (rowIndex < board.length &&
                    columnIndex >= 0 &&
                    board[rowIndex][columnIndex] == board[row][column]) {
                matchedCells.add(new Cell(rowIndex, columnIndex, true, board[row][column]));
                rowIndex++;
                columnIndex--;
            }
            if (rowIndex == board.length && columnIndex == -1) {
                evaluationResult.setWin(true);
                evaluationResult.setMatchedCells(matchedCells);
            }
        }
        return evaluationResult;
    }

    /**
     * Method returns whether the player has selected all the cells in the principal diagonal.
     *
     * @param board  - 2d array representation of the tic-tac-toe board
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     */
    @NonNull
    private static EvaluationResult hasPlayerWonThePrincipalDiagonal(@NonNull CellState[][] board, int row, int column) {
        EvaluationResult evaluationResult = new EvaluationResult();
        if (isIndexOnBoard(board, row, column) && isIndexInPrincipalDiagonal(row, column)) {
            int rowIndex = 0;
            int columnIndex = 0;
            List<Cell> matchedCells = new ArrayList<>();
            while (rowIndex < board.length &&
                    columnIndex < board.length &&
                    board[rowIndex][columnIndex] == board[row][column]) {
                matchedCells.add(new Cell(rowIndex, columnIndex, true, board[row][column]));
                rowIndex++;
                columnIndex++;
            }
            if (rowIndex == board.length && columnIndex == board.length) {
                evaluationResult.setWin(true);
                evaluationResult.setMatchedCells(matchedCells);
            }
        }
        return evaluationResult;
    }

    @NonNull
    private static EvaluationResult validateSquare(@NonNull CellState[][] board, CellState playerState, int rowStart, int columnStart, int rowEnd, int columnEnd) {
        int columnIndex;
        int rowIndex;
        boolean playerWon = true;
        EvaluationResult evaluationResult = new EvaluationResult();
        List<Cell> matchedCells = new ArrayList<>();
        for (columnIndex = columnStart; columnIndex <= columnEnd; columnIndex++) {
            for (rowIndex = rowStart; rowIndex <= rowEnd; rowIndex++) {
                if (!isIndexOnBoard(board, rowIndex, columnIndex) || board[rowIndex][columnIndex] != playerState) {
                    playerWon = false;
                    break;
                } else {
                    matchedCells.add(new Cell(rowIndex, columnIndex, true, playerState));
                }
            }
            if (playerWon && rowIndex == rowEnd + 1 && columnIndex == columnEnd) {
                evaluationResult.setWin(true);
                evaluationResult.setMatchedCells(matchedCells);
            }
        }
        return evaluationResult;
    }

    /**
     * Method evaluates whether the player has formed a 2x2 box after the current move.
     * The selected cell can possibly form 4 2x2 box with the adjacent elements on the board.
     * 1. Top Left box.
     * 2. Top Right box.
     * 3. Bottom Left box.
     * 4. Bottom Right box.
     * If the user forms a 2x2 box on, then the player has won the game.
     *
     * @param board  - 2d array representation of the tic-tac-toe board.
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     */
    @NonNull
    private static EvaluationResult hasPlayerWonASquare(@NonNull CellState[][] board, int row, int column) {
        //Top Left
        EvaluationResult evaluationResult = validateSquare(board, board[row][column], row - 1, column - 1, row, column);
        if (evaluationResult.hasWon()) {
            return evaluationResult;
        }
        //TopRight
        evaluationResult = validateSquare(board, board[row][column], row - 1, column, row, column + 1);
        if (evaluationResult.hasWon()) {
            return evaluationResult;
        }
        //BottomLeft
        evaluationResult = validateSquare(board, board[row][column], row, column - 1, row + 1, column);
        if (evaluationResult.hasWon()) {
            return evaluationResult;
        }
        //BottomRight
        return validateSquare(board, board[row][column], row, column, row + 1, column + 1);
    }
}
