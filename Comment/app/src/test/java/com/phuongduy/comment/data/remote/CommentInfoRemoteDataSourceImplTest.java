package com.phuongduy.comment.data.remote;

import com.phuongduy.comment.factory.CommentInfoRemoteDataSourceFactory;

import org.junit.Assert;
import org.junit.Test;

public class CommentInfoRemoteDataSourceImplTest {
    private final CommentInfoRemoteDataSource remoteDataSourceImpl = CommentInfoRemoteDataSourceFactory.create();

    @Test
    public void getWebsiteTitleFromUrl_valid_url() {
        System.out.printf("Page title %s%n", remoteDataSourceImpl.getWebsiteTitleFromUrl("https://google.com"));
    }

    @Test
    public void getWebsiteTitleFromUrl_invalid_url() {
        String title = remoteDataSourceImpl.getWebsiteTitleFromUrl("https");
        Assert.assertEquals("", title);
    }
}