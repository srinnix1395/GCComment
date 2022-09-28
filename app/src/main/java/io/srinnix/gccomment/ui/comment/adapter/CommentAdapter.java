package io.srinnix.gccomment.ui.comment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.ListAdapter;
import io.srinnix.gccomment.R;
import io.srinnix.gccomment.data.model.Comment;
import io.srinnix.gccomment.widget.TextCommentView;

public class CommentAdapter extends ListAdapter<Comment, CommentViewHolder> {

    public static final int PAYLOAD_JSON = 1;

    private final LayoutInflater layoutInflater;
    private final OnClickCommentListener onClickCommentListener;
    private final TextCommentView.OnClickContentListener onClickContentListener;

    public CommentAdapter(Context context,
                          OnClickCommentListener onClickCommentListener,
                          TextCommentView.OnClickContentListener onClickContentListener) {
        super(new CommentDiffItemCallback());

        this.layoutInflater = LayoutInflater.from(context);
        this.onClickCommentListener = onClickCommentListener;
        this.onClickContentListener = onClickContentListener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view, onClickCommentListener, onClickContentListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }

        for (Object payload : payloads) {
            if (payload instanceof ArraySet<?>) {
                for (int key : ((ArraySet<Integer>) payload)) {
                    Comment comment = getItem(position);
                    if (key == PAYLOAD_JSON) {
                        holder.bindJson(comment.getJson(), comment.isExpand());
                    }
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }

    public interface OnClickCommentListener {
        void onClick(int position);
    }
}
