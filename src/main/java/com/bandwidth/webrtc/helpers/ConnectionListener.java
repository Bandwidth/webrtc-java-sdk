package com.bandwidth.webrtc.helpers;

import org.kurento.jsonrpc.client.JsonRpcWSConnectionListener;

import java.util.function.Consumer;

public class ConnectionListener implements JsonRpcWSConnectionListener
{
    private Runnable disconnectedHandler;

    @Override
    public void connected() {

    }

    @Override
    public void connectionFailed() {

    }

    @Override
    public void disconnected() {

    }

    @Override
    public void reconnected(boolean sameServer) {

    }

    @Override
    public void reconnecting() {
        if (this.disconnectedHandler != null) {
            this.disconnectedHandler.run();
        }
    }

    public void setDisconnectedHandler(Runnable callback) {
        this.disconnectedHandler = callback;
    }
}
