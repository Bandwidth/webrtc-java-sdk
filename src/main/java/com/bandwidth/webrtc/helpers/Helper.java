package com.bandwidth.webrtc.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static com.google.common.base.Strings.isNullOrEmpty;

public class Helper {

    public static ObjectMapper mapper = new ObjectMapper() {
        private static final long serialVersionUID = -174113593500315394L;

        {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    };


    public static <T extends Object> T deserialize(String json, Class<T> clazz)
            throws IOException {
        if (isNullOrEmpty(json))
            return null;

        return mapper.readValue(json, clazz);
    }
}
