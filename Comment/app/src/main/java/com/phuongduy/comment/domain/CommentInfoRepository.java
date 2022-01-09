package com.phuongduy.comment.domain;

import com.phuongduy.comment.domain.entity.Links;
import com.phuongduy.comment.domain.entity.Mentions;
import com.phuongduy.comment.factory.CommentInfoRepositoryFactory;

/**
 * Use the {@link CommentInfoRepositoryFactory#create()} to create an instance of this repository.
 */
public interface CommentInfoRepository {
    Mentions extractMentions(String comment);

    Links extractLinks(String comment);
}
