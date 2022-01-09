package com.phuongduy.comment.data.remote;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CommentInfoRemoteDataSourceImpl implements CommentInfoRemoteDataSource {
    private static final int CONNECT_TIMEOUT = 10000;

    @Override
    public String getWebsiteTitleFromUrl(String url) {
        try {
            Document document = Jsoup.connect(url).timeout(CONNECT_TIMEOUT).get();
            return document.title();
        } catch (Throwable error) {
            return "";
        }
    }
}
