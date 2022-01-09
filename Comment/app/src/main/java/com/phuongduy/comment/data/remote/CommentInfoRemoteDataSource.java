package com.phuongduy.comment.data.remote;

import com.phuongduy.comment.factory.CommentInfoRemoteDataSourceFactory;

/**
 * Use the {@link CommentInfoRemoteDataSourceFactory#create()} to create an instance of this data source.
 */
public interface CommentInfoRemoteDataSource {
    String getWebsiteTitleFromUrl(String url);
}
