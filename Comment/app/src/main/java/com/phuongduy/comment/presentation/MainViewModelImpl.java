package com.phuongduy.comment.presentation;

import android.util.Log;

import com.phuongduy.comment.domain.entity.Link;
import com.phuongduy.comment.domain.entity.Links;
import com.phuongduy.comment.domain.entity.Mentions;
import com.phuongduy.comment.domain.usecase.ExtractLinksFromCommentUseCase;
import com.phuongduy.comment.domain.usecase.ExtractMentionsFromCommentUseCase;
import com.phuongduy.comment.uimodel.CommentItem;
import com.phuongduy.comment.uimodel.CommentUiModel;
import com.phuongduy.comment.uimodel.LinkItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModelImpl implements MainViewModel {
    private static final String TAG = "MainViewModelImpl";

    private final ExtractMentionsFromCommentUseCase extractMentionsFromCommentUseCase;
    private final ExtractLinksFromCommentUseCase extractLinksFromCommentUseCase;

    private final PublishProcessor<List<CommentUiModel>> commentItemListProcessor = PublishProcessor.create();

    private final ArrayList<CommentUiModel> commentItemList = new ArrayList<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);

    public MainViewModelImpl(ExtractMentionsFromCommentUseCase extractMentionsFromCommentUseCase,
                             ExtractLinksFromCommentUseCase extractLinksFromCommentUseCase) {
        this.extractLinksFromCommentUseCase = extractLinksFromCommentUseCase;
        this.extractMentionsFromCommentUseCase = extractMentionsFromCommentUseCase;
    }

    @Override
    public Flowable<List<CommentUiModel>> commentItemList() {
        return commentItemListProcessor;
    }

    @Override
    public void addComment(String comment) {
        Disposable disposable = Single.fromCallable((Callable<Mentions>) () -> extractMentionsFromCommentUseCase.execute(comment))
                .doOnSuccess(mentions -> {
                    String timeStamp = dateFormat.format(new Date());
                    CommentItem commentItem = new CommentItem(System.currentTimeMillis(), comment, timeStamp, mentions.mentionedList());
                    commentItemList.add(commentItem);
                    commentItemListProcessor.offer(commentItemList);
                })
                .map((Function<Mentions, Links>) mentions -> extractLinksFromCommentUseCase.execute(comment))
                .doOnSuccess(links -> {
                    for (Link link : links.getLinks()) {
                        commentItemList.add(new LinkItem(link.getUrl(), link.getTitle()));
                    }
                    commentItemListProcessor.offer(commentItemList);
                })
                .ignoreElement()
                .subscribeOn(Schedulers.io())
                .subscribe(() -> Log.d(TAG, "Finish info extraction"),
                        throwable -> Log.e(TAG, String.format("Failed to extract info with error %s", throwable.toString())));

        compositeDisposable.add(disposable);
    }

    @Override
    public void cleanUp() {
        compositeDisposable.clear();
    }
}
