package com.bandwidth.webrtc.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateParticipantResponse {
    private String conferenceId;
    private String participantId;
    private String deviceToken;
}
