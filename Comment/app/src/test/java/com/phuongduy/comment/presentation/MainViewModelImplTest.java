package com.phuongduy.comment.presentation;

import com.phuongduy.comment.domain.entity.Link;
import com.phuongduy.comment.domain.entity.Links;
import com.phuongduy.comment.domain.entity.Mentions;
import com.phuongduy.comment.domain.usecase.ExtractLinksFromCommentUseCase;
import com.phuongduy.comment.domain.usecase.ExtractMentionsFromCommentUseCase;
import com.phuongduy.comment.uimodel.CommentItem;
import com.phuongduy.comment.uimodel.CommentUiModel;
import com.phuongduy.comment.uimodel.LinkItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

public class MainViewModelImplTest {
    private final ExtractMentionsFromCommentUseCase extractMentionsFromCommentUseCase = mock(ExtractMentionsFromCommentUseCase.class);
    private final ExtractLinksFromCommentUseCase extractLinksFromCommentUseCase = mock(ExtractLinksFromCommentUseCase.class);

    private final MainViewModel viewModel = new MainViewModelImpl(extractMentionsFromCommentUseCase, extractLinksFromCommentUseCase);

    @Before
    public void setUp() {
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @After
    public void cleanUp() {
        viewModel.cleanUp();
    }

    @Test
    public void addComment_success_case() {
        String comment = "@billgates are you interested in Olympic 2020 - https://olympics.com/tokyo-2020/en/?";
        Mentions mentions = new Mentions();
        mentions.addMentionedName("billgates");

        Links links = new Links();
        links.addLink(new Link("https://olympics.com/tokyo-2020/en/", "Tokyo 2020 Summer Olympics - Athletes, Medals & Results"));

        when(extractMentionsFromCommentUseCase.execute(comment)).thenReturn(mentions);
        when(extractLinksFromCommentUseCase.execute(comment)).thenReturn(links);

        final ArrayList<CommentUiModel> commentUiModelList = new ArrayList<>();

        viewModel.commentItemList().subscribe(commentUiModels -> {
            commentUiModelList.clear();
            commentUiModelList.addAll(commentUiModels);
        });

        viewModel.addComment(comment);

        assertEquals(2, commentUiModelList.size());

        CommentItem commentItem = (CommentItem) commentUiModelList.get(0);

        assertEquals(comment, commentItem.comment);
        assertEquals(1, commentItem.mentionedPeople.size());
        assertEquals("billgates", commentItem.mentionedPeople.get(0));

        LinkItem linkItem = (LinkItem) commentUiModelList.get(1);
        assertEquals("https://olympics.com/tokyo-2020/en/", linkItem.url);
        assertEquals("Tokyo 2020 Summer Olympics - Athletes, Medals & Results", linkItem.title);

        verify(extractMentionsFromCommentUseCase).execute(comment);
        verify(extractLinksFromCommentUseCase).execute(comment);
    }

    @Test
    public void addComment_failure_in_mentions_extraction() {
        String comment = "@billgates are you interested in Olympic 2020 - https://olympics.com/tokyo-2020/en/?";

        when(extractMentionsFromCommentUseCase.execute(comment)).thenThrow(new RuntimeException());

        final ArrayList<CommentUiModel> commentUiModelList = new ArrayList<>();

        viewModel.commentItemList().subscribe(commentUiModels -> {
            commentUiModelList.clear();
            commentUiModelList.addAll(commentUiModels);
        });

        viewModel.addComment(comment);

        assertTrue(commentUiModelList.isEmpty());

        verify(extractMentionsFromCommentUseCase).execute(comment);
        verify(extractLinksFromCommentUseCase, never()).execute(comment);
    }

    @Test
    public void addComment_failure_in_links_extraction() {
        String comment = "@billgates are you interested in Olympic 2020 - https://olympics.com/tokyo-2020/en/?";
        Mentions mentions = new Mentions();
        mentions.addMentionedName("billgates");

        when(extractMentionsFromCommentUseCase.execute(comment)).thenReturn(mentions);
        when(extractLinksFromCommentUseCase.execute(comment)).thenThrow(new RuntimeException());

        final ArrayList<CommentUiModel> commentUiModelList = new ArrayList<>();

        viewModel.commentItemList().subscribe(commentUiModels -> {
            commentUiModelList.clear();
            commentUiModelList.addAll(commentUiModels);
        });

        viewModel.addComment(comment);

        assertEquals(1, commentUiModelList.size());

        CommentItem commentItem = (CommentItem) commentUiModelList.get(0);

        assertEquals(comment, commentItem.comment);
        assertEquals(1, commentItem.mentionedPeople.size());
        assertEquals("billgates", commentItem.mentionedPeople.get(0));

        verify(extractMentionsFromCommentUseCase).execute(comment);
        verify(extractLinksFromCommentUseCase).execute(comment);
    }
}