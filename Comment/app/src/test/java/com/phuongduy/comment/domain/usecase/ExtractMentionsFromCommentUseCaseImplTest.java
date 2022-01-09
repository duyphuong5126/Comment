package com.phuongduy.comment.domain.usecase;

import com.phuongduy.comment.domain.CommentInfoRepository;
import com.phuongduy.comment.domain.entity.Mentions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExtractMentionsFromCommentUseCaseImplTest {
    private final CommentInfoRepository repository = mock(CommentInfoRepository.class);

    private final ExtractMentionsFromCommentUseCase useCase = new ExtractMentionsFromCommentUseCaseImpl(repository);

    @Test
    public void execute() {
        String comment = "@billgates are you interested in Olympic 2020 - https://olympics.com/tokyo-2020/en/?";
        Mentions mentions = new Mentions();
        mentions.addMentionedName("billgates");

        when(repository.extractMentions(comment)).thenReturn(mentions);

        Mentions result = useCase.execute(comment);

        assertEquals(result, mentions);

        verify(repository).extractMentions(comment);
    }
}