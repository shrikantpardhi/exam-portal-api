package com.dynast.examportal.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public final class ObjectMapperSingleton {
    private static ObjectMapper INSTANCE;

    public static ObjectMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObjectMapper();
        }
        INSTANCE.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return INSTANCE;
    }
}
