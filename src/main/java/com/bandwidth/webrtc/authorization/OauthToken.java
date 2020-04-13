package com.bandwidth.webrtc.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@Getter
public class OauthToken {

    public OauthToken() {

    }

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private long expiresIn;
}
