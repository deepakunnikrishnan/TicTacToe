package com.androidnerds.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.androidnerds.tictactoe.customviews.board.TicTacToeBoard;
import com.androidnerds.tictactoe.databinding.ActivityMainBinding;
import com.androidnerds.tictactoe.game.model.Player;

/**
 * Activity class that displays the Tic-Tac-Toe game to the user.
 */
public class MainActivity extends AppCompatActivity implements TicTacToeBoard.OnGameCompletionListener,
        TicTacToeBoard.OnPlayerChangedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }

    private void init() {
        initializeTicTacToeBoard();
        binding.buttonNewGame.setOnClickListener(view -> onNewGameClicked());
    }

    private void initializeTicTacToeBoard() {
        binding.ticTacToeBoard.setGameCompletionListener(this);
        binding.ticTacToeBoard.setPlayerChangedListener(this);
        try {
            binding.ticTacToeBoard.start();
        } catch (Exception e) {
            Log.d(MainActivity.class.getName(), ""+e.getMessage());
        }
    }

    @Override
    public void onPlayerChanged(Player player) {
        binding.labelCurrentPlayer.setPlayerIcon(player.getPlayerIcon());
    }

    private void onNewGameClicked() {
        binding.ticTacToeBoard.resetBoard();
        binding.textViewGameResult.setText("");
    }

    @Override
    public void onGameCompleted(TicTacToeBoard.GameResult gameResult) {
        String message;
        if(gameResult == TicTacToeBoard.GameResult.PLAYER_1_WON) {
            message = getResources().getString(R.string.player_1_won);
        }else if(gameResult == TicTacToeBoard.GameResult.PLAYER_2_WON) {
            message = getResources().getString(R.string.player_2_won);
        }else {
            message = getResources().getString(R.string.game_draw);
        }
        binding.textViewGameResult.setText(message);
    }
}