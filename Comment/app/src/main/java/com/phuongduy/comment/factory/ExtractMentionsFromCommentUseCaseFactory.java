package com.phuongduy.comment.factory;

import com.phuongduy.comment.domain.usecase.ExtractMentionsFromCommentUseCase;
import com.phuongduy.comment.domain.usecase.ExtractMentionsFromCommentUseCaseImpl;

public class ExtractMentionsFromCommentUseCaseFactory {
    public static ExtractMentionsFromCommentUseCase create() {
        return new ExtractMentionsFromCommentUseCaseImpl(CommentInfoRepositoryFactory.create());
    }
}
