package com.phuongduy.comment.domain.usecase;

import com.phuongduy.comment.domain.CommentInfoRepository;
import com.phuongduy.comment.domain.entity.Link;
import com.phuongduy.comment.domain.entity.Links;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class ExtractLinksFromCommentUseCaseImplTest {
    private final CommentInfoRepository repository = mock(CommentInfoRepository.class);

    private final ExtractLinksFromCommentUseCase useCase = new ExtractLinksFromCommentUseCaseImpl(repository);

    @Test
    public void execute() {
        String comment = "@billgates are you interested in Olympic 2020 - https://olympics.com/tokyo-2020/en/?";
        Links links = new Links();
        links.addLink(new Link("https://olympics.com/tokyo-2020/en/", "Tokyo 2020 Summer Olympics - Athletes, Medals & Results"));

        when(repository.extractLinks(comment)).thenReturn(links);

        Links result = useCase.execute(comment);

        assertEquals(result, links);

        verify(repository).extractLinks(comment);
    }
}