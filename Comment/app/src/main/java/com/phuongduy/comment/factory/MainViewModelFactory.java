package com.phuongduy.comment.factory;

import com.phuongduy.comment.domain.usecase.ExtractLinksFromCommentUseCase;
import com.phuongduy.comment.domain.usecase.ExtractMentionsFromCommentUseCase;
import com.phuongduy.comment.presentation.MainViewModel;
import com.phuongduy.comment.presentation.MainViewModelImpl;

public class MainViewModelFactory {
    public static MainViewModel create() {
        ExtractLinksFromCommentUseCase extractLinksFromCommentUseCase = ExtractLinksFromCommentUseCaseFactory.create();
        ExtractMentionsFromCommentUseCase extractMentionsFromCommentUseCase = ExtractMentionsFromCommentUseCaseFactory.create();
        return new MainViewModelImpl(extractMentionsFromCommentUseCase, extractLinksFromCommentUseCase);
    }
}
