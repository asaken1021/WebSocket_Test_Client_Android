package net.asaken1021.websocket_test_client;

import android.util.Log;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class WebSocketClient {

    public static WebSocketContainer container = null;
    public static URI uri = null;
    public static Session session = null;

    public WebSocketClient() {
        super();
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect(String URL) {

        uri = URI.create(URL);

        try {
            session = container.connectToServer(new WebSocketClient(), uri);
        } catch (javax.websocket.DeploymentException e) {
            Log.e("WebSocketClient_connect", "DeploymentException");
        } catch (java.io.IOException e) {
            Log.e("WebSocketClient_connect", "IOException");
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        Log.d("WebSocketClient", "WebSocket session opened.");
    }

    @OnMessage
    public String onMessage(String message) {
        Log.d("WebSocketClient", "WebSocket message received:" + message);
        return message;
    }

    @OnClose
    public void onClose(Session session){
        Log.d("WebSocketClient", "WebSocket session closed.");
    }

    @OnError
    public void onError(Throwable th){
        Log.e("WebSocketClient", "WebSocket:" + th.getLocalizedMessage());
    }
}