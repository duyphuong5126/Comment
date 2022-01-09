package com.phuongduy.comment.diffutil;

import com.phuongduy.comment.uimodel.CommentItem;
import com.phuongduy.comment.uimodel.CommentUiModel;
import com.phuongduy.comment.uimodel.LinkItem;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CommentItemDiffUtilTest {
    @Test
    public void list_sizes() {
        ArrayList<CommentUiModel> oldCommentList = new ArrayList<>();
        ArrayList<CommentUiModel> newCommentList = new ArrayList<>();
        List<String> emptyMentionList = new ArrayList<>();

        oldCommentList.add(new CommentItem(123, "abc", "2021-01-09 10:00:00", emptyMentionList));
        oldCommentList.add(new CommentItem(123, "xyz", "2021-01-09 10:10:00", emptyMentionList));

        newCommentList.add(new CommentItem(123, "abc", "2021-01-09 10:20:00", emptyMentionList));
        newCommentList.add(new LinkItem("htts://www.google.com", "Google"));
        newCommentList.add(new CommentItem(123, "xyz", "2021-01-09 10:30:00", emptyMentionList));
        newCommentList.add(new LinkItem("htts://www.twitter.com", "Twitter"));
        newCommentList.add(new LinkItem("htts://www.apple.com", "Apple"));

        CommentItemDiffUtil diffUtil = new CommentItemDiffUtil(oldCommentList, newCommentList);
        assertEquals(2, diffUtil.getOldListSize());
        assertEquals(5, diffUtil.getNewListSize());
    }

    @Test
    public void areItemsTheSame() {
        ArrayList<CommentUiModel> oldCommentList = new ArrayList<>();
        ArrayList<CommentUiModel> newCommentList = new ArrayList<>();
        CommentItemDiffUtil diffUtil = new CommentItemDiffUtil(oldCommentList, newCommentList);

        List<String> emptyMentionList = new ArrayList<>();

        oldCommentList.add(new CommentItem(123, "abc", "2021-01-09 10:00:00", emptyMentionList));
        newCommentList.add(new CommentItem(456, "xyz", "2021-01-09 10:00:00", emptyMentionList));

        assertFalse(diffUtil.areItemsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new CommentItem(123, "abc", "2021-01-09 10:00:00", emptyMentionList));
        newCommentList.add(new CommentItem(123, "xyz", "2021-01-09 10:00:00", emptyMentionList));
        assertTrue(diffUtil.areItemsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new LinkItem("htts://www.google.com", "Google"));
        newCommentList.add(new LinkItem("htts://google.com", "Google"));
        assertFalse(diffUtil.areItemsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new LinkItem("htts://www.google.com", "Google"));
        newCommentList.add(new LinkItem("htts://www.google.com", "Google"));
        assertTrue(diffUtil.areItemsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new LinkItem("htts://www.google.com", "Google"));
        newCommentList.add(new CommentItem(123, "xyz", "2021-01-09 10:00:00", emptyMentionList));
        assertFalse(diffUtil.areItemsTheSame(0, 0));
    }

    @Test
    public void areContentsTheSame() {
        ArrayList<CommentUiModel> oldCommentList = new ArrayList<>();
        ArrayList<CommentUiModel> newCommentList = new ArrayList<>();
        CommentItemDiffUtil diffUtil = new CommentItemDiffUtil(oldCommentList, newCommentList);

        List<String> emptyMentionList = new ArrayList<>();

        oldCommentList.add(new CommentItem(123, "abc", "2021-01-09 10:00:00", emptyMentionList));
        newCommentList.add(new CommentItem(456, "abc", "2021-01-09 10:00:00", emptyMentionList));
        assertFalse(diffUtil.areContentsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new CommentItem(123, "abc", "2021-01-09 10:00:00", emptyMentionList));
        newCommentList.add(new CommentItem(123, "xyz", "2021-01-09 10:00:00", emptyMentionList));
        assertFalse(diffUtil.areContentsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new CommentItem(123, "abc", "2021-01-09 10:00:00", emptyMentionList));
        newCommentList.add(new CommentItem(123, "abc", "2021-01-09 10:01:00", emptyMentionList));
        assertFalse(diffUtil.areContentsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new CommentItem(123, "abc", "2021-01-09 10:00:00", emptyMentionList));
        newCommentList.add(new CommentItem(123, "abc", "2021-01-09 10:00:00", emptyMentionList));
        assertTrue(diffUtil.areContentsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new LinkItem("htts://www.google.com", "Google"));
        newCommentList.add(new LinkItem("htts://google.com", "Google"));
        assertFalse(diffUtil.areContentsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new LinkItem("htts://www.google.com", "Google"));
        newCommentList.add(new LinkItem("htts://www.twitter.com", "Twitter"));
        assertFalse(diffUtil.areContentsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new LinkItem("htts://www.google.com", "Google"));
        newCommentList.add(new LinkItem("htts://www.google.com", "Google"));
        assertTrue(diffUtil.areContentsTheSame(0, 0));

        oldCommentList.clear();
        newCommentList.clear();

        oldCommentList.add(new LinkItem("htts://www.google.com", "Google"));
        newCommentList.add(new CommentItem(123, "xyz", "2021-01-09 10:00:00", emptyMentionList));
        assertFalse(diffUtil.areContentsTheSame(0, 0));
    }
}