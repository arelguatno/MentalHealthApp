package com.example.mentalhealthapp.ui;

import android.Manifest;
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


public class VideoActivity extends AppCompatActivity implements Connector.IConnect {

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    private Button btnConnect;
    private Button btnDisconnect;

    private Connector vc;
    private FrameLayout videoFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Log.d("VideoActivity", "onCreate");

        checkCameraPermission();

        btnConnect = findViewById(R.id.btn_vid_connect);
        btnDisconnect = findViewById(R.id.btn_vid_discon);

        ConnectorPkg.setApplicationUIContext(this);
        ConnectorPkg.initialize();
        videoFrame =  (FrameLayout)findViewById(R.id.videoFrame);

    }

    public void Start(View v){

        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default,  16, "","",0);
        vc.showViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());

    }

    public void Connect(View v){
        btnConnect.setClickable(false);
        btnDisconnect.setClickable(true);

        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default, 15, "warning info@VidyoClient info@VidyoConnector", "", 0);
        vc.showViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());

        String token = "cHJvdmlzaW9uAGphbmVsbGVAZjEwYjhiLnZpZHlvLmlvADYzNzY1MzMyNDI5AAAwMjYyM2U2NWNhMzhkOTY5N2MxMTY0ZTQ4ZTUzNTM5ZDNlZGNiOTFkYzhkMjlmNWYwMGM2NWM0YjAyNzg2OGVmYzlkNjQxMGUyZmM5MDYyMjYyMTQ3MDA5YzM5OGEzZTk=";
        vc.connect("prod.vidyo.io", token, "Janelle", "DemoRoom", this);

//        Janelle is the display name, this can be dynamic
    }

    public void Disconnect(View v){
         vc.disconnect();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("VideoActivity", "Camera granted");
                findViewById(R.id.btn_vid_connect).setClickable(true);
            }
        }
    }
}