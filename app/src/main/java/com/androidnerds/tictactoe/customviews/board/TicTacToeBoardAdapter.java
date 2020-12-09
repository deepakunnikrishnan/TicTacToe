package com.androidnerds.tictactoe.customviews.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.tictactoe.R;
import com.androidnerds.tictactoe.databinding.ItemBoardCellBinding;
import com.androidnerds.tictactoe.game.BoardUtils;
import com.androidnerds.tictactoe.game.model.Cell;

/**
 * Adapter class for the RecyclerView used as the Tic-Tac-Toe Board.
 */
public class TicTacToeBoardAdapter extends RecyclerView.Adapter<TicTacToeViewHolder> {

    private Context context;
    private Cell[][] board;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(View view, int position);
    }

    public TicTacToeBoardAdapter(Context context, Cell[][] board, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.board = board;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TicTacToeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBoardCellBinding boardCellBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_board_cell, parent, false);
        return new TicTacToeViewHolder(parent.getContext(), boardCellBinding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TicTacToeViewHolder holder, int position) {
        Cell cell = board[BoardUtils.calculateRowPosition(position, board.length)][BoardUtils.calculateColumnPosition(position,board.length)];
        holder.bind(cell, position);
    }

    @Override
    public int getItemCount() {
        int boardLength = null != board ? board.length:0;
        return BoardUtils.getTotalCellsOnBoard(boardLength);
    }

}
