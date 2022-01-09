package com.phuongduy.comment.factory;

import com.phuongduy.comment.data.CommentInfoExtractor;
import com.phuongduy.comment.data.CommentInfoExtractorImpl;
import com.phuongduy.comment.data.SerializationService;
import com.phuongduy.comment.domain.CommentInfoRepository;

public class CommentInfoExtractorFactory {

    public static CommentInfoExtractor create() {
        CommentInfoRepository repository = CommentInfoRepositoryFactory.create();
        SerializationService serializationService = SerializationServiceFactory.create();
        return new CommentInfoExtractorImpl(repository, serializationService);
    }
}
