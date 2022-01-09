package com.phuongduy.comment.factory;

import com.phuongduy.comment.data.SerializationService;
import com.phuongduy.comment.data.SerializationServiceImpl;

public class SerializationServiceFactory {
    public static SerializationService create() {
        return new SerializationServiceImpl();
    }
}
