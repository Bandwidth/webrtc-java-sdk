package com.bandwidth.webrtc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WebRtcOptions {

    @JsonProperty
    private String websocketUrl;

    @JsonProperty
    private String sipDestination;
}
