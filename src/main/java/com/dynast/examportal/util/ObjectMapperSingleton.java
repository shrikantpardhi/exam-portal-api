package com.dynast.examportal.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class ObjectMapperSingleton {
    private static ObjectMapper INSTANCE;

    public static ObjectMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ObjectMapper();
        }
        return INSTANCE;
    }
}
