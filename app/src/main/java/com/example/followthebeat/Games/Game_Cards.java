package com.example.followthebeat.Games;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.followthebeat.GlobalSettings;
import com.example.followthebeat.MediaPlayers.SFX;
import com.example.followthebeat.R;
import com.example.followthebeat.StoryFlowController;

import java.util.HashSet;
import java.util.Random;

public class Game_Cards extends GlobalSettings {
    // Tool Declaration
    Handler mHandler = new Handler(); // Para sa delay na actions (Game speed or flow)
    Random random = new Random();
    CountDownTimer countDownTimer;

    // XML Declaration
    TextView livesText;
    TextView timerText;
    ImageButton[] card;
    int[] cardImages;

    // Other variables na need sa mga ibang classes.
    String sequence = "";
    String userInput;
    int currentSequence, i;

    // Game Settings.
    int maxSequence = 6;
    int gameSpeed = 1000;
    int playerLives = 3;
    long timer = 25000, currentTimer;
    int COLOR_DEFAULT = Color.parseColor("#00FFFFFF");
    int COLOR_CORRECT = Color.parseColor("#B200E676");
    int COLOR_WRONG = Color.parseColor("#B2ED514E");
    int COLOR_FLASH = Color.parseColor("#B2808080");

// Start ng Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_cards);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        // ID ng mga ImageButton
        card = new ImageButton[] {
                findViewById(R.id.button_card1),
                findViewById(R.id.button_card2),
                findViewById(R.id.button_card3),
                findViewById(R.id.button_card4),
        };
        // ID ng mga Image Files
        cardImages = new int[]{
                R.drawable.game_card_card01,
                R.drawable.game_card_card02,
                R.drawable.game_card_card03,
                R.drawable.game_card_card04,
                R.drawable.game_card_card05,
                R.drawable.game_card_card06,
                R.drawable.game_card_card07,
                R.drawable.game_card_card08,
                R.drawable.game_card_card09,
                R.drawable.game_card_card10,
                R.drawable.game_card_card11,
                R.drawable.game_card_card12,
                R.drawable.game_card_card13,
                R.drawable.game_card_card14,
                R.drawable.game_card_card15,
                R.drawable.game_card_card16,
                R.drawable.game_card_card17,
                R.drawable.game_card_card18,
                R.drawable.game_card_card19,
                R.drawable.game_card_card20,
        };

        // ID ng TextViews (Timer and Player Lives)
        livesText = findViewById(R.id.livesText);
        timerText = findViewById(R.id.TimerText);

        // Randomize yung image ng mga cards
        HashSet<Integer> chosenCards = new HashSet<>();
        for (int i = 0; i <= 3; i++) {
            int randomIndex;
            do {
                randomIndex = random.nextInt(cardImages.length);
            } while (chosenCards.contains(randomIndex)); // Check lang kung may kapareho ng image.
            chosenCards.add(randomIndex);
            card[i].setImageResource(cardImages[randomIndex]);
        }
        toggleButtons(false);
        startGame();
    }

// Show ng tutorial
private void startGame() {
    addSequence();
    livesText.setText(String.valueOf(playerLives));
    timerText.setText(String.valueOf(timer / 1000));
    mHandler.postDelayed(startSequence, gameSpeed);
}


// Method ng start timer
    private void startTimer() {
        countDownTimer = new CountDownTimer(timer, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf((int) (millisUntilFinished / 1000)));
                currentTimer = millisUntilFinished;
            }
            // Kung tapos na yung timer then show death dialog and balik sa startmenu
            public void onFinish() {
                playerDeath();
            }
        }.start(); // Start ng countdown timer
    }

// Method ng pag Pause ng timer
    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            timer = currentTimer;
            countDownTimer.cancel();
        }
    }

// Method na nag expand ng button and reset or animation
    private void expandButtonAndReset(ImageButton button) {
        ScaleAnimation anim = new ScaleAnimation(
                1f, 1.2f,
                1f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        anim.setDuration(200); // Duration ng animation
        anim.setFillAfter(true);

        // Animation listener para mareset yung expansion or paglaki ng imagebutton
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Start ng Animation
            }
            // ended ng animation
            @Override
            public void onAnimationEnd(Animation animation) {
                // ended ng Animation , para ma-revert yung button size
                ScaleAnimation revertAnim = new ScaleAnimation(
                        1.2f, 1f,
                        1.2f, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                );
                revertAnim.setDuration(200); // Duration ng animation
                revertAnim.setFillAfter(true);
                button.startAnimation(revertAnim);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                // Pag ulit lang ng Animation
            }
        });
        button.startAnimation(anim);
    }

// Method para sa pag start ng sequence. Dito yung pinapakita sa player yung need sundan.
    private final Runnable startSequence = new Runnable() {
        @Override
        public void run() {
            // Mapakita lang bawat sequence
            if (i < sequence.length()) {
                int c = sequence.charAt(i) - '0';
                changeButtonOverlayColor(COLOR_DEFAULT);
                expandButtonAndReset(card[c]);
                playSound(SFX.CARD[c]);
                i++;
                mHandler.postDelayed(this, gameSpeed); // Loop lang ulit itong method.

            // Kung napakita na lahat, then gagayahin na ng user.
            } else {
                playSound(SFX.TURN);
                changeButtonOverlayColor(COLOR_FLASH);
                mHandler.postDelayed(startUserInput, (long) (500));
            }
        }
    };

// Method para mag start na magpindot ng buttons yung players.
    private final Runnable startUserInput = new Runnable() {
        public void run() {
            startTimer();
            userInput = "";
            currentSequence = 0;
            changeButtonOverlayColor(COLOR_DEFAULT);
            toggleButtons(true);
        }
    };

// Listener sa pagka click ng mga cards. Tatawagin lang yung cardClicked method.
    public void onClickCard0(View view) {
        cardClicked(0);}
    public void onClickCard1(View view) {
        cardClicked(1);}
    public void onClickCard2(View view) {
        cardClicked(2);}
    public void onClickCard3(View view) {
        cardClicked(3);}

// Method kung ano gagawin after ng click sa cards
    private void cardClicked(int c) {
        playSound(SFX.CARD[c]);
        checkSequence(c);
        expandButtonAndReset(card[c]);
    }

// Method para ma check yung bawat input ng user kung tama ba sa sequence.
    private void checkSequence(int x) {
        userInput += x;

        // Kung tama yung input ng user sa lahat ng sequence.
        if (userInput.length() == sequence.length() && userInput.equals(sequence)) {
            toggleButtons(false);
            // Kung last sequence na, then complete na yung player.
            if (sequence.length() == maxSequence) mHandler.postDelayed(playerComplete, (long) (300));
            // Kung di pa last sequence then correct pa lang and next sequence naman.
            else mHandler.postDelayed(playerCorrect, (long) (700));

        // Kung tama yung input ng user bawat sequence.
        } else if (x == sequence.charAt(currentSequence) - '0') {
            currentSequence++;  // Add yung currentSequence para lang ma iterate next yung bawat sequence

        // Hindi tama yung input ng user sa current sequence.
        } else {
            toggleButtons(false);
            playerLives -= 1;
            livesText.setText(String.valueOf(playerLives));
            playSound(SFX.WRONG);
            changeButtonOverlayColor(COLOR_WRONG);
            // Kung wala ng lives yung player, run na yung playerDeath method.
            if (playerLives == 0)  playerDeath();
            // Kung meron pa, then run lang yung playerWrong method.
            else mHandler.postDelayed(playerWrong, (long) (500));
        }
    }

// Method para mag add ng panibagong sequence na need gayahin ng players. Kada stage, padagdag ng padagdag yung sequence.
    private void addSequence() {
        sequence += random.nextInt(4);  // Pick random number 0-3
        i = 0;
    }

// Method kung nagkamali yung player. Reset lang yung value ng userInput at CurrentSequence para ma restart lang yung pag check ng bawat values na ininput ng user.
    private final Runnable playerWrong = new Runnable() {
        @Override
        public void run() {
            userInput = "";
            currentSequence = 0;
            changeButtonOverlayColor(COLOR_DEFAULT);
            toggleButtons(true);
        }
    };

// Method para mag add ng color overlay sa button lahat. Ginagamit lang ito sa mga correct, turn, and wrong warning sa users.
    private void changeButtonOverlayColor(int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        for (ImageButton button : card) {
            button.setImageTintList(colorStateList);
        }
    }

// Method kung tama yung player.
    private final Runnable playerCorrect = new Runnable() {
        @Override
        public void run() {
            gameSpeed = (int) (gameSpeed * 0.85);
            playSound(SFX.CORRECT);
            onPause();
            changeButtonOverlayColor(COLOR_CORRECT);
            addSequence();
            mHandler.postDelayed(startSequence, 700);
        }
    };

// Method kung na complete na ng player yung maximum stage ng sequence
    private final Runnable playerComplete = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(Game_Cards.this, StoryFlowController.class));
            finish();
        }
    };

// Method kung wala ng lives yung player. Magpapakita lang ng dialog.
    private void playerDeath() {
        StoryFlowController.currentStory = StoryFlowController.DEATH;
        startActivity(new Intent(Game_Cards.this, StoryFlowController.class)
                .putExtra("restartAt", StoryFlowController.CARD_START));
        finish();
    }

// Method para sa pag toggle off/on ng buttons.
    public void toggleButtons(boolean isEnabled) {
        for (ImageButton button : card) {
            button.setEnabled(isEnabled);
        }
    }

// Method para mag play ng sounds. Tatawagin yung Sounds class.
    private void playSound(int sound) {
        SFX.playSound(this, sound);
    }
}