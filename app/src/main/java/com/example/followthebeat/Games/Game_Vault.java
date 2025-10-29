package com.example.followthebeat.Games;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.followthebeat.GlobalSettings;
import com.example.followthebeat.MediaPlayers.SFX;
import com.example.followthebeat.R;
import com.example.followthebeat.StoryFlowController;

public class Game_Vault extends GlobalSettings {
    Handler mHandler = new Handler();

    EditText combination;
    TextView lifeCounter;

    // Game Settings
    int lives = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_vault_codes);
        String clues = getIntent().getStringExtra("clues");

        combination = findViewById(R.id.Combination);
        lifeCounter = findViewById(R.id.lifeCounter);

        combination.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (input.length() == clues.length()) {
                        if (input.equals(clues)) {
                            playSound(SFX.CORRECT);
                            startActivity(new Intent(Game_Vault.this, StoryFlowController.class));
                            finish();
                        } else {
                            playSound(SFX.WRONG);
                            KeyboardHelper.hideKeyboard(Game_Vault.this, combination);
                            Toast.makeText(Game_Vault.this, "Wrong combination", Toast.LENGTH_SHORT).show();
                            shakeEditText(combination);
                            combination.setText("");
                            lives--;
                            lifeCounter.setText(String.valueOf(lives));
                            if (lives == 0) playerDeath();
                        }
                    }
                }
        });
    }

    private void shakeEditText(EditText editText) {
        int delta = 30; // distance ng shake animation
        ObjectAnimator shakeAnimator = ObjectAnimator.ofFloat(editText, "translationX", 0, delta, -delta, delta, -delta, delta, 0);
        shakeAnimator.setDuration(500); // duration
        shakeAnimator.setRepeatCount(0); // numbers ng repeat ng shake
        shakeAnimator.start();
    }

    private void playerDeath() {
        StoryFlowController.currentStory = StoryFlowController.DEATH;
        startActivity(new Intent(Game_Vault.this, StoryFlowController.class)
                .putExtra("restartAt", StoryFlowController.VAULT_START));
        finish();

    }

// Method para ma hide yung keyboard
    public static class KeyboardHelper {
        public static void hideKeyboard(Context context, EditText editText) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
    private void playSound(int sound) {
        SFX.playSound(this, sound);
    }
}