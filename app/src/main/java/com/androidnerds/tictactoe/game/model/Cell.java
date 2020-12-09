package com.androidnerds.tictactoe.game.model;

import java.util.Objects;

/**
 * Class holds the data required for the visual representation of a cell on the board.
 */
public class Cell implements Comparable<Cell> {

    private final int rowPosition;
    private final int columnPosition;
    private boolean matchedCell;
    private CellState cellState = CellState.FREE;

    public Cell(int rowPosition, int columnPosition) {
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
    }

    public Cell(int rowPosition, int columnPosition, CellState cellState) {
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
        this.cellState = cellState;
    }

    public Cell(int rowPosition, int columnPosition, boolean matchedCell, CellState cellState) {
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
        this.matchedCell = matchedCell;
        this.cellState = cellState;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public boolean isMatchedCell() {
        return matchedCell;
    }

    public void setMatchedCell(boolean matchedCell) {
        this.matchedCell = matchedCell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return getRowPosition() == cell.getRowPosition() &&
                getColumnPosition() == cell.getColumnPosition() &&
                isMatchedCell() == cell.isMatchedCell() &&
                getCellState() == cell.getCellState();
    }

    @Override
    public String toString() {
        return "Cell{" +
                "rowPosition=" + rowPosition +
                ", columnPosition=" + columnPosition +
                ", matchedCell=" + matchedCell +
                ", cellState=" + cellState +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRowPosition(), getColumnPosition(), isMatchedCell(), getCellState());
    }

    @Override
    public int compareTo(Cell cell) {
        int compare = Integer.compare(this.getRowPosition(), cell.getRowPosition());
        if(compare == 0) {
            return Integer.compare(this.getColumnPosition(), cell.getColumnPosition());
        }
        return compare;
    }
}
