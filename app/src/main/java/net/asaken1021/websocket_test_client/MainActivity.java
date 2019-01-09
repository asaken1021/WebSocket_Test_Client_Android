package net.asaken1021.websocket_test_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static WebSocketClient client = null;

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
        client = new WebSocketClient();

        client.connect(addressText.getText().toString());
    }
}