package com.androidnerds.tictactoe.game;

import com.androidnerds.tictactoe.game.model.Cell;
import com.androidnerds.tictactoe.game.model.CellState;

import java.util.ArrayList;
import java.util.List;

public class GameTestUtils {

    public static final int SPAN_COUNT = 4;

    public static CellState[][] getBoard() {
        CellState[][] board = new CellState[SPAN_COUNT][SPAN_COUNT];
        for (int row = 0; row < SPAN_COUNT; row++) {
            for (int colum = 0; colum < SPAN_COUNT; colum++) {
                board[row][colum] = CellState.FREE;
            }
        }
        return board;
    }

    public static CellState[][] getBoardForPlayerWonByFirstRow() {
        CellState[][] board = getBoard();
        for(int  column = 0; column < board.length;column++) {
            board[0][column] = CellState.CROSS;
        }
        //Simulate Player 2
        for(int  column = 0; column < board.length-1;column++) {
            board[1][column] = CellState.CIRCLE;
        }
        return board;
    }

    public static List<Cell> getMatchedCellsForPlayerWonByFirstRow() {
        CellState[][] board = getBoard();
        List<Cell> cellList = new ArrayList<>();
        for(int  column = 0; column < board.length;column++) {
            cellList.add(new Cell(0, column, true, CellState.CROSS));
        }
        return cellList;
    }

    public static CellState[][] getBoardForPlayerWonByFirstColumn() {
        CellState[][] board = getBoard();
        for(int  row = 0; row < board[0].length;row++) {
            board[row][0] = CellState.CROSS;
        }
        //Simulate Player 2
        for(int  row = 0; row < board.length-1;row++) {
            board[row][1] = CellState.CIRCLE;
        }
        return board;
    }

    public static List<Cell> getMatchedCellsForPlayerWonByFirstColumn() {
        CellState[][] board = getBoard();
        List<Cell> cellList = new ArrayList<>();
        for(int  row = 0; row < board[0].length;row++) {
            cellList.add(new Cell(row, 0, true, CellState.CROSS));
        }
        return cellList;
    }

    public static CellState[][] getBoardForPlayerWonByPrincipalDiagonal() {
        CellState[][] board = getBoard();
        //Simulate Player 1
        for(int row = 0, column = 0; row < board.length && column < board.length;row++,column++) {
            board[row][column] = CellState.CROSS;
        }
        //Simulate Player 2
        for(int column = 1; column < board.length; column++) {
            board[0][column] = CellState.CIRCLE;
        }
        return board;
    }

    public static List<Cell> getMatchedCellsForPlayerWonByPrincipalDiagonal() {
        CellState[][] board = getBoard();
        List<Cell> cellList = new ArrayList<>();
        for(int row = 0, column = 0; row < board.length && column < board.length;row++,column++) {
            cellList.add(new Cell(row, column, true, CellState.CROSS));
        }
        return cellList;
    }

    public static CellState[][] getBoardForPlayerWonBySecondaryDiagonal() {
        CellState[][] board = getBoard();
        //Simulate Player 1
        for(int row = 0, column = board.length - 1; row < board.length && column >= 0;row++,column--) {
            board[row][column] = CellState.CROSS;
        }
        //Simulate Player 2
        board[0][1] = CellState.CIRCLE;
        board[0][2] = CellState.CIRCLE;
        board[1][1] = CellState.CIRCLE;

        return board;
    }

    public static List<Cell> getMatchedCellsForPlayerWonBySecondaryDiagonal() {
        CellState[][] board = getBoard();
        List<Cell> cellList = new ArrayList<>();
        for(int row = 0, column = board.length - 1; row < board.length && column >= 0;row++,column--) {
            cellList.add(new Cell(row, column, true, CellState.CROSS));
        }
        return cellList;
    }

    public static CellState[][] getBoardForPlayerWonByCorners() {
        CellState[][] board = getBoard();
        //Simulate Player 1
        board[0][0] = CellState.CROSS;
        board[0][board.length-1] = CellState.CROSS;
        board[board.length-1][0] = CellState.CROSS;
        board[board.length-1][board.length-1] = CellState.CROSS;

        //Simulate Player 2
        board[0][1] = CellState.CIRCLE;
        board[0][2] = CellState.CIRCLE;
        board[1][1] = CellState.CIRCLE;

        return board;
    }

    public static List<Cell> getMatchedCellsForPlayerWonByCorners() {
        CellState[][] board = getBoard();
        List<Cell> cellList = new ArrayList<>();
        cellList.add(new Cell(0, 0, true, CellState.CROSS));
        cellList.add(new Cell(0, board.length-1, true, CellState.CROSS));
        cellList.add(new Cell(board.length-1,0,  true, CellState.CROSS));
        cellList.add(new Cell(board.length-1,board.length-1,  true, CellState.CROSS));
        return cellList;
    }

    public static CellState[][] getBoardForPlayerWonBy2x2Box() {
        CellState[][] board = getBoard();
        //Simulate Player 1
        board[0][0] = CellState.CROSS;
        board[0][1] = CellState.CROSS;
        board[1][0] = CellState.CROSS;
        board[1][1] = CellState.CROSS;

        //Simulate Player 2
        board[0][2] = CellState.CIRCLE;
        board[1][2] = CellState.CIRCLE;
        board[2][2] = CellState.CIRCLE;

        return board;
    }

    public static List<Cell> getMatchedCellsForPlayerWonBy2x2Box() {
        List<Cell> cellList = new ArrayList<>();
        cellList.add(new Cell(0, 0, true, CellState.CROSS));
        cellList.add(new Cell(0, 1, true, CellState.CROSS));
        cellList.add(new Cell(1,0,  true, CellState.CROSS));
        cellList.add(new Cell(1,1,  true, CellState.CROSS));
        return cellList;
    }





}
