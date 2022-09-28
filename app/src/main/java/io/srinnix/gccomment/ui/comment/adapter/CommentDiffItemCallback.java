package io.srinnix.gccomment.ui.comment.adapter;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.DiffUtil;
import io.srinnix.gccomment.data.model.Comment;

public class CommentDiffItemCallback extends DiffUtil.ItemCallback<Comment> {

    @Override
    public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
        return oldItem.equals(newItem);
    }

    @Nullable
    @Override
    public Object getChangePayload(@NonNull Comment oldItem, @NonNull Comment newItem) {
        ArraySet<Integer> payloads = new ArraySet<>();

        if (!Objects.equals(oldItem.getJson(), newItem.getJson())
                || oldItem.isExpand() != newItem.isExpand()) {
            payloads.add(CommentAdapter.PAYLOAD_JSON);
        }

        if (payloads.isEmpty()) {
            return null;
        } else {
            return payloads;
        }
    }
}
