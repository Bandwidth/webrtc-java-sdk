package com.bandwidth.webrtc.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParticipantJoinedEvent {
    private String conferenceId;
    private String participantId;
}
