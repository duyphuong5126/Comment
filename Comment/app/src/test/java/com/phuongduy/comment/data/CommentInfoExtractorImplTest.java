package com.phuongduy.comment.data;

import com.phuongduy.comment.domain.CommentInfoRepository;
import com.phuongduy.comment.domain.entity.Link;
import com.phuongduy.comment.domain.entity.Links;
import com.phuongduy.comment.domain.entity.Mentions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class CommentInfoExtractorImplTest {
    private final CommentInfoRepository repository = mock(CommentInfoRepository.class);
    private final SerializationService serializationService = mock(SerializationService.class);

    private final CommentInfoExtractor commentInfoExtractor = new CommentInfoExtractorImpl(repository, serializationService);

    @Test
    public void extract_links_and_mentions_json_data() {
        String comment = "@billgates @elonmusk are you interted in the Olympics 2020 - https://olympics.com/tokyo-2020/en/";

        Links links = new Links();
        links.addLink(new Link("https://olympics.com/tokyo-2020/en/", "Tokyo 2020 Summer Olympics"));

        String linkJsonData = "{ \"links\":[{ \"url\":\"https://olympics.com/tokyo-2020/en/\",\"title\":\"Tokyo 2020 Summer Olympics\" }]}";
        when(repository.extractLinks(comment)).thenReturn(links);
        when(serializationService.serialize(links)).thenReturn(linkJsonData);

        String resultLinkJson = commentInfoExtractor.extractLinksJsonData(comment);

        assertEquals(linkJsonData, resultLinkJson);

        verify(repository).extractLinks(comment);
        verify(serializationService).serialize(links);

        Mentions mentions = new Mentions();
        mentions.addMentionedName("billgates");
        mentions.addMentionedName("elonmusk");
        String mentionsJsonData = "{ \"mentions\": [ \"billgates\", \"elonmusk\" ] }";
        when(repository.extractMentions(comment)).thenReturn(mentions);
        when(serializationService.serialize(mentions)).thenReturn(mentionsJsonData);

        String resultMentionsJson = commentInfoExtractor.extractMentionsJsonData(comment);
        assertEquals(mentionsJsonData, resultMentionsJson);
        verify(repository).extractMentions(comment);
        verify(serializationService).serialize(mentions);
    }
}