package net.asaken1021.websocket_test_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;

import javax.websocket.*;

@ClientEndpoint
public class MainActivity extends AppCompatActivity {

    public static WebSocketContainer webSocketContainer = null;
    public static URI uri = null;
    public static Session session = null;

    EditText addressText;
    Button connectButton;
    TextView statusText;
    EditText messageText;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressText = (EditText) findViewById(R.id.addressText);
        connectButton = (Button) findViewById(R.id.connectButton);
        statusText = (TextView) findViewById(R.id.statusText);
        messageText = (EditText) findViewById(R.id.messageText);
        sendButton = (Button) findViewById(R.id.sendButton);
    }

    public void connect(View v) {
        webSocketContainer = ContainerProvider.getWebSocketContainer();
        uri = URI.create(addressText.getText().toString());

        try {
            session = webSocketContainer.connectToServer(this, uri);
        } catch (javax.websocket.DeploymentException e) {
            Log.e("WebSocket", "DeploymentException");
        } catch (java.io.IOException e) {
            Log.e("WebSocket", "IOException");
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        Log.d("WebSocket", "セッション確立");
        statusText.setText("ステータス：接続済");
    }

    @OnMessage
    public String onMessage(String message) {
        Log.d("WebSocket", "メッセージ受信：" + message);
        return message;
    }

    @OnClose
    public void onClose(Session session) {
        Log.d("WebSocket", "セッション切断");
    }

    @OnError
    public void onError(Session session, Throwable th) {
        Log.d("WebSocket", "セッションエラー");
    }
}