package com.phuongduy.comment.data;

import com.google.gson.Gson;

public class SerializationServiceImpl implements SerializationService {
    @Override
    public <T> String serialize(T object) {
        return new Gson().toJson(object);
    }

    @Override
    public <T> T deserialize(String json, Class<T> objectClass) {
        return new Gson().fromJson(json, objectClass);
    }
}
