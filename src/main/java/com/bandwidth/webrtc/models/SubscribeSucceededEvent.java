package com.bandwidth.webrtc.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SubscribeSucceededEvent {
    private String conferenceId;
    private String participantId;
    private String streamId;
}
