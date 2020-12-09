package com.androidnerds.tictactoe.customviews.board;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.androidnerds.tictactoe.R;

public class CurrentPlayerLabel extends ConstraintLayout {

    private AppCompatImageView imageViewPlayerIcon;

    public CurrentPlayerLabel(@NonNull Context context) {
        this(context, null);
    }

    public CurrentPlayerLabel(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_current_player_label, this, true);
        initViews();

    }

    private void initViews() {
        imageViewPlayerIcon = (AppCompatImageView) getChildAt(1);
    }

    public void setPlayerIcon(@DrawableRes int playerIconId) {
        imageViewPlayerIcon.setImageDrawable(ContextCompat.getDrawable(getContext(),playerIconId));
    }
}
