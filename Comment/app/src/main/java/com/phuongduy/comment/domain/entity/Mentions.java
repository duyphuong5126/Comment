package com.phuongduy.comment.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Mentions {
    private final ArrayList<String> mentions = new ArrayList<>();

    public void addMentionedName(String name) {
        if (!mentions.contains(name)) {
            mentions.add(name);
        }
    }

    public List<String> mentionedList() {
        return mentions;
    }
}
