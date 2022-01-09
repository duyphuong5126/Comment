package com.phuongduy.comment.data;

import com.phuongduy.comment.factory.CommentInfoExtractorFactory;

/**
 * Service that can be used to extract the mentioned names or links in a comment.
 * The return data is json format, please see the method descriptions for more details.
 * Its methods should be called in background threads, especially the method to extract links info.
 * Use the {@link CommentInfoExtractorFactory#create()} to create an instance of this service.
 */
public interface CommentInfoExtractor {
    /**
     * Extract the mentioned names in a comment and return them as a json string.
     *
     * @param comment: The comment that may contain some mentioned names.
     *                 For example: "@billgates do you know where is @elonmusk?"
     * @return json data of the mentioned names, for example: { "mentions": [ "billgates", "elonmusk" ] }
     */
    String extractMentionsJsonData(String comment);

    /**
     * Extract the links in a comment and return them as a json string.
     * This method must be run in a background thread, due to some potential network usage to get the information of the links.
     *
     * @param comment: The comment that may contain some mentioned names.
     *                 For example: "Olympics 2020 is happening; https://olympics.com/tokyo-2020/en/"
     * @return json data of the links extracted from the comment.
     * For example:
     * {
     * "links": [
     * {
     * "url": "https://olympics.com/tokyo-2020/en/",
     * "title": "Tokyo 2020 Olympics - Home of the Next Summer Games" }
     * ]
     * }
     */
    String extractLinksJsonData(String comment);
}
