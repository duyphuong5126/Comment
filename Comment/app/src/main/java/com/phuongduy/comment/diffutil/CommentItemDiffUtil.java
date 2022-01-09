package com.phuongduy.comment.diffutil;

import androidx.recyclerview.widget.DiffUtil;

import com.phuongduy.comment.uimodel.CommentItem;
import com.phuongduy.comment.uimodel.CommentUiModel;
import com.phuongduy.comment.uimodel.LinkItem;

import java.util.List;

public class CommentItemDiffUtil extends DiffUtil.Callback {
    private final List<CommentUiModel> oldList;
    private final List<CommentUiModel> newList;

    public CommentItemDiffUtil(List<CommentUiModel> oldList, List<CommentUiModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        CommentUiModel oldItem = oldList.get(oldItemPosition);
        CommentUiModel newItem = newList.get(newItemPosition);
        if (oldItem instanceof CommentItem && newItem instanceof CommentItem) {
            CommentItem oldComment = (CommentItem) oldItem;
            CommentItem newComment = (CommentItem) newItem;
            return oldComment.id == newComment.id;
        } else if (oldItem instanceof LinkItem && newItem instanceof LinkItem) {
            LinkItem oldLink = (LinkItem) oldItem;
            LinkItem newLink = (LinkItem) newItem;
            return oldLink.url.equals(newLink.url);
        } else return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CommentUiModel oldItem = oldList.get(oldItemPosition);
        CommentUiModel newItem = newList.get(newItemPosition);

        if (oldItem instanceof CommentItem && newItem instanceof CommentItem) {
            CommentItem oldComment = (CommentItem) oldItem;
            CommentItem newComment = (CommentItem) newItem;
            return oldComment.id == newComment.id &&
                    oldComment.comment.equals(newComment.comment) &&
                    oldComment.timeStamp.equals(newComment.timeStamp);
        } else if (oldItem instanceof LinkItem && newItem instanceof LinkItem) {
            LinkItem oldLink = (LinkItem) oldItem;
            LinkItem newLink = (LinkItem) newItem;
            return oldLink.url.equals(newLink.url) && oldLink.title.equals(newLink.title);
        } else return false;
    }
}
