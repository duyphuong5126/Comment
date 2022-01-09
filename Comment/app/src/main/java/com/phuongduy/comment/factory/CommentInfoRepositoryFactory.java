package com.phuongduy.comment.factory;

import com.phuongduy.comment.data.CommentInfoRepositoryImpl;
import com.phuongduy.comment.data.remote.CommentInfoRemoteDataSource;
import com.phuongduy.comment.domain.CommentInfoRepository;

public class CommentInfoRepositoryFactory {
    public static CommentInfoRepository create() {
        CommentInfoRemoteDataSource remoteDataSource = CommentInfoRemoteDataSourceFactory.create();
        return new CommentInfoRepositoryImpl(remoteDataSource);
    }
}
