package com.phuongduy.comment.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.phuongduy.comment.databinding.ItemCommentBinding;
import com.phuongduy.comment.uimodel.CommentItem;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    private final ItemCommentBinding viewBinding;

    public CommentViewHolder(ViewGroup parent) {
        super(ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
        viewBinding = ItemCommentBinding.bind(itemView);
    }

    public void bindTo(CommentItem commentItem) {
        viewBinding.tvComment.setText(commentItem.comment);
        viewBinding.tvDateTime.setText(commentItem.timeStamp);
        viewBinding.tagGroup.setTags(commentItem.mentionedPeople);
    }
}
