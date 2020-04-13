package com.bandwidth.webrtc.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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
        if (isNullOrWhiteSpace(json))
            return null;

        return mapper.readValue(json, clazz);
    }

    public static boolean isNullOrWhiteSpace(String s) {
        if (s == null) {
            return true;
        }

        int length = s.length();
        if (length > 0) {
            for (int start = 0, middle = length / 2, end = length - 1; start <= middle; start++, end--) {
                if (s.charAt(start) > ' ' || s.charAt(end) > ' ') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
