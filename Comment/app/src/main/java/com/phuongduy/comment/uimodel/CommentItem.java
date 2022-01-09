package com.phuongduy.comment.uimodel;

import java.util.List;

public class CommentItem extends CommentUiModel {
    public long id;
    public String comment;
    public String timeStamp;
    public List<String> mentionedPeople;

    public CommentItem(long id, String comment, String timeStamp, List<String> mentionedPeople) {
        this.id = id;
        this.comment = comment;
        this.timeStamp = timeStamp;
        this.mentionedPeople = mentionedPeople;
    }
}
