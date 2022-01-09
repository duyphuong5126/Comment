package com.phuongduy.comment.factory;

import com.phuongduy.comment.domain.usecase.ExtractLinksFromCommentUseCase;
import com.phuongduy.comment.domain.usecase.ExtractLinksFromCommentUseCaseImpl;

public class ExtractLinksFromCommentUseCaseFactory {
    public static ExtractLinksFromCommentUseCase create() {
        return new ExtractLinksFromCommentUseCaseImpl(CommentInfoRepositoryFactory.create());
    }
}
