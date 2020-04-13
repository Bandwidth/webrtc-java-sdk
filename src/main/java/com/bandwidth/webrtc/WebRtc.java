package com.bandwidth.webrtc;

import com.bandwidth.webrtc.authorization.OauthToken;
import com.bandwidth.webrtc.authorization.WebRtcAuthorizer;
import com.bandwidth.webrtc.authorization.WebRtcCredentials;
import com.bandwidth.webrtc.exceptions.HttpException;
import com.bandwidth.webrtc.helpers.WebRtcWebSocket;
import com.bandwidth.webrtc.models.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.kurento.jsonrpc.client.JsonRpcClient;
import org.kurento.jsonrpc.client.JsonRpcClientNettyWebSocket;

import java.io.IOException;
import java.util.function.Consumer;


public class WebRtc {


    private Long tokenExpiration = null;

    private String authToken = null;

    private JsonRpcClient client;

    private WebRtcWebSocket socketListener = new WebRtcWebSocket();;

    private Gson gson = new Gson();

    public WebRtc() {

    }

    public void connect(WebRtcCredentials creds) throws IOException, HttpException, Exception {

        WebRtcOptions options = WebRtcOptions.builder()
                .websocketUrl("wss://server-rtc.rand.bandwidth.com")
                .sipDestination("+19192892727")
                .build();

        _connect(creds, options);


    }

    private void _connect(WebRtcCredentials creds, WebRtcOptions options) throws IOException, HttpException, Exception {

        if (this.authToken == null || this.tokenExpiration == null || this.tokenExpiration < System.currentTimeMillis()) {
            OauthToken token = WebRtcAuthorizer.getClientCredentials(creds);
            this.authToken = token.getAccessToken();
            this.tokenExpiration = System.currentTimeMillis() + (token.getExpiresIn() * 1000);
        }

        String webSocketUrl = String.format("%s/v1/?at=c&auth=%s&accountId=%s", options.getWebsocketUrl(), this.authToken, creds.getAccountId());

        this.client = new JsonRpcClientNettyWebSocket(webSocketUrl);

        this.client.connect();

        this.client.setServerRequestHandler(socketListener);

    }

    public StartConferenceResponse startConference() {

        try {
            JsonElement je = this.client.sendRequest("startConference", new JsonObject());
            return gson.fromJson(je, StartConferenceResponse.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }


    }

    public void endConference(String conferenceId) {

        JsonObject jo = new JsonObject();

        JsonElement jsonString = new JsonPrimitive(conferenceId);

        jo.add("conferenceId", jsonString);

        try {
            JsonElement je = this.client.sendRequest("endConference", jo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    public CreateParticipantResponse createParticipant(String conferenceId) {
        JsonObject jo = new JsonObject();
        JsonElement jsonString = new JsonPrimitive(conferenceId);
        jo.add("conferenceId", jsonString);
        try {
            JsonElement je = this.client.sendRequest("createParticipant", jo);
            return gson.fromJson(je, CreateParticipantResponse.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void removeParticipant(String conferenceId, String participantId) {
        JsonObject jo = new JsonObject();
        jo.add("conferenceId", new JsonPrimitive(conferenceId));
        jo.add("participantId", new JsonPrimitive(participantId));
        try {
            JsonElement je = this.client.sendRequest("removeParticipant", jo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void subscribe(String conferenceId, String participantId, String streamId) {
        JsonObject jo = new JsonObject();
        JsonElement conferenceIdJson = new JsonPrimitive(conferenceId);
        JsonElement participantIdJson = new JsonPrimitive(participantId);
        JsonElement streamIdJson = new JsonPrimitive(streamId);
        jo.add("conferenceId", conferenceIdJson);
        jo.add("participantId", participantIdJson);
        jo.add("streamId", streamIdJson);
        try {
            JsonElement je = this.client.sendRequest("subscribeParticipant", jo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void unsubscribe(String conferenceId, String participantId, String streamId) {
        JsonObject jo = new JsonObject();
        JsonElement conferenceIdJson = new JsonPrimitive(conferenceId);
        JsonElement participantIdJson = new JsonPrimitive(participantId);
        JsonElement streamIdJson = new JsonPrimitive(streamId);
        jo.add("conferenceId", conferenceIdJson);
        jo.add("participantId", participantIdJson);
        jo.add("streamId", streamIdJson);
        try {
            JsonElement je = this.client.sendRequest("unsubscribeParticipant", jo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void unpublish(String conferenceId, String participantId, String streamId) {
        JsonObject jo = new JsonObject();
        JsonElement conferenceIdJson = new JsonPrimitive(conferenceId);
        JsonElement participantIdJson = new JsonPrimitive(participantId);
        JsonElement streamIdJson = new JsonPrimitive(streamId);
        jo.add("conferenceId", conferenceIdJson);
        jo.add("participantId", participantIdJson);
        jo.add("streamId", streamIdJson);
        try {
            JsonElement je = this.client.sendRequest("unpublish", jo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void disconnect() throws IOException {
        this.client.close();
    }

    public void setOnParticipantJoined(Consumer<ParticipantJoinedEvent> onParticipantJoined) {
        this.socketListener.setOnParticipantJoined(onParticipantJoined);
    }

    public void setOnParticipantLeft(Consumer<ParticipantLeftEvent> onParticipantLeft) {
        this.socketListener.setOnParticipantLeft(onParticipantLeft);
    }

    public void setOnParticipantPublished(Consumer<ParticipantPublishedEvent> onParticipantPublished) {
        this.socketListener.setOnParticipantPublished(onParticipantPublished);
    }

    public void setOnParticipantUnpublished(Consumer<ParticipantUnpublishedEvent> onParticipantUnpublished) {
        this.socketListener.setOnParticipantUnpublished(onParticipantUnpublished);
    }

    public void setOnSubscribeSucceeded(Consumer<SubscribeSucceededEvent> onSubscribeSucceeded) {
        this.socketListener.setOnSubscribeSucceeded(onSubscribeSucceeded);
    }

    public void setOnSubscribeFailed(Consumer<SubscribeFailedEvent> onSubscribeFailed) {
        this.socketListener.setOnSubscribeFailed(onSubscribeFailed);
    }

    public void setOnParticipantUnsubscribed(Consumer<ParticipantUnsubscribedEvent> onParticipantUnsubscribed) {
        this.socketListener.setOnParticipantUnsubscribed(onParticipantUnsubscribed);
    }
}
