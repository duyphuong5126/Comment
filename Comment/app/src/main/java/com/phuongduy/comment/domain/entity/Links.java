package com.phuongduy.comment.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Links {
    private final ArrayList<Link> links = new ArrayList<>();

    public void addLink(Link link) {
        if (!links.contains(link)) {
            links.add(link);
        }
    }

    public List<Link> getLinks() {
        return links;
    }
}
