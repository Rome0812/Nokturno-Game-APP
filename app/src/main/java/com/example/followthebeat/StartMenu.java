package com.example.followthebeat;

import static com.example.followthebeat.MediaPlayers.Videos.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.media3.common.Player;
import androidx.media3.ui.PlayerView;
import com.example.followthebeat.MediaPlayers.Videos;

public class StartMenu extends GlobalSettings{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startmenu);
        PlayerView playerView = findViewById(R.id.player_view_startmenu);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        // Set lang yung video na i-play and yung intro image base sa pinasa na intent.
        Videos.playVideo(this, playerView, 0);

        // Action kung nag click si user anywhere on the screen.
        playerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartMenu.this, StoryFlowController.class));
                finish();
            }
        });

        // Restart video kung nag end
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    player.seekTo(0);
                }
            }
        });
    }

    public void aboutUs (View view) {
        setContentView(R.layout.activity_aboutus);
        player.pause();
    }

    public void aboutUsGoBack (View view) {
        startActivity(new Intent(this, StartMenu.class));
        finish();
    }

}
