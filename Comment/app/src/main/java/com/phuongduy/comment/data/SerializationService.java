package com.phuongduy.comment.data;

import com.phuongduy.comment.factory.SerializationServiceFactory;

/**
 * Use the {@link SerializationServiceFactory#create()} to create an instance of this service.
 */
public interface SerializationService {
    <T> String serialize(T object);

    <T> T deserialize(String json, Class<T> objectClass);
}
