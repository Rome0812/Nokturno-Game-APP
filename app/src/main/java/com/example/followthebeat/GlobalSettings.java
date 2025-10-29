package com.example.followthebeat;

import static com.example.followthebeat.MediaPlayers.Videos.player;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.Player;
// Class for global settings. Para ma set lang yung screen setting and on home and back button action sa lahat ng class.
public class GlobalSettings extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set lang ng screen to Immersive Fullscreen.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Pause yung video kung na minimized ni user yung app.
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && player.getPlaybackState() == Player.STATE_READY && player.isPlaying()) {
            player.pause();
        }
    }

    // Resume yung video kung inopen ni user yung app pagkatapos niya i-minimized
    @Override
    protected void onResume() {
        super.onResume();
        if (player != null && (player.getPlaybackState() == Player.STATE_READY || player.getPlaybackState() == Player.STATE_BUFFERING)) {
            player.play();
        }
    }

    // Tanggalin yung action kung nag back press si user.
    @Override
    public void onBackPressed() {
    }
}
