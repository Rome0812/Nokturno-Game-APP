package com.example.followthebeat.MediaPlayers;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.followthebeat.R;

public class SFX {
    private static MediaPlayer player;

    // Initialize lang ng Names para sa sounds para madali ma identify at matawag itong class. (ex. Sounds.playSound(this, CORRECT);
    public static final int CARD0 = 0;
    public static final int CARD1 = 1;
    public static final int CARD2 = 2;
    public static final int CARD3 = 3;
    public static final int WRONG = 4;
    public static final int TURN = 5;
    public static final int CORRECT = 6;
    public static final int BOOK = 7;
    public static final int[] CARD = {CARD0, CARD1, CARD2, CARD3};

    public static void playSound(Context context, int sound) {
        int soundResource;
        switch (sound) {
            case CARD0:
                soundResource = R.raw.sfx_game_card_card01;
                break;
            case CARD1:
                soundResource = R.raw.sfx_game_card_card02;
                break;
            case CARD2:
                soundResource = R.raw.sfx_game_card_card03;
                break;
            case CARD3:
                soundResource = R.raw.sfx_game_card_card04;
                break;
            case WRONG:
                soundResource = R.raw.sfx_game_card_wrong;
                break;
            case TURN:
                soundResource = R.raw.sfx_game_card_turn;
                break;
            case CORRECT:
                soundResource = R.raw.sfx_game_card_correct;
                break;
            case BOOK:
                soundResource = R.raw.sfx_game_book_swipe;
                break;
            default:
                soundResource = R.raw.sfx_game_card_correct;
                break;
        }

        // Kung may nag play pa, cancel na yun then play yung current sound.
        if (player != null) {
            player.release();
        }

        // Play na kung ano naka set sa soundResource.
        player = MediaPlayer.create(context, soundResource);
        if (player != null) {
            player.setOnCompletionListener(mp -> {
                mp.release();
                player = null;
            });
            player.start();
        }
    }
}