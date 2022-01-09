package com.phuongduy.comment.data;

import com.phuongduy.comment.domain.CommentInfoRepository;

public class CommentInfoExtractorImpl implements CommentInfoExtractor {
    private final CommentInfoRepository repository;
    private final SerializationService serializationService;

    public CommentInfoExtractorImpl(CommentInfoRepository repository, SerializationService serializationService) {
        this.repository = repository;
        this.serializationService = serializationService;
    }

    @Override
    public String extractLinksJsonData(String comment) {
        return serializationService.serialize(repository.extractLinks(comment));
    }

    @Override
    public String extractMentionsJsonData(String comment) {
        return serializationService.serialize(repository.extractMentions(comment));
    }
}
