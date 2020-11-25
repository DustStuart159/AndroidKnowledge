package com.stuart.anroidvideo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.stuart.anroidvideo.controllers.VideoController;

public class MainAct extends AppCompatActivity {

    private VideoView videoView;
    private int position = 0;
    private VideoController videoController;
    private Button buttonUp;
    private Button buttonRaw;
    private Button buttonLocal;
    private Button buttonURL;
    private Button buttonDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        this.videoView = (VideoView) findViewById(R.id.videoView);
        this.buttonDown = (Button) findViewById(R.id.button_speed_down);
        this.buttonRaw = (Button) findViewById(R.id.button_raw);
        this.buttonLocal = (Button) findViewById(R.id.button_local);
        this.buttonURL = (Button) findViewById(R.id.button_url);
        this.buttonUp = (Button) findViewById(R.id.button_speed_up);

        // Set the media controller buttons
        if (this.videoController == null) {
            this.videoController = new VideoController(MainAct.this, videoView, buttonRaw, buttonLocal, buttonURL, buttonDown, buttonUp);
        }
    }

    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        
        // Store current position.
        videoController.storeCurrentPos(savedInstanceState);
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        videoController.savePos(savedInstanceState);
    }
}