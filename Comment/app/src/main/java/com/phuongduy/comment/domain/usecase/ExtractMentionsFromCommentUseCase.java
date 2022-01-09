package com.phuongduy.comment.domain.usecase;

import com.phuongduy.comment.domain.entity.Mentions;

public interface ExtractMentionsFromCommentUseCase {
    Mentions execute(String comment);
}
