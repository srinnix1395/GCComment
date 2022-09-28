package io.srinnix.gccomment.widget.recyclerview.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.srinnix.gccomment.R;

public class CommentItemDecoration extends RecyclerView.ItemDecoration {

    private int verticalMargin = 0;
    private int horizontalMargin = 0;

    public CommentItemDecoration(Context context) {
        if (context == null) {
            return;
        }

        verticalMargin = (int) context.getResources().getDimension(R.dimen.dimen8dp);
        horizontalMargin = (int) context.getResources().getDimension(R.dimen.dimen16dp);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top = verticalMargin;
        outRect.left = horizontalMargin;
        outRect.right = horizontalMargin;
    }
}
