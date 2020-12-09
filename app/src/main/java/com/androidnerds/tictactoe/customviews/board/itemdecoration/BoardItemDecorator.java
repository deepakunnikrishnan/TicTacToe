package com.androidnerds.tictactoe.customviews.board.itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.tictactoe.game.BoardUtils;

/**
 * Decorator for the cells on the Tic-Tac-Toe board.
 */
public class BoardItemDecorator extends RecyclerView.ItemDecoration {

    private final int border;
    private final int cellCount;


    public BoardItemDecorator(int border, int cellCount) {
        this.border = border;
        this.cellCount = cellCount;
    }

    /**
     * Calculates the offset for items based on the position on the board.
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (BoardUtils.calculateRowPosition(parent.getChildAdapterPosition(view), cellCount) == 0) {
            outRect.top = border;
        }
        if (BoardUtils.calculateColumnPosition(parent.getChildAdapterPosition(view), cellCount) == 0) {
            outRect.left = border;
        }
        outRect.right = border;
        if (BoardUtils.calculateRowPosition(parent.getChildAdapterPosition(view), cellCount) != (cellCount - 1)) {
            outRect.bottom = border;
        }
    }

}
