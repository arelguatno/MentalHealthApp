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

import java.util.ArrayList;
import java.util.List;

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
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        btnConnect = findViewById(R.id.btn_vid_connect);
        btnDisconnect = findViewById(R.id.btn_vid_discon);

        checkCameraPermission();

        ConnectorPkg.setApplicationUIContext(this);
        ConnectorPkg.initialize();
        videoFrame = (FrameLayout) findViewById(R.id.videoFrame);
        callID = getIntent().getStringExtra("CALL_ID");

        Log.d(TAG, "User display name: " + DISPLAY_NAME);
        Log.d(TAG, "Call ID: " + callID);
    }

    public void Connect(View v) {
        btnConnect.setClickable(false);
        btnDisconnect.setClickable(true);

        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default, 15, "warning info@VidyoClient info@VidyoConnector", "", 0);
        vc.showViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());

        String token = "cHJvdmlzaW9uAHVzZXIxQGYxMGI4Yi52aWR5by5pbwA2Mzc2NTY1ODMzNgAANTE1NTg2ODNjOGEyNDY4YzE2YmUyZWE5NTI0NWE4ZTg3NjNiZjRhZGRjOWRhNjEzZjQxOWU2Y2JjOGU4ZGU5MjU0M2UzNDgyMTdmYzIxZGQ5NDg5NDczOGRmODAzNGYz";
        vc.connect("prod.vidyo.io", token, DISPLAY_NAME, callID, this);
    }

    public void Disconnect(View v) {
        vc.disconnect();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onSuccess() {
    }

    public void onFailure(Connector.ConnectorFailReason connectorFailReason) {
    }

    public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) {
    }

    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};

    private boolean checkCameraPermission() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {  // permissions granted.
                } else {
                    String perStr = "";
                    for (String per : permissions) {
                        perStr += "\n" + per;
                    }   // permissions list of don't granted permission
                }
                return;
            }
        }
    }
}
