package com.phuongduy.comment.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.phuongduy.comment.databinding.ItemLinkBinding;
import com.phuongduy.comment.uimodel.LinkItem;

public class LinkViewHolder extends RecyclerView.ViewHolder {
    private final ItemLinkBinding viewBinding;

    public LinkViewHolder(ViewGroup parent) {
        super(ItemLinkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
        viewBinding = ItemLinkBinding.bind(itemView);
    }

    public void bindTo(LinkItem linkItem) {
        viewBinding.tvLink.setText(linkItem.url);
        viewBinding.tvLinkTitle.setText(linkItem.title);
        viewBinding.tvLinkTitle.setVisibility(linkItem.title.isEmpty() ? View.GONE : View.VISIBLE);
    }
}
