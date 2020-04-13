package com.bandwidth.webrtc.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParticipantLeftEvent {
    private String conferenceId;
    private String participantId;
}
