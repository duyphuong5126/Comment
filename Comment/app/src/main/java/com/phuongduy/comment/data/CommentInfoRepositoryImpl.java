package com.phuongduy.comment.data;

import com.phuongduy.comment.data.remote.CommentInfoRemoteDataSource;
import com.phuongduy.comment.domain.entity.Link;
import com.phuongduy.comment.domain.entity.Links;
import com.phuongduy.comment.domain.entity.Mentions;
import com.phuongduy.comment.domain.CommentInfoRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentInfoRepositoryImpl implements CommentInfoRepository {
    private static final Pattern MENTION_PATTERN = Pattern.compile("@([A-Za-z0-9_.]+[A-Za-z0-9_.])");
    private static final Pattern EMAIL_DOMAIN_PATTERN = Pattern.compile("@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final Pattern URL_PATTERN = Pattern.compile("(?i)\\b(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,}))\\.?)(?::\\d{2,5})?(?:[/?#]\\S*)?\\b");
    private static final String MENTION_STRING = "@";

    private final CommentInfoRemoteDataSource remoteDataSource;

    public CommentInfoRepositoryImpl(CommentInfoRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Mentions extractMentions(String comment) {
        Mentions mentions = new Mentions();
        Matcher mentionMatcher = MENTION_PATTERN.matcher(comment);
        while (mentionMatcher.find()) {
            String mentionedName = mentionMatcher.group().replace(MENTION_STRING, "");
            if (!EMAIL_DOMAIN_PATTERN.matcher(mentionMatcher.group()).find()) {
                mentions.addMentionedName(mentionedName);
            }
        }
        return mentions;
    }

    @Override
    public Links extractLinks(String comment) {
        Links links = new Links();
        Matcher urlMatcher = URL_PATTERN.matcher(comment);
        while (urlMatcher.find()) {
            String url = urlMatcher.group();
            Link link = new Link(url, remoteDataSource.getWebsiteTitleFromUrl(url));
            links.addLink(link);
        }
        return links;
    }
}
