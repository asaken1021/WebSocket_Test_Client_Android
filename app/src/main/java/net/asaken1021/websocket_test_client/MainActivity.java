package net.asaken1021.websocket_test_client;

import java.net.URI;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import static android.provider.CalendarContract.CalendarCache.URI;

public class MainActivity extends AppCompatActivity {

    EditText addressText;
    Button connectButton;
    Button disconnectButton;
    TextView statusText;
    EditText messageText;
    Button sendButton;
    TextView msgText;

    private WebSocketClient client;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addressText = (EditText) findViewById(R.id.addressText);
        connectButton = (Button) findViewById(R.id.connectButton);
        disconnectButton = (Button) findViewById(R.id.disconnectButton);
        statusText = (TextView) findViewById(R.id.statusText);
        messageText = (EditText) findViewById(R.id.messageText);
        sendButton = (Button) findViewById(R.id.sendButton);
        msgText = (TextView) findViewById(R.id.msgText);

        handler = new Handler();
    }

    public void connect(View v){
        try {

            URI uri = new URI(addressText.getText().toString());

            client = new WebSocketClient(uri) {
                @Override
                public void onOpen (ServerHandshake hs){
                    Log.d("WebSocketClient", "onOpen");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            statusText.setText("ステータス：接続中");
                        }
                    });
                }

                @Override
                public void onMessage(final String msg) {
                    Log.d("WebSocketClient", "onMessage: " + msg);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            msgText.setText(msgText.getText() + "\r\n" + msg);
                        }
                    });
                }
                @Override
                public void onError(Exception e){
                    Log.d("WebSocketClient", "onError");
                    e.printStackTrace();
                }

                @Override
                public void onClose(int code, String reason, boolean remote){
                    Log.d("WebSocketClient", "onClose");
                }
            };

            client.connect();
            disconnectButton.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Log.d("WebSocketClient", "error");
            e.printStackTrace();
        }
    }

    public void sendMessage(View v){
        client.send(messageText.getText().toString());
        messageText.setText("");
    }

    public void disconnect(View v){
        client.close();
        statusText.setText("ステータス：切断");
        disconnectButton.setVisibility(View.INVISIBLE);
    }
}