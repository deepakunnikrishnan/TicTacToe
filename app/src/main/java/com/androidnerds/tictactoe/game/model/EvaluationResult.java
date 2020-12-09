package com.androidnerds.tictactoe.game.model;

import java.util.List;

/**
 * This class holds the result of the evaluation.
 */
public class EvaluationResult {

    private boolean win;
    private List<Cell> matchedCells;

    public EvaluationResult() {
    }

    public EvaluationResult(boolean win, List<Cell> matchedCells) {
        this.win = win;
        this.matchedCells = matchedCells;
    }

    public boolean hasWon() {
        return win;
    }

    public List<Cell> getMatchedCells() {
        return matchedCells;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setMatchedCells(List<Cell> matchedCells) {
        this.matchedCells = matchedCells;
    }
}
