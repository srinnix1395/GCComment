package io.srinnix.gccomment.ui.comment.adapter;

import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.srinnix.gccomment.data.model.Comment;
import io.srinnix.gccomment.data.model.Link;
import io.srinnix.gccomment.databinding.ItemCommentBinding;
import io.srinnix.gccomment.widget.TextCommentView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    private final ItemCommentBinding viewBinding;

    public CommentViewHolder(@NonNull View itemView,
                             CommentAdapter.OnClickCommentListener onClickCommentListener,
                             TextCommentView.OnClickContentListener onClickContentListener) {
        super(itemView);
        this.viewBinding = ItemCommentBinding.bind(itemView);

        viewBinding.getRoot().setOnClickListener(view -> onClickCommentListener.onClick(getAdapterPosition()));
        viewBinding.tvContent.setOnClickContentListener(onClickContentListener);
    }

    public void bindData(Comment comment) {
        bindContent(comment.getContent(), comment.getTags(), comment.getLinks());
        bindJson(comment.getJson(), comment.isExpand());
    }

    public void bindContent(String content, List<String> tags, List<Link> links) {
        viewBinding.tvContent.setContent(content, tags, links);
    }

    public void bindJson(String json, boolean isExpand) {
        if (json == null || json.isEmpty()) {
            viewBinding.tvJson.setVisibility(View.GONE);
            return;
        }

        viewBinding.tvJson.setText(json);
        viewBinding.tvJson.setVisibility(isExpand ? View.VISIBLE : View.GONE);
    }
}
