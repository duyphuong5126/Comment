package com.phuongduy.comment.data;

import com.phuongduy.comment.domain.entity.Link;
import com.phuongduy.comment.domain.entity.Links;
import com.phuongduy.comment.domain.entity.Mentions;
import com.phuongduy.comment.factory.SerializationServiceFactory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.List;

public class SerializationServiceImplTest {
    private final SerializationService serviceImpl = SerializationServiceFactory.create();

    @Test
    public void mentions_serialization_test() {
        Mentions mentions = new Mentions();
        mentions.addMentionedName("billgate");
        mentions.addMentionedName("elonmusk");
        assertEquals("{\"mentions\":[\"billgate\",\"elonmusk\"]}", serviceImpl.serialize(mentions));
    }

    @Test
    public void mentions_deserialization_test() {
        Mentions mentions = serviceImpl.deserialize("{\"mentions\":[\"billgate\",\"elonmusk\"]}", Mentions.class);
        assertEquals(2, mentions.mentionedList().size());
        assertEquals("billgate", mentions.mentionedList().get(0));
        assertEquals("elonmusk", mentions.mentionedList().get(1));
    }

    @Test
    public void links_serialization_test() {
        Links links = new Links();
        links.addLink(new Link("https://www.google.com", "Google"));
        links.addLink(new Link("https://twitter.com/home", "Twitter"));
        assertEquals("{\"links\":[{\"url\":\"https://www.google.com\",\"title\":\"Google\"},{\"url\":\"https://twitter.com/home\",\"title\":\"Twitter\"}]}",
                serviceImpl.serialize(links));
    }

    @Test
    public void links_deserialization_test() {
        Links links = serviceImpl.deserialize("{\"links\":[{\"url\":\"https://www.google.com\",\"title\":\"Google\"},{\"url\":\"https://twitter.com/home\",\"title\":\"Twitter\"}]}", Links.class);

        List<Link> linkList = links.getLinks();
        assertEquals(2, linkList.size());
        assertEquals("https://www.google.com", linkList.get(0).getUrl());
        assertEquals("Google", linkList.get(0).getTitle());
        assertEquals("https://twitter.com/home", linkList.get(1).getUrl());
        assertEquals("Twitter", linkList.get(1).getTitle());
    }
}