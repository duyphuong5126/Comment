package com.phuongduy.comment.domain.usecase;

import com.phuongduy.comment.domain.CommentInfoRepository;
import com.phuongduy.comment.domain.entity.Links;

public class ExtractLinksFromCommentUseCaseImpl implements ExtractLinksFromCommentUseCase {
    private final CommentInfoRepository repository;

    public ExtractLinksFromCommentUseCaseImpl(CommentInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Links execute(String comment) {
        return repository.extractLinks(comment);
    }
}
