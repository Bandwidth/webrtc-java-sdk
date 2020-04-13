package com.bandwidth.webrtc.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class WebRtcCredentials {

    @JsonProperty
    private String accountId;

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;
}
