package com.example.followthebeat;

import static com.example.followthebeat.MediaPlayers.Videos.player;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.ui.PlayerView;

import com.example.followthebeat.Games.Game_Cards;
import com.example.followthebeat.Games.Game_Clues;
import com.example.followthebeat.MediaPlayers.Videos;

// Class para control yung overall flow ng story.
public class StoryFlowController extends GlobalSettings {

    // Settings sa video player.
    public static float videoFastforwardspeed = 4f;
    public static int videoBackwardsec = 5000;

    // Set ng what part ng story yung specific scenes. In order from top to bottom para ma determine yung sequence nila sa storyline.
    public static final int START = 0;
    public static final int INTRO = 1;
    public static final int DOOR = 2;
    public static final int BASEMENT = 3;
    public static final int BASEMENT_DIALOG = 4;
    public static final int BASEMENT_END = 5;
    public static final int CARD_START = 6;
    public static final int CARD_INSTRUCTIONS = 7;
    public static final int CARD_GAME = 8;
    public static final int VISION = 9;
    public static final int VAULT_INTRO = 10;
    public static final int VAULT_START = 11;
    public static final int VAULT_INSTRUCTIONS = 12;
    public static final int VAULT_SPELLBOOK = 13;
    public static final int VAULT_GAME = 14;
    public static final int OUTRO = 15;
    public static final int END = 16;
    public static final int DEATH = -1;

    // Ginagamit sa sequence ng pag play ng story. (Pwede din gamitin sa pang test kung gusto i-test yung specific story).
    public static int currentStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // PART 0
        if (currentStory == START)
            startActivity(new Intent(this, StartMenu.class));
        // PART 1
        else if (currentStory == INTRO)
            startActivity(new Intent(this, Stories.class).putExtra("videoType", INTRO));
        // PART 2
        else if (currentStory == DOOR)
            startActivity(new Intent(this, Dialogs.class).putExtra("videoType", DOOR)
            .putExtra("yes", BASEMENT).putExtra("no",END)
            .putExtra("dialog", R.drawable.dialog_door).putExtra("yesButton", R.drawable.dialog_door_yes).putExtra("noButton", R.drawable.dialog_nogoback));
        // PART 3
        else if (currentStory == BASEMENT)
            startActivity(new Intent(this, Stories.class).putExtra("videoType", BASEMENT));
        // PART 4
        else if (currentStory == BASEMENT_DIALOG)
            startActivity(new Intent(this, Dialogs.class).putExtra("videoType", BASEMENT_DIALOG)
            .putExtra("yes", BASEMENT_END).putExtra("no",END)
            .putExtra("dialog", R.drawable.dialog_basement).putExtra("yesButton", R.drawable.dialog_basement_yes).putExtra("noButton", R.drawable.dialog_nogoback));
        // PART 5
        else if (currentStory == BASEMENT_END)
            startActivity(new Intent(this, Stories.class).putExtra("videoType", BASEMENT_END));
        // PART 6
        else if (currentStory == CARD_START)
            startActivity(new Intent(this, Intros.class).putExtra("videoType", CARD_START)
            .putExtra("intro", R.drawable.game_card_start));
        // PART 7
        else if (currentStory == CARD_INSTRUCTIONS)
            startActivity(new Intent(this, Tutorials.class).putExtra("videoType", CARD_INSTRUCTIONS)
            .putExtra("tutorial", R.drawable.game_card_tutorial));
        // PART 8
        else if (currentStory == CARD_GAME)
            startActivity(new Intent(this, Game_Cards.class));
        // PART 9
        else if (currentStory == VISION)
            startActivity(new Intent(this, Stories.class).putExtra("videoType", VISION));
        // PART 10
        else if (currentStory == VAULT_INTRO)
            startActivity(new Intent(this, Stories.class).putExtra("videoType", VAULT_INTRO));
        // PART 11
        else if (currentStory == VAULT_START)
            startActivity(new Intent(this, Intros.class).putExtra("videoType", VAULT_START)
                    .putExtra("intro", R.drawable.game_vault_start));
        // PART 12
        else if (currentStory == VAULT_INSTRUCTIONS)
            startActivity(new Intent(this, Tutorials.class).putExtra("videoType", VAULT_INSTRUCTIONS)
                    .putExtra("tutorial", R.drawable.game_vault_tutorial));
        // PART 13
        else if (currentStory == VAULT_SPELLBOOK)
            startActivity(new Intent(this, Stories.class).putExtra("videoType", VAULT_SPELLBOOK));
        // PART 14
        else if (currentStory == VAULT_GAME)
            startActivity(new Intent(this, Game_Clues.class));
        // Part 15
        else if (currentStory == OUTRO)
            startActivity(new Intent(this, Stories.class).putExtra("videoType", OUTRO));
        // Part 16
        else if (currentStory == END)
            startActivity(new Intent(this, Stories.class).putExtra("videoType", END));


        // GAME OVER
        else if (currentStory == DEATH) {
            currentStory = getIntent().getIntExtra("restartAt",0);
            startActivity(new Intent(this, Intros.class).putExtra("videoType", DEATH));
            return;
        }

        // Kung lumagpas na sa haba ng story natin, ulit lang sa umpisa.
        else {
            currentStory = START;
            startActivity(new Intent(this, StoryFlowController.class));
            finish();
            return;
        }

        // Set next story para ma play mamaya.
        currentStory++;
        finish();
    }

// Class para ma handle yung starting screens. Yung mga intro ng ibang story or game. Need lang ng click dito para ma next lang sa story
    public static class Intros extends GlobalSettings {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_intros);
            PlayerView playerView = findViewById(R.id.player_view_startmenu);
            ImageView intro = findViewById(R.id.introtext);

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            // Set lang yung video na i-play and yung intro image base sa pinasa na intent.
            intro.setImageResource(getIntent().getIntExtra("intro",0));
            int videoType = getIntent().getIntExtra("videoType",0);
            Videos.playVideo(this, playerView, videoType);

            // Action kung nag click si user anywhere on the screen.
            playerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intros.this, StoryFlowController.class));
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
    }

// Class para mahandle yung video stories. Yung mga video lang pinapakita at may fast forward and backward din na functions.
    public static class Stories extends GlobalSettings {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_stories);
            PlayerView playerView = findViewById(R.id.player_view_stories);
            Button rewindButton = findViewById(R.id.rewindButton);
            Button forwardButton = findViewById(R.id.forwardButton);

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            // Set lang yung video na i-play base sa pinasa na intent.
            int videoType = getIntent().getIntExtra("videoType",0);
            Videos.playVideo(this, playerView, videoType);

            // Next na sa story kung nag end na yung video.
            player.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    if (playbackState == Player.STATE_ENDED) {
                        startActivity(new Intent(Stories.this, StoryFlowController.class));
                        finish();
                    }
                }
            });

            // Listener kung nag long click si user sa fast button. Fast forward lang yung video.
            forwardButton.setOnLongClickListener(v -> {
                player.setPlaybackParameters(new PlaybackParameters(videoFastforwardspeed));
                return true;
            });

            // Listener kung nag release ng click hold si user sa fast button. Revert back lang yung video speed.
            forwardButton.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    player.setPlaybackParameters(new PlaybackParameters(1f));
                }
                return false;
            });

            // Listener kung nag click si user sa rewind button. Go back 5 seconds ago lang yung video
            rewindButton.setOnClickListener(v -> {
                player.seekTo(player.getCurrentPosition() - videoBackwardsec);
            });
        }
    }

// Class para ma handle yung may dialogs na screen. Yung may pipiliin si user. Yung mga dialog and buttons ay naka set based sa pinasa na intent.
    public static class Dialogs extends GlobalSettings {
        int videoType;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dialogs);
            PlayerView playerView = findViewById(R.id.player_view_dialog);
            ImageView dialog = findViewById(R.id.dialog);
            ImageButton yesButton = findViewById(R.id.yesButton);
            ImageButton noButton = findViewById(R.id.noButton);

            // Set yung mga specific dialogs and buttons based sa pinasa na intent extra.
            dialog.setImageResource(getIntent().getIntExtra("dialog", 0));
            yesButton.setImageResource(getIntent().getIntExtra("yesButton", 0));
            noButton.setImageResource(getIntent().getIntExtra("noButton", 0));

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            // Set lang yung video na i-play base sa pinasa na intent.
            videoType = getIntent().getIntExtra("videoType", 0);
            Videos.playVideo(this, playerView, videoType);

            // Hide muna yung buttons
            yesButton.setAlpha(0f);
            noButton.setAlpha(0f);
            dialog.setAlpha(0f);

            // Listener kung nag end na yung animation.
            Animator.AnimatorListener animationListener = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    yesButton.setVisibility(View.VISIBLE);
                    noButton.setVisibility(View.VISIBLE);
                    dialog.setVisibility(View.VISIBLE);
                }
            };

            // Delay muna ng 2 seconds bago magpakita yung buttons.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    yesButton.animate().alpha(1f).setDuration(2000).setListener(animationListener).start();
                    noButton.animate().alpha(1f).setDuration(2000).setListener(animationListener).start();
                    dialog.animate().alpha(1f).setDuration(2000).setListener(animationListener).start();
                }
            }, 2000);

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

        // Action for yes button
        public void yesButton (View view) {
            // Set kung saan story mapupunta si user based sa pinasa na intent sa "yes"
            currentStory = getIntent().getIntExtra("yes",0);
            startActivity(new Intent(Dialogs.this, StoryFlowController.class));
            finish();
        }

        // Action for no button
        public void noButton (View view) {
            // Set kung saan story mapupunta si user based sa pinasa na intent sa "no"
            currentStory = getIntent().getIntExtra("no",0);
            startActivity(new Intent(Dialogs.this, StoryFlowController.class));
            finish();
        }
    }

// Class para ma handle yung Tutorials na screen. Dito yung mag next ng story kung nagclick si user anywhere on the screen.
    public static class Tutorials extends GlobalSettings {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tutorials);
            PlayerView playerView = findViewById(R.id.player_view_tutorials);
            ImageView instructions = findViewById(R.id.introtext);

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            // Set lang yung video na i-play and yung tutorial image base sa pinasa na intent.
            instructions.setImageResource(getIntent().getIntExtra("tutorial",0));
            int videoType = getIntent().getIntExtra("videoType",0);
            Videos.playVideo(this, playerView, videoType);

            // Action kung nag click si user anywhere on the screen.
            playerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Tutorials.this, StoryFlowController.class));
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
    }
}

