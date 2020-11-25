package com.stuart.anroidvideo.controllers;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.stuart.anroidvideo.utils.VideoViewUtils;

public class VideoController extends MediaController {
    private float videoSpeed = 1;
    private VideoView videoView;
    private MediaPlayer mediaPlayer;
    private int position = 0;
    private Button buttonUp;
    private Button buttonRaw;
    private Button buttonLocal;
    private Button buttonURL;
    private Button buttonDown;

    public VideoController(Context context, VideoView videoView, Button buttonRaw, Button buttonLocal, Button buttonURL, Button buttonDown, Button buttonUp) {
        super(context);
        this.buttonDown=buttonDown;
        this.buttonRaw=buttonRaw;
        this.buttonLocal=buttonLocal;
        this.buttonURL=buttonURL;
        this.videoView= videoView;
        this.buttonUp=buttonUp;

        // init video view
        initVideoView();

    }

    private void initVideoView() {
        // Set the videoView that acts as the anchor for the MediaController.
        this.setAnchorView(videoView);

        // Set MediaController for VideoView
        this.videoView.setMediaController(this);

        // When the video file ready for playback.
        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                    VideoController.this.mediaPlayer = mediaPlayer = mediaPlayer;
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        VideoController.this.setAnchorView(videoView);
                    }
                });
            }
        });

        this.buttonRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "myvideo.mp4" in directory "raw".
                String resName = VideoViewUtils.RAW_VIDEO_SAMPLE;
                VideoViewUtils.playRawVideo(VideoController.this.getContext(), videoView, resName);
            }
        });

        this.buttonLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String localPath = VideoViewUtils.LOCAL_VIDEO_SAMPLE;
                VideoViewUtils.playLocalVideo(VideoController.this.getContext(), videoView, localPath);
            }
        });

        this.buttonURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoURL = VideoViewUtils.URL_VIDEO_SAMPLE;
                VideoViewUtils.playURLVideo(VideoController.this.getContext(), videoView, videoURL);
            }
        });

        this.buttonDown.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                videoSpeed -= videoSpeed > 0.25 ? 0.25 : 0;
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(videoSpeed));
            }
        });

        this.buttonUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                videoSpeed += 0.25;
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(videoSpeed));
            }
        });
    }

    public void storeCurrentPos(Bundle savedInstanceState){
        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }

    public void savePos(Bundle savedInstanceState){
        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }
}
