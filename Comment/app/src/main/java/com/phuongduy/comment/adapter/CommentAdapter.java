package com.phuongduy.comment.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.phuongduy.comment.diffutil.CommentItemDiffUtil;
import com.phuongduy.comment.uimodel.CommentItem;
import com.phuongduy.comment.uimodel.CommentUiModel;
import com.phuongduy.comment.uimodel.LinkItem;
import com.phuongduy.comment.viewholder.CommentViewHolder;
import com.phuongduy.comment.viewholder.LinkViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<CommentUiModel> currentList = new ArrayList<>();

    private static final int COMMENT_ITEM_TYPE = 1;
    private static final int LINK_ITEM_TYPE = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case COMMENT_ITEM_TYPE:
                return new CommentViewHolder(parent);
            case LINK_ITEM_TYPE:
                return new LinkViewHolder(parent);
            default:
                throw new RuntimeException(String.format("Invalid view type %d", viewType));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentUiModel item = currentList.get(position);
        if (holder instanceof CommentViewHolder) {
            ((CommentViewHolder) holder).bindTo((CommentItem) item);
        } else if (holder instanceof LinkViewHolder) {
            ((LinkViewHolder) holder).bindTo((LinkItem) item);
        }
    }

    @Override
    public int getItemCount() {
        return currentList.size();
    }

    @Override
    public int getItemViewType(int position) {
        CommentUiModel item = currentList.get(position);
        if (item instanceof CommentItem) {
            return COMMENT_ITEM_TYPE;
        } else if (item instanceof LinkItem) {
            return LINK_ITEM_TYPE;
        } else throw new RuntimeException(String.format("Invalid item %s", item.toString()));
    }

    public void updateList(List<CommentUiModel> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CommentItemDiffUtil(currentList, newList));
        currentList.clear();
        currentList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }
}
