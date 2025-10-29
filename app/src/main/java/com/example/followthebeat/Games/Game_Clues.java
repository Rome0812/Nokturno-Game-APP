package com.example.followthebeat.Games;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import java.util.Random;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.followthebeat.GlobalSettings;
import com.example.followthebeat.MediaPlayers.SFX;
import com.example.followthebeat.R;

public class Game_Clues extends GlobalSettings {

    // Random clue declaration
    Random random = new Random();
    String clues = String.valueOf(random.nextInt(9000) + 1000); // Declare agad ng random clue pagka start ng activity.

    float x1, x2;
    int page;

    // XML Declaration
    ConstraintLayout bg;
    int[] background;
    TextView[] textClues;
    View cover;

    // Settings for fade animations
    AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
    AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);

    // Game Settings
    int transitionDuration = 1000;
    float swipeDistance = 300;

    // On create ng activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_vault_clues);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        bg = findViewById(R.id.combinelayout);

        // Set na ng duration sa pag transition
        fadeIn.setDuration(transitionDuration);
        fadeOut.setDuration(transitionDuration);

        // Initialize na yung variables para sa mga xml
        cover = findViewById(R.id.views);
        textClues = new TextView[]{
                findViewById(R.id.textView_clue1),
                findViewById(R.id.textView_clue2),
                findViewById(R.id.textView_clue3),
                findViewById(R.id.textView_clue4)};
        background = new int[]{
                R.drawable.game_vault_clue01,
                R.drawable.game_vault_clue02,
                R.drawable.game_vault_clue03,
                R.drawable.game_vault_clue04};

        // I set na yung first clue and background pag ka start ng activity
        textClues[page].setText(String.valueOf(clues.charAt(page)));
        bg.setBackgroundResource(background[page]);


    }

    // Listener para sa pag swipe
    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            // Set yung distance value kung saan nag touch down yung user
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                break;
            // Set yung distance value kung saan nag touch up yung user (binitawan yung touch). Dito narin mag determine kung paano nag swipe yung user.
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                float swipeDist = x2 - x1;
                int prevPage = 0;   // Ginagamit para mag clear up ng previous text.
                // Kung pasok siya sa swipe distance na need para ma next or prev. Kung di naman, edi wala.
                if (Math.abs(swipeDist) > swipeDistance) {
                    if (x1 < x2) {  // Right Swipe
                        if (page > 0) { // Kung more than 0 pa yung current page, then pwede pa mag previous page.
                            prevPage = page;
                            playSound(SFX.BOOK);
                            page--;
                        }
                    } else if (x1 > x2) { // Left Swipe
                        if (page < textClues.length) { // Kung may natitira pang clues, then pwede pa mag next page.
                            prevPage = page;
                            playSound(SFX.BOOK);
                            page++;
                        }
                    }
                    // If Last page then punta na sa vault class
                    if (page == textClues.length) {
                        // Punta na sa vault class kasama ng value ng clues.
                        startActivity(new Intent(this, Game_Vault.class).putExtra("clues", clues));
                        page--;
                        // Kung di pa last page then set na yung background and text na naka depende kung anong value na ng page. (ex. Page=1 then background[1],text[1], etc.)
                    } else {
                        cover.startAnimation(fadeIn);
                        cover.startAnimation(fadeOut);
                        textClues[prevPage].setText("");
                        textClues[page].setText(String.valueOf(clues.charAt(page)));
                        bg.setBackgroundResource(background[page]);
                    }
                }
                break;
        }
        return false;
    }
    private void playSound(int sound) {
        SFX.playSound(this, sound);
    }
}