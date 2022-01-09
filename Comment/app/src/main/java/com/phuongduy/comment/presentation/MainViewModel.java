package com.phuongduy.comment.presentation;

import com.phuongduy.comment.uimodel.CommentUiModel;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface MainViewModel {
    Flowable<List<CommentUiModel>> commentItemList();

    void addComment(String comment);

    void cleanUp();
}
