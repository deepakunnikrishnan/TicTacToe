package com.androidnerds.tictactoe.game.model;

import androidx.annotation.DrawableRes;

/**
 * Object representation for the Player in the game.
 */
public class Player {

    private String player;
    @DrawableRes
    private int playerIcon;
    private CellState cellState;

    public Player(String player, int playerIcon, CellState cellState) {
        this.player = player;
        this.playerIcon = playerIcon;
        this.cellState = cellState;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPlayerIcon() {
        return playerIcon;
    }

    public void setPlayerIcon(@DrawableRes int playerIcon) {
        this.playerIcon = playerIcon;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }
}
