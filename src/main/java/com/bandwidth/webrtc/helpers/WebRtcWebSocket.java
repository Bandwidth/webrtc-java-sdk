package com.bandwidth.webrtc.helpers;

import com.bandwidth.webrtc.WebRtc;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.bandwidth.webrtc.models.*;
import org.kurento.jsonrpc.DefaultJsonRpcHandler;
import org.kurento.jsonrpc.Transaction;
import org.kurento.jsonrpc.message.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class WebRtcWebSocket extends DefaultJsonRpcHandler<JsonObject> {

    private static Logger log = LoggerFactory.getLogger(WebRtcWebSocket.class.getName());

    private Consumer<ParticipantJoinedEvent> onParticipantJoined;

    private Consumer<ParticipantLeftEvent> onParticipantLeft;

    private Consumer<ParticipantPublishedEvent> onParticipantPublished;

    private Consumer<ParticipantUnpublishedEvent> onParticipantUnpublished;

    private Consumer<SubscribeSucceededEvent> onSubscribeSucceeded;

    private Consumer<SubscribeFailedEvent> onSubscribeFailed;

    private Consumer<ParticipantUnsubscribedEvent> onParticipantUnsubscribed;

    private Consumer<ParticipantSubscribedEvent> onParticipantSubscribed;

    private Gson gson = new Gson();


    @Override
    public void handleRequest(Transaction transaction, Request<JsonObject> request) {

        String methodName = request.getMethod();

        switch (methodName) {
            case "participantJoined":
                if (onParticipantJoined != null) {
                    onParticipantJoined.accept(gson.fromJson(request.getParams(), ParticipantJoinedEvent.class));
                }
                break;
            case "participantLeft":
                if (onParticipantLeft != null) {
                    onParticipantLeft.accept(gson.fromJson(request.getParams(), ParticipantLeftEvent.class));
                    return;
                }
                break;
            case "participantPublished":
                if (onParticipantPublished != null) {
                    onParticipantPublished.accept(gson.fromJson(request.getParams(), ParticipantPublishedEvent.class));
                }
                break;
            case "participantUnpublished":
                if (onParticipantUnpublished != null) {
                   onParticipantUnpublished.accept(gson.fromJson(request.getParams(), ParticipantUnpublishedEvent.class));
                }
                break;
            case "subscribeSucceeded":
                if (onSubscribeSucceeded != null) {
                    onSubscribeSucceeded.accept(gson.fromJson(request.getParams(), SubscribeSucceededEvent.class));
                }
                break;
            case "subscribeFailed":
                if (onSubscribeFailed != null) {
                    onSubscribeFailed.accept(gson.fromJson(request.getParams(), SubscribeFailedEvent.class));
                }
                break;
            case "participantUnsubscribed":
                if (onParticipantUnsubscribed != null) {
                    onParticipantUnsubscribed.accept(gson.fromJson(request.getParams(), ParticipantUnsubscribedEvent.class));
                }
                break;
            case "participantSubscribed":
                if (onParticipantSubscribed != null) {
                    onParticipantSubscribed.accept(gson.fromJson(request.getParams(), ParticipantSubscribedEvent.class));
                }
                break;
            default:
                log.error("Failed for " + methodName);
                break;
        }
    }

    public void setOnParticipantJoined(Consumer<ParticipantJoinedEvent> onParticipantJoined) {
        this.onParticipantJoined = onParticipantJoined;
    }

    public void setOnParticipantLeft(Consumer<ParticipantLeftEvent> onParticipantLeft) {
        this.onParticipantLeft = onParticipantLeft;
    }

    public void setOnParticipantPublished(Consumer<ParticipantPublishedEvent> onParticipantPublished) {
        this.onParticipantPublished = onParticipantPublished;
    }

    public void setOnParticipantUnpublished(Consumer<ParticipantUnpublishedEvent> onParticipantUnpublished) {
        this.onParticipantUnpublished = onParticipantUnpublished;
    }

    public void setOnSubscribeSucceeded(Consumer<SubscribeSucceededEvent> onSubscribeSucceeded) {
        this.onSubscribeSucceeded = onSubscribeSucceeded;
    }

    public void setOnSubscribeFailed(Consumer<SubscribeFailedEvent> onSubscribeFailed) {
        this.onSubscribeFailed = onSubscribeFailed;
    }

    public void setOnParticipantUnsubscribed(Consumer<ParticipantUnsubscribedEvent> onParticipantUnsubscribed) {
        this.onParticipantUnsubscribed = onParticipantUnsubscribed;
    }

    public void setOnParticipantSubscribed(Consumer<ParticipantSubscribedEvent> onParticipantSubscribed) {
        this.onParticipantSubscribed = onParticipantSubscribed;
    }
}
