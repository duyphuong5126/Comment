package com.phuongduy.comment.view;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phuongduy.comment.R;

public class DefaultListVerticalDivider extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = view.getContext().getResources().getDimensionPixelSize(R.dimen.app_medium_space);
    }
}
