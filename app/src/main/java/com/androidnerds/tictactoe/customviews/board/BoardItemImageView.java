package com.androidnerds.tictactoe.customviews.board;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

/**
 * ImageView that sets the player icon image.
 */
public class BoardItemImageView extends AppCompatImageView {

    public BoardItemImageView(@NonNull Context context) {
        this(context, null);
    }

    public BoardItemImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setImage(0);
    }

    public void setImage(@DrawableRes int drawableId) {
        if(drawableId != 0) {
            setImageDrawable(ContextCompat.getDrawable(getContext(), drawableId));
        } else {
            setImageDrawable(null);
        }
    }
}
