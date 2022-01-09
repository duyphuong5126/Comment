package com.phuongduy.comment.domain.usecase;

import com.phuongduy.comment.domain.CommentInfoRepository;
import com.phuongduy.comment.domain.entity.Mentions;

public class ExtractMentionsFromCommentUseCaseImpl implements ExtractMentionsFromCommentUseCase {
    private final CommentInfoRepository repository;

    public ExtractMentionsFromCommentUseCaseImpl(CommentInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mentions execute(String comment) {
        return repository.extractMentions(comment);
    }
}
