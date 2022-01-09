package com.phuongduy.comment.factory;

import com.phuongduy.comment.data.remote.CommentInfoRemoteDataSource;
import com.phuongduy.comment.data.remote.CommentInfoRemoteDataSourceImpl;

public class CommentInfoRemoteDataSourceFactory {
    public static CommentInfoRemoteDataSource create() {
        return new CommentInfoRemoteDataSourceImpl();
    }
}
