package com.androidnerds.tictactoe.game;

import androidx.annotation.NonNull;

import com.androidnerds.tictactoe.game.model.CellState;

/**
 * Utility class providing different methods related to the Board for the game.
 */
public final class BoardUtils {

    private BoardUtils() {
        //empty constructor
    }

    /**
     * For calculating the rowPosition based on the adapterPosition of the data.
     *
     * @param adapterPosition - Position in the visual representation
     * @param boardSize       - size of the board.
     * @return the rowPosition if the boardSize is not zero.
     * If boardSize is 0, this method would return the position as -1.
     */
    public static int calculateRowPosition(int adapterPosition, int boardSize) {
        return boardSize != 0 ? adapterPosition / boardSize : -1;
    }

    /**
     * For calculating the columnPosition based on the adapterPosition of the data.
     *
     * @param adapterPosition - Position of the cell in the visual representation
     * @param boardSize       - size of the board.
     */
    public static int calculateColumnPosition(int adapterPosition, int boardSize) {
        return adapterPosition % boardSize;
    }

    /**
     * Calculates the adapter position in the visual representation based on the row,column indices.
     */
    public static int calculateAdapterPosition(int rowIndex, int columnIndex, int boardSize) {
        return rowIndex + columnIndex * boardSize;
    }

    public static int getTotalCellsOnBoard(int boardSize) {
        return (int) Math.pow(boardSize, 2);
    }

    /**
     * Method evaluates whether the row & column index represents a cell on the board.
     *
     * @param board  - 2d array representation of the tic-tac-toe board.
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     * @return TRUE - if the cell is on the board.
     * FALSE - if the indices does not represent a cell on the board.
     */
    protected static boolean isIndexOnBoard(@NonNull CellState[][] board, int row, int column) {
        return null != board && row >= 0 && row < board.length &&
                column >= 0 && column < board.length;
    }

    /**
     * Method returns whether the index is on the principal diagonal of the board.
     *
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     * @return TRUE - if the selected cell comes on the principal diagonal of the board.
     * FALSE - if the selected cell comes on the principal diagonal of the board.
     */
    protected static boolean isIndexInPrincipalDiagonal(int row, int column) {
        return row == column;
    }

    /**
     * Method returns whether the (row,column) index comes in the secondary diagonal of the board.
     *
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     * @param size   - represents the board size.
     * @return TRUE - if the cell comes in the secondary diagonal.
     * FALSE - if the cell is not present in the secondary diagonal.
     */
    protected static boolean isIndexOnSecondaryDiagonal(int row, int column, int size) {
        return row + column == size - 1;
    }

    /**
     * Method returns whether the (row,column) index is a corner cell in the board.
     *
     * @param board  - 2d array representation of the tic-tac-toe board.
     * @param row    - rowIndex of the selected cell on the board.
     * @param column - columnIndex of the selected cell on the board.
     * @return TRUE - if the cell is corner cell.
     * FALSE - if the cell is not a corner cell.
     */
    protected static boolean isIndexOnCornerOfBoard(@NonNull CellState[][] board, int row, int column) {
        return null != board &&
                ((row == 0 && column == 0) ||
                        (row == 0 && column == board.length - 1) ||
                        (row == board.length - 1 && column == 0) ||
                        (row == board.length - 1 && column == board.length - 1));
    }
}
