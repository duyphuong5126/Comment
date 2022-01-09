package com.phuongduy.comment.domain.usecase;

import com.phuongduy.comment.domain.entity.Links;

public interface ExtractLinksFromCommentUseCase {
    Links execute(String comment);
}
