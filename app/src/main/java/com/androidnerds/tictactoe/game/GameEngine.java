package com.androidnerds.tictactoe.game;

import com.androidnerds.tictactoe.R;
import com.androidnerds.tictactoe.game.model.Cell;
import com.androidnerds.tictactoe.game.model.CellState;
import com.androidnerds.tictactoe.game.model.EvaluationResult;
import com.androidnerds.tictactoe.game.model.Player;

import java.util.List;

import static com.androidnerds.tictactoe.game.GameStatusEvaluator.evaluate;

/**
 * Core class that handles the Tic-Tac-Toe game.
 * <p>
 * This class determines the size of the board, state of the game, representation of the board and
 * evaluates the game status after each move.
 * </p>
 * <p>
 * This class provides the changes in the game status to the subscriber via the {@link GameStatusListener} callback passed
 * in the constructor of the class.
 * </p>
 * <p>
 * The game can be started by calling the {@link GameEngine#startGame()} method. This will result in changing the status of the game and
 * the game engine would ask the first player to perform his move via the {@link GameStatusListener}.
 * </p>
 * <p>
 * When the player has selected a cell on the board, it should be passed to the GameEngine via {@link GameEngine#onPlayerSelected(int, int)} method.
 * On receiving the row & column indices of the selected cell, the GameEngine updates the 2d representation of the board.
 * Once updated, this class proceeds with evaluating the status of the game based on the move of the player.
 * {@link GameStatusEvaluator} class is used to evaluate the status and performs the action based on the result.(refer {@link GameStatusEvaluator#evaluate(CellState[][], int, int)} method.
 * </p>
 */
public class GameEngine {


    public enum GameType {
        ONE_PLAYER,
        TWO_PLAYER
    }

    //Enum representation of the game's status.
    public enum GameStatus {
        NOT_INITIALIZED,
        NOT_STARTED,
        PLAYER_1_WON,
        PLAYER_2_WON,
        DRAW,
        NEXT_PLAYER_PLAYS
    }

    //Interface for updating the Game Status to the caller.
    public interface GameStatusListener {
        void onGameStatusChanged(GameStatus gameStatus);

        void onGameOver(List<Cell> matchedCells);
    }

    //Represents the max number of ROW/COLUMN on the board(4x4)
    public static final int SPAN_COUNT = 4;
    //
    private final GameStatusListener gameStatusListener;
    //2d array representation of the Tic-Tac-Toe board.
    private CellState[][] board;
    //Represents the game-type
    private GameType gameType = GameType.TWO_PLAYER;
    //Object for Player 1
    private Player player1;
    //Object for Player 2
    private Player player2;
    //Object holds the current player playing the game.
    private Player currentPlayer;
    //Holds the result after the evaluation by the engine.
    private EvaluationResult evaluationResult;
    //Indicates whether the game has completed or not.
    private GameStatus gameStatus = GameStatus.NOT_INITIALIZED;

    public GameEngine(GameStatusListener gameStatusListener) {
        this.gameStatusListener = gameStatusListener;
    }

    /**
     * Initializes the board and other values related to the Game Engine.
     */
    public void init() {
        initBoard(SPAN_COUNT);
        setPlayers();
        evaluationResult = null;
        gameStatus = GameStatus.NOT_STARTED;
    }

    /**
     * Initializes the 2d array representation of the board.
     *
     * @param boardSize - size of the board.
     */
    private void initBoard(int boardSize) {
        this.board = new CellState[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                this.board[row][column] = CellState.FREE;
            }
        }
    }

    /**
     * Creates and sets the Player objects for the game.
     */
    private void setPlayers() {
        player1 = new Player("Player1", R.drawable.ic_close, CellState.CROSS);
        player2 = new Player("Player2", R.drawable.ic_circle, CellState.CIRCLE);
    }

    /**
     * Method to start the game. When this method is invoked, the game is initiated and the status of the
     * game is updated.
     */
    public void startGame() throws Exception {
        if(gameStatus != GameStatus.NOT_STARTED) {
            throw new Exception("InvalidStateException: Initialize the GameEngine before starting the game.");
        }
        currentPlayer = player1;
        onGameStatusChanged(GameStatus.NEXT_PLAYER_PLAYS);
    }

    /**
     * @return the no: of cells on a row/column on the board.
     */
    public int getBoardSize() {
        return SPAN_COUNT;
    }

    /**
     * @return The Player object of the current player playing.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Method is invoked when the Player has selected a cell on the board.
     * Status of the game is evaluated after the current move by the Player.
     * 1. If the game is over and the current player has won, then gameStatus is updated and passed via the {@link GameStatusListener} as
     * {@link GameStatus#PLAYER_1_WON} or {@link GameStatus#PLAYER_2_WON} depending on the current player.
     * 2. If after evaluation the player has not won, then validates whether free cells are available.
     * a) If free cells are available, then gameStatus is updated and passed via the {@link GameStatusListener} as {@link GameStatus#NEXT_PLAYER_PLAYS}.
     * a) If free cells are not available, then gameStatus is updated and passed via the {@link GameStatusListener} as {@link GameStatus#DRAW}.
     * 3. If the game is over, then the flag {@link #GameEngine#gameCompleted} is updated to true.
     *
     * @param row    - rowIndex of the cell selected on the board.
     * @param column - columnIndex of the cell selected on the board.
     */
    public void onPlayerSelected(int row, int column) throws Exception {
        if(gameStatus != GameStatus.NEXT_PLAYER_PLAYS) {
            throw new Exception("InvalidStateException: Start the game before calling onPlayerSelected");
        }
        board[row][column] = currentPlayer.getCellState();
        GameStatus gameStatus = getGameStatus(board, row, column);
        if (gameStatus == GameStatus.NEXT_PLAYER_PLAYS) {
            nexTurn();
        }
        onGameStatusChanged(gameStatus);
        if (gameStatus == GameStatus.DRAW || gameStatus == GameStatus.PLAYER_1_WON || gameStatus == GameStatus.PLAYER_2_WON) {
            onGameCompleted(gameStatus);
        }
    }

    private void onGameStatusChanged(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        if (null != gameStatusListener) {
            gameStatusListener.onGameStatusChanged(gameStatus);
        }
    }

    /**
     * Evaluates the status of the game after the move.
     *
     * @param board  - 2d array representation of the board.
     * @param row    - rowIndex of the cell currently selected.
     * @param column - columnIndex of the cell currently selected.
     * @return {@link GameStatus#PLAYER_1_WON} - if Player1 wins the game.
     * {@link GameStatus#PLAYER_2_WON} - if Player2 wins the game.
     * {@link GameStatus#NEXT_PLAYER_PLAYS} - if Free cells are available on the board.
     * {@link GameStatus#DRAW} - if there are no free cells on the board.
     */
    private GameStatus getGameStatus(CellState[][] board, int row, int column) {
        evaluationResult = evaluate(board, row, column);
        if (evaluationResult.hasWon()) {
            if (currentPlayer == player1) {
                return GameStatus.PLAYER_1_WON;
            } else {
                return GameStatus.PLAYER_2_WON;
            }
        } else if (isFreeCellsAvailable(board)) {
            return GameStatus.NEXT_PLAYER_PLAYS;
        } else {
            return GameStatus.DRAW;
        }
    }

    private void onGameCompleted(GameStatus gameStatus) {
        if (gameStatus == GameStatus.PLAYER_1_WON || gameStatus == GameStatus.PLAYER_2_WON) {
            if (null != gameStatusListener) {
                gameStatusListener.onGameOver(evaluationResult.getMatchedCells());
            }
        }
    }

    /**
     * Alternates between the two players for the game.
     */
    private void nexTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    /**
     * @return TRUE - if the game has completed.
     * FALSE - if the game is still in progress on has not started.
     */
    public boolean isGameOver() {
        return gameStatus == GameStatus.PLAYER_1_WON ||
                gameStatus == GameStatus.PLAYER_2_WON ||
                gameStatus == GameStatus.DRAW;
    }

    /**
     * Method evaluates whether the board contains any free cells.
     *
     * @return TRUE if there are free cells on the board.
     */
    private boolean isFreeCellsAvailable(CellState[][] board) {
        for (CellState[] row : board) {
            for (CellState cellState : row) {
                if (cellState == CellState.FREE) {
                    return true;
                }
            }
        }
        return false;
    }
}
