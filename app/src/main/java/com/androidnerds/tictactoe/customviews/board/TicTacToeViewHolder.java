package com.androidnerds.tictactoe.customviews.board;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.tictactoe.R;
import com.androidnerds.tictactoe.databinding.ItemBoardCellBinding;
import com.androidnerds.tictactoe.game.model.Cell;
import com.androidnerds.tictactoe.game.model.CellState;

/**
 * ViewHolder for the Cell on the board.
 * <p>
 * Handles the populating the Cell UI.
 */
public class TicTacToeViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private int position;
    private ItemBoardCellBinding boardCellBinding;

    public TicTacToeViewHolder(Context context, @NonNull ItemBoardCellBinding binding, TicTacToeBoardAdapter.OnItemClickListener onItemClickListener) {
        super(binding.getRoot());
        this.boardCellBinding = binding;
        this.context = context;
        setItemViewClick(binding, onItemClickListener);
    }

    private void setItemViewClick(@NonNull ItemBoardCellBinding binding, TicTacToeBoardAdapter.OnItemClickListener onItemClickListener) {
        binding.getRoot().setOnClickListener(view -> {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClicked(view, position);
            }
        });
    }

    public void bind(Cell cell, int position) {
        this.position = position;
        setCellState(cell, boardCellBinding.getRoot());
        setParentBackground(cell, boardCellBinding.parent);
        setBoardItemImage(cell, boardCellBinding.imageView);
    }

    private void setCellState(Cell cell, View itemView) {
        itemView.setClickable(cell.getCellState() == CellState.FREE);
        itemView.setEnabled(cell.getCellState() == CellState.FREE);
    }

    private void setParentBackground(Cell cell, SquareCellLayout squareCellLayout) {
        squareCellLayout.setBackgroundColor(cell.isMatchedCell() ?
                ContextCompat.getColor(context, R.color.gold) : ContextCompat.getColor(context, R.color.white));
    }

    private void setBoardItemImage(Cell cell, BoardItemImageView imageView) {
        int drawableId = 0;
        if (cell.getCellState() == CellState.CIRCLE) {
            drawableId = R.drawable.ic_circle;
        } else if (cell.getCellState() == CellState.CROSS) {
            drawableId = R.drawable.ic_close;
        }
        imageView.setImage(drawableId);
    }

}
