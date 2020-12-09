package com.androidnerds.tictactoe.customviews.board;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.tictactoe.R;
import com.androidnerds.tictactoe.customviews.board.itemdecoration.BoardItemDecorator;
import com.androidnerds.tictactoe.game.BoardUtils;
import com.androidnerds.tictactoe.game.GameEngine;
import com.androidnerds.tictactoe.game.model.Cell;
import com.androidnerds.tictactoe.game.model.Player;

import java.util.List;

/**
 * This class forms the visual representation of the Tic-Tac-Toe board for the game.
 * This class delegates the game execution to the {@link GameEngine} and is only responsible
 * for the visual representation of the game.
 */
public class TicTacToeBoard extends RecyclerView implements TicTacToeBoardAdapter.OnItemClickListener, GameEngine.GameStatusListener {

    private Cell[][] board;
    private TicTacToeBoardAdapter adapter;
    private GameEngine gameEngine;

    private OnPlayerChangedListener playerChangedListener;
    private OnGameCompletionListener gameCompletionListener;

    public enum GameResult {
        PLAYER_1_WON,
        PLAYER_2_WON,
        DRAW
    }

    //Interface for updating the change in player for the next turn.
    public interface OnPlayerChangedListener {
        void onPlayerChanged(Player player);
    }

    //Interface for updating the completion of the game.
    public interface OnGameCompletionListener {
        void onGameCompleted(GameResult gameResult);
    }

    public TicTacToeBoard(@NonNull Context context) {
        this(context, null);
    }

    public TicTacToeBoard(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Setter for the {@link OnPlayerChangedListener} listener.
     */
    public void setPlayerChangedListener(OnPlayerChangedListener playerChangedListener) {
        this.playerChangedListener = playerChangedListener;
    }

    /**
     * Setter for the {@link OnGameCompletionListener} listener.
     */
    public void setGameCompletionListener(OnGameCompletionListener gameCompletionListener) {
        this.gameCompletionListener = gameCompletionListener;
    }

    /**
     * Initializing the TicTacToeBoard
     */
    private void init(Context context) {
        initGameEngine();
        initBoard(context, gameEngine.getBoardSize());
    }

    private void initGameEngine() {
        gameEngine = new GameEngine(this);
        gameEngine.init();
    }

    public void start() throws Exception {
        gameEngine.startGame();
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int width = View.MeasureSpec.getSize(widthSpec);
        int divider = getContext().getResources().getDimensionPixelSize(R.dimen.border_width);
        int height = width + ((divider / 2) * gameEngine.getBoardSize());
        setMeasuredDimension(width, height);
    }

    private void initBoard(Context context, int boardSize) {
        this.board = new Cell[boardSize][boardSize];
        initBoardValues(boardSize);
        initBoardView(context, boardSize);
    }

    private void initBoardValues(int boardSize) {
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                Cell cell = new Cell(row, column);
                this.board[row][column] = cell;
            }
        }
    }

    private void initBoardView(Context context, int boardSize) {
        setHasFixedSize(true);
        setBackgroundColor(ContextCompat.getColor(context, R.color.grey));
        int spacing = context.getResources().getDimensionPixelSize(R.dimen.border_width);
        addItemDecoration(new BoardItemDecorator(spacing, boardSize));
        setLayoutManager(new GridLayoutManager(context, boardSize));
        setBoardAdapter(context, this.board);
    }

    private void setBoardAdapter(Context context, Cell[][] board) {
        this.adapter = new TicTacToeBoardAdapter(context, board, this);
        setAdapter(this.adapter);
    }

    public void resetBoard() {
        gameEngine.init();
        initBoardValues(gameEngine.getBoardSize());
        this.adapter.notifyDataSetChanged();
        try {
            gameEngine.startGame();
        } catch (Exception e) {
            Log.d(TicTacToeBoard.class.getName(), ""+e.getMessage());
        }
    }

    /**
     * Action when the user taps on a cell on the board.
     * When the user taps on the cell, state of the cell is updated to reflect the selection.
     * The row-column position of the selected cell is passed down to the {@link GameEngine} to decide the next step.
     *
     * @param position Position in the adapter used to populate the board.
     */
    @Override
    public void onItemClicked(View view, int position) {
        if (!gameEngine.isGameOver()) {
            Cell cell = this.board[BoardUtils.calculateRowPosition(position, board.length)][BoardUtils.calculateColumnPosition(position, board.length)];
            cell.setCellState(gameEngine.getCurrentPlayer().getCellState());
            this.adapter.notifyItemChanged(position);
            try {
                this.gameEngine.onPlayerSelected(BoardUtils.calculateRowPosition(position, board.length), BoardUtils.calculateColumnPosition(position, board.length));
            } catch (Exception e) {
                Log.d(TicTacToeBoard.class.getName(), "exception: "+e.getMessage());
            }
        }
    }

    /**
     * Callback from GameEngine, when the status of the game has changed.
     */
    @Override
    public void onGameStatusChanged(GameEngine.GameStatus gameStatus) {
        switch (gameStatus) {
            case DRAW:
                if (null != gameCompletionListener) {
                    gameCompletionListener.onGameCompleted(GameResult.DRAW);
                }
                break;
            case PLAYER_1_WON:
                if (null != gameCompletionListener) {
                    gameCompletionListener.onGameCompleted(GameResult.PLAYER_1_WON);
                }
                break;
            case PLAYER_2_WON:
                if (null != gameCompletionListener) {
                    gameCompletionListener.onGameCompleted(GameResult.PLAYER_2_WON);
                }
                break;
            case NEXT_PLAYER_PLAYS:
                if (null != playerChangedListener) {
                    playerChangedListener.onPlayerChanged(this.gameEngine.getCurrentPlayer());
                }
            default:
                break;
        }
    }

    /**
     * Callback when the game has completed with a win for the player.
     * On receiving the list of cells matched, UI is updated to highlight the same.
     * The data source of the adapter is updated and the adapter is notified to reflect the
     * change in data.
     *
     * @param matchedCells represents the list of cells that matched to win the game.
     */
    @Override
    public void onGameOver(List<Cell> matchedCells) {
        if (null != matchedCells && !matchedCells.isEmpty()) {
            for (Cell cell : matchedCells) {
                Cell matchedCell = this.board[cell.getRowPosition()][cell.getColumnPosition()];
                matchedCell.setMatchedCell(true);
            }
            this.adapter.notifyDataSetChanged();
        }
    }
}
