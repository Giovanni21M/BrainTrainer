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

    public static final String TAG = MainActivity.class.getName();

    static HashMap<Integer, Button> hashMap = new HashMap<Integer, Button>();

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
            if (isActive == true) {
                Random random = new Random();

                Button hashMapButton;

                hashMap.put(1, answerOne);
                hashMap.put(2, answerTwo);
                hashMap.put(3, answerThree);
                hashMap.put(4, answerFour);

                int x = random.nextInt(4) + 1;

                for (int i = 0; i < 4; i++) {
                    hashMapButton = hashMap.get(x);
                    if (i == x) {
                        hashMapButton.setText(Integer.toString(solution));
                    } else {
                        int y = random.nextInt(100);
                        hashMapButton.setText(Integer.toString(solution + y));
                    }
                }
            } else {
                answerOne.setText("Answer 1");
                answerTwo.setText("Answer 2");
                answerThree.setText("Answer 3");
                answerFour.setText("Answer 4");
            }
        }

        // generate the equation and set the solution
        private void equationView() {
            if (isActive == true) {
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
            } else {
                equationTextView.setText("Equation");
                solutionView();
            }
        }

        // check if selected answer matches solution
        private void answerCheck(Button button) {
            if (button.getText() == Integer.toString(solution)) {
                Log.i("Correct Answer", "Chosen");
                //feedBack(1);
            } else if (button.getText() != Integer.toString(solution)) {
                Log.i("Wrong Answer", "Chosen");
                //feedBack(2);
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
            if (isActive == false) {
                playButton.setText("Stop");
                buttonEnabled(answerButtonLayout);
                countDown();
                equationView();
            } else {
                isActive = false;
                playButton.setText("Play");
                countTextView.setText("30");
                buttonEnabled(answerButtonLayout);
                countDownTimer.cancel();
                equationView();
            }
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
