package com.example.followthebeat.MediaPlayers;

import android.content.Context;
import android.net.Uri;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.example.followthebeat.R;
import com.example.followthebeat.StoryFlowController;

// Class for playing videos
public class Videos {
    public static ExoPlayer player;

    public static void playVideo(Context context, PlayerView view, int video) {
        if (player != null) {
            player.stop();
            player.release();
        }

        player = new ExoPlayer.Builder(context).build();
        view.setPlayer(player);
        Uri videoUri;

        switch (video) {
            case StoryFlowController.START:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_story_start);
                break;
            case StoryFlowController.INTRO:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_story_shortintro);
                break;
            case StoryFlowController.DOOR:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_story_opendoor);
                break;
            case StoryFlowController.BASEMENT:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_story_basement_start);
                break;
            case StoryFlowController.BASEMENT_DIALOG:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_story_basement_dialog);
                break;
            case StoryFlowController.BASEMENT_END:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_story_basement_end);
                break;
            case StoryFlowController.CARD_START:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_game_card_start);
                break;
            case StoryFlowController.CARD_INSTRUCTIONS:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_game_card_tutorial);
                break;
            case StoryFlowController.VISION:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_story_visions);
                break;
            case StoryFlowController.VAULT_INTRO:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_game_vault_intro);
                break;
            case StoryFlowController.VAULT_START:
            case StoryFlowController.VAULT_INSTRUCTIONS:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_game_vault_start);
                break;
            case StoryFlowController.VAULT_SPELLBOOK:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_game_vault_spellbook);
                break;
            case StoryFlowController.OUTRO:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_story_outro);
                break;
            case StoryFlowController.END:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_story_end);
                break;
            case StoryFlowController.DEATH:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_playerdeath);
                break;
            default:
                videoUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.video_story_start);
                break;
        }

        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }
}