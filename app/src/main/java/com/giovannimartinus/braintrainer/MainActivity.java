package com.giovannimartinus.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final BrainGame brainGame = new BrainGame();

    Button playButton;
    TextView countTextView;
    GridLayout answerButtonLayout;

    class BrainGame {

        CountDownTimer countDownTimer;

        boolean isActive = false;
        int solution;

        // enable or disable answer buttons
        private void buttonEnabled(GridLayout gridLayout) {
            ArrayList<View> gridLayoutButtons = gridLayout.getTouchables();

            for (View view : gridLayoutButtons) {
                if(view instanceof Button && view.isEnabled()) {
                    view.setEnabled(false);
                } else if (view instanceof  Button && view.isEnabled() == false){
                    view.setEnabled(true);
                }
            }
        }

        // generate the equation
        private void equationView() {
            Random random = new Random();

            int x = random.nextInt(100);
            int z = random.nextInt(100);

            String operators = "+-*/";
            String xyz = x + Character.toString(operators.charAt(random.nextInt(operators.length()))) + z;

            solution = Integer.parseInt(xyz);
        }

        // check if selected answer matches solution
        private void answerCheck(Button button) {
            if (button.getText() == Integer.toString(solution)) {
                feedBack(1);
            } else if (button.getText() != Integer.toString(solution)) {
                feedBack(2);
            }
        }

        // answer chosen by user
        private void answerButton(View view) {
            Button clickedButton = (Button) view;

            switch (clickedButton.getId()) {
                case R.id.answerOne:
                    answerCheck(clickedButton); // a view may need to be passed by specific id instead
                    break;
                case R.id.answerTwo:
                    answerCheck(clickedButton); // a view may need to be passed by specific id instead
                    break;
                case R.id.answerThree:
                    answerCheck(clickedButton); // a view may need to be passed by specific id instead
                    break;
                case R.id.answerFour:
                    answerCheck(clickedButton); // a view may need to be passed by specific id instead
                    break;
            }
        }

        // convert millis to seconds and set countTextView
        private void countView(int x) {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(x) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(x));
            String secondsString = String.format("%02d", seconds);
            countTextView.setText(secondsString);
        }

        // count down timer function
        private void countDown() {
            if (isActive == false) {
                isActive = true;
                countDownTimer = new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int millisInt = (int) millisUntilFinished;
                        countView(millisInt);
                    }

                    @Override
                    public void onFinish() {
                        isActive = false;
                        buttonEnabled(answerButtonLayout);
                    }
                }.start();
            }
        }

        // display correct, wrong, and finally the score
        private void feedBack(int x) {}

        // start the game
        private void hitPlay() {
            buttonEnabled(answerButtonLayout);
            countDown();
            equationView();
        }

    }

    // determine which answer is chosen
    public void answerClick(View view) {
        brainGame.answerButton(view);
    }

    // play game
    public void playClick(View view) {
        brainGame.hitPlay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerButtonLayout = (GridLayout) findViewById(R.id.answerButtonLayout);
        countTextView = (TextView) findViewById(R.id.countTextView);
        playButton = (Button) findViewById(R.id.playButton);
    }
}
