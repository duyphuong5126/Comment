package com.phuongduy.comment.data;

import com.phuongduy.comment.data.remote.CommentInfoRemoteDataSource;
import com.phuongduy.comment.domain.entity.Link;
import com.phuongduy.comment.domain.entity.Links;
import com.phuongduy.comment.domain.entity.Mentions;
import com.phuongduy.comment.domain.CommentInfoRepository;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyString;

import java.util.List;

import static org.junit.runners.MethodSorters.NAME_ASCENDING;
import static org.mockito.Mockito.when;

@FixMethodOrder(value = NAME_ASCENDING)
public class CommentInfoRepositoryImplTest {
    private final CommentInfoRemoteDataSource remoteDataSource = Mockito.mock(CommentInfoRemoteDataSource.class);

    private final CommentInfoRepository repositoryImpl = new CommentInfoRepositoryImpl(remoteDataSource);

    @Test
    public void case_01_comment_with_one_mentioned_name() {
        Mentions mentions = repositoryImpl.extractMentions("@billgates do you know where is elonmusk");

        List<String> mentionList = mentions.mentionedList();

        assertTrue(mentionList.size() == 1 && mentionList.get(0).equals("billgates"));
    }

    @Test
    public void case_02_comment_with_one_mentioned_name_with_number() {
        Mentions mentions = repositoryImpl.extractMentions("@bill_gates_123_ do you know where is elonmusk");

        List<String> mentionList = mentions.mentionedList();
        assertTrue(mentionList.size() == 1 && mentionList.get(0).equals("bill_gates_123_"));
    }

    @Test
    public void case_03_comment_with_mentioned_name_has_under_score_character() {
        Mentions mentions = repositoryImpl.extractMentions("@bill_gates_ Is your email billgates@microsoft.com.us?");

        List<String> mentionList = mentions.mentionedList();
        assertTrue(mentionList.size() == 1 && mentionList.get(0).equals("bill_gates_"));
    }

    @Test
    public void case_04_comment_with_two_mentioned_names() {
        Mentions mentions = repositoryImpl.extractMentions("@billgates_123 do you know where is @elonmusk_456");

        List<String> mentionList = mentions.mentionedList();
        assertTrue(mentionList.size() == 2 && mentionList.get(0).equals("billgates_123") && mentionList.get(1).equals("elonmusk_456"));
    }

    @Test
    public void case_05_comment_with_mentioned_names_begin_with_special_characters() {
        Mentions mentions = repositoryImpl.extractMentions("$$@billgates do you know where is ###@elonmusk");

        List<String> mentionList = mentions.mentionedList();
        assertTrue(mentionList.size() == 2 && mentionList.get(0).equals("billgates") && mentionList.get(1).equals("elonmusk"));
    }

    @Test
    public void case_06_comment_with_mentioned_names_begin_with_that_names() {
        Mentions mentions = repositoryImpl.extractMentions("billgates@billgates do you know where is elonmusk@elonmusk_123");

        List<String> mentionList = mentions.mentionedList();
        assertTrue(mentionList.size() == 2 && mentionList.get(0).equals("billgates") && mentionList.get(1).equals("elonmusk_123"));
    }

    @Test
    public void case_07_comment_with_a_name_mentioned_twice() {
        Mentions mentions = repositoryImpl.extractMentions("@billgates@billgates do you know where is elonmusk");

        List<String> mentionList = mentions.mentionedList();
        assertTrue(mentionList.size() == 1 && mentionList.get(0).equals("billgates"));
    }

    @Test
    public void case_08_comment_with_a_mentioned_name_and_email() {
        Mentions mentions = repositoryImpl.extractMentions("@billgates Is your email billgates@microsoft.com?");

        List<String> mentionList = mentions.mentionedList();
        assertTrue(mentionList.size() == 1 && mentionList.get(0).equals("billgates"));
    }

    @Test
    public void case_09_comment_with_a_mentioned_name_and_email() {
        Mentions mentions = repositoryImpl.extractMentions("@billgates Is your email billgates@microsoft.com.us?");

        List<String> mentionList = mentions.mentionedList();
        assertTrue(mentionList.size() == 1 && mentionList.get(0).equals("billgates"));
    }

    @Test
    public void case_10_comment_with_a_mentioned_name_begin_with_number() {
        Mentions mentions = repositoryImpl.extractMentions("@123billgates do you know where is elonmusk");

        List<String> mentionList = mentions.mentionedList();
        assertTrue(mentionList.size() == 1 && mentionList.get(0).equals("123billgates"));
    }

    @Test
    public void case_11_comment_with_a_mentioned_name_begin_with_under_score() {
        Mentions mentions = repositoryImpl.extractMentions("@_billgates do you know where is elonmusk");

        List<String> mentionList = mentions.mentionedList();
        assertTrue(mentionList.size() == 1 && mentionList.get(0).equals("_billgates"));
    }

    @Test
    public void case_12_comment_with_valid_link() {
        String url = "https://olympics.com/tokyo-2020/en";
        String pageTitle = "Tokyo 2020 Summer Olympics - Athletes, Medals & Results";

        when(remoteDataSource.getWebsiteTitleFromUrl(url)).thenReturn(pageTitle);

        Links links = repositoryImpl.extractLinks("Olympics 2020 is happening; https://olympics.com/tokyo-2020/en/");

        verify(remoteDataSource).getWebsiteTitleFromUrl(url);

        List<Link> linkList = links.getLinks();
        assertEquals(1, linkList.size());
        assertEquals(url, linkList.get(0).getUrl());
        assertEquals(pageTitle, linkList.get(0).getTitle());
    }

    @Test
    public void case_13_comment_with_valid_link() {
        String firstUrl = "https://www.google.com";
        String firstPageTitle = "Google";
        String secondUrl = "https://twitter.com/home";
        String secondPageTitle = "Twitter";

        when(remoteDataSource.getWebsiteTitleFromUrl(firstUrl)).thenReturn(firstPageTitle);
        when(remoteDataSource.getWebsiteTitleFromUrl(secondUrl)).thenReturn(secondPageTitle);

        Links links = repositoryImpl.extractLinks("Google: https://www.google.com/, Twitter: https://twitter.com/home");

        verify(remoteDataSource).getWebsiteTitleFromUrl(firstUrl);
        List<Link> linkList = links.getLinks();
        assertTrue(linkList.size() == 2 &&
                linkList.get(0).getUrl().equals(firstUrl) &&
                linkList.get(0).getTitle().equals(firstPageTitle) &&
                linkList.get(1).getUrl().equals(secondUrl) &&
                linkList.get(1).getTitle().equals(secondPageTitle));
    }

    @Test
    public void case_14_comment_with_invalid_link() {
        Links links = repositoryImpl.extractLinks("Olympics 2020 is happening; https://olympics .com/tokyo-2020/en/");

        verify(remoteDataSource, never()).getWebsiteTitleFromUrl(anyString());

        assertTrue(links.getLinks().isEmpty());
    }

    @Test
    public void case_15_comment_with_some_mentioned_names_and_links() {
        String comment = "@billgates @elonmusk are you interted in the Olympics 2020 - \nhttps://olympics.com/tokyo-2020/en/";
        String linkTitle = "Tokyo 2020 Summer Olympics - Athletes, Medals & Results";

        when(remoteDataSource.getWebsiteTitleFromUrl("https://olympics.com/tokyo-2020/en")).thenReturn(linkTitle);

        Mentions mentions = repositoryImpl.extractMentions(comment);

        List<String> mentionList = mentions.mentionedList();
        assertEquals(2, mentionList.size());
        assertEquals("billgates", mentionList.get(0));
        assertEquals("elonmusk", mentionList.get(1));

        Links links = repositoryImpl.extractLinks(comment);
        assertEquals(1, links.getLinks().size());

        Link olympicLink = links.getLinks().get(0);
        assertEquals("https://olympics.com/tokyo-2020/en", olympicLink.getUrl());
        assertEquals(linkTitle, olympicLink.getTitle());

        verify(remoteDataSource).getWebsiteTitleFromUrl("https://olympics.com/tokyo-2020/en");
    }
}