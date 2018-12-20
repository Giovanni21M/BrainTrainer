package com.giovannimartinus.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final BrainGame brainGame = new BrainGame();

    Button answerOne;
    Button answerTwo;
    Button answerThree;
    Button answerFour;
    Button playButton;
    TextView equationTextView;
    TextView countTextView;
    GridLayout answerButtonLayout;

    class BrainGame {

        CountDownTimer countDownTimer;

        boolean isActive = false;
        int solution;

        // enable or disable answer buttons
        private void buttonEnabled(GridLayout gridLayout) {
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                View child = gridLayout.getChildAt(i);
                if (child.isEnabled()) {
                    child.setEnabled(false);
                } else {
                    child.setEnabled(true);
                }
            }
        }

        // set the solution and other integers to a random answerButton
        private void solutionView() {
            Random random = new Random();

            HashMap<Integer, Button> hashMap = new HashMap<Integer, Button>();
            Button hashMapButton;

            hashMap.put(0, answerOne);
            hashMap.put(1, answerTwo);
            hashMap.put(2, answerThree);
            hashMap.put(3, answerFour);

            int x = random.nextInt(3);

            for (int i = 0; i < 3; i++) {
                if (i == x) {
                    hashMapButton = hashMap.get(x);
                    hashMapButton.setText(solution);
                } else {
                    hashMapButton = hashMap.get(x);
                    int y = random.nextInt(100);
                    hashMapButton.setText(solution + y);
                }
            }
        }

        // generate the equation and set the solution
        private void equationView() {
            Random random = new Random();

            int x = random.nextInt(100);
            int y = random.nextInt(100);

            String operators = "+-*/";
            String operator = Character.toString(operators.charAt(random.nextInt(operators.length())));

            String xyz = x + " " + operator + " " + y;
            equationTextView.setText(xyz);

            switch (operator) {
                case "+":
                    solution = x + y;
                    break;
                case "-":
                    solution = x - y;
                    break;
                case "*":
                    solution = x * y;
                    break;
                case "/":
                    solution = x / y;
                    break;
            }

            solutionView();
        }

        /* check if selected answer matches solution
        private void answerCheck(Button button) {
            if (button.getText() == Integer.toString(solution)) {
                feedBack(1);
            } else if (button.getText() != Integer.toString(solution)) {
                feedBack(2);
            }
        }*/

        /* answer chosen by user
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
        }*/

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
                        countTextView.setText("0");
                        buttonEnabled(answerButtonLayout);
                    }
                }.start();
            }
        }

        /* display correct, wrong, and finally the score
        private void feedBack(int x) {}*/

        // start the game
        private void hitPlay() {
            buttonEnabled(answerButtonLayout);
            countDown();
            equationView();
        }

    }

    /* determine which answer is chosen
    public void answerClick(View view) {
        brainGame.answerButton(view);
    }*/

    // play game
    public void playClick(View view) {
        brainGame.hitPlay();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerOne = (Button) findViewById(R.id.answerOne);
        answerTwo = (Button) findViewById(R.id.answerTwo);
        answerThree = (Button) findViewById(R.id.answerThree);
        answerFour = (Button) findViewById(R.id.answerFour);
        answerButtonLayout = (GridLayout) findViewById(R.id.answerButtonLayout);
        equationTextView = (TextView) findViewById(R.id.equationTextView);
        countTextView = (TextView) findViewById(R.id.countTextView);
        playButton = (Button) findViewById(R.id.playButton);
    }
}
