package com.example.mentalhealthapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mentalhealthapp.R;
import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.ConnectorPkg;

import static com.example.mentalhealthapp.utility.Constants.DISPLAY_NAME;


public class VideoActivity extends AppCompatActivity implements Connector.IConnect {

    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private static final int PERMISSION_REQUEST_MIC =0;
    private static final String TAG = "VideoActivity";

    private Button btnConnect;
    private Button btnDisconnect;

    private Connector vc;
    private FrameLayout videoFrame;
    String callID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        checkCameraPermission();
        checkMicPermission();

        btnConnect = findViewById(R.id.btn_vid_connect);
        btnDisconnect = findViewById(R.id.btn_vid_discon);

        ConnectorPkg.setApplicationUIContext(this);
        ConnectorPkg.initialize();
        videoFrame =  (FrameLayout)findViewById(R.id.videoFrame);
        callID = getIntent().getStringExtra("CALL_ID");

        Log.d(TAG, "User display name: " + DISPLAY_NAME);
        Log.d(TAG, "Call ID: " + callID);
    }

    public void Connect(View v){
        btnConnect.setClickable(false);
        btnDisconnect.setClickable(true);

        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default, 15, "warning info@VidyoClient info@VidyoConnector", "", 0);
        vc.showViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());

        String token = "cHJvdmlzaW9uAHVzZXIxQGYxMGI4Yi52aWR5by5pbwA2Mzc2NTU4NjU0OAAAOGIxNjA2ZmFiMTJjMjYwNGExNjdmZGJhZTNlZjQ0YTIyM2ZjMDg0NjczODBjOTA5ZGMwODc5ZWEyNTlkNDQ3OTQ2ODc5MWI3M2NkMjlhNzQ4NmZhYTcwMTA5NjIwM2Ez";
        vc.connect("prod.vidyo.io", token, DISPLAY_NAME, callID, this);
    }

    public void Disconnect(View v){
        vc.disconnect();
        Log.d(TAG, "Phone disconnected " + DISPLAY_NAME);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onSuccess() { }

    public void onFailure(Connector.ConnectorFailReason connectorFailReason) { }

    public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) { }

    private void checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            Log.d("VideoActivity", "Camera request permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
    }
    private void checkMicPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            Log.d("VideoActivity", "Mic request permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PERMISSION_REQUEST_MIC);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("VideoActivity", "Camera granted");
//                findViewById(R.id.btn_vid_connect).setClickable(true);
            }
        }
        if (requestCode == PERMISSION_REQUEST_MIC) {
            // Request for mic permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("VideoActivity", "mic granted");
//                findViewById(R.id.btn_vid_connect).setClickable(true);
            }
        }

    }


}