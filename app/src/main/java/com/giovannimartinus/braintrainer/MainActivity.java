package com.giovannimartinus.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    final BrainGame brainGame = new BrainGame();

    public static final String TAG = MainActivity.class.getName();

    //static HashMap<Integer, Button> hashMap = new HashMap<Integer, Button>();
    static ArrayList<Integer> answersList = new ArrayList<Integer>();

    Button answerOne;
    Button answerTwo;
    Button answerThree;
    Button answerFour;
    Button playButton;
    TextView equationTextView;
    TextView countTextView;
    TextView feedbackView;
    TextView scoreTextView;
    GridLayout answerButtonLayout;

    class BrainGame {

        CountDownTimer countDownTimer;

        boolean isActive = false;
        int solution;
        int locationOfCorrectAnswer;
        int score = 0;
        int problems = 0;

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
                answersList.clear();
                Random random = new Random();

                int y;
                locationOfCorrectAnswer = random.nextInt(4);

                for (int i = 0; i < 4; i++) {
                    if (i == locationOfCorrectAnswer) {
                        answersList.add(solution);
                    } else {
                        y = random.nextInt(10000) - 10000;
                        while (y == solution) {
                            y = random.nextInt(10000) - 10000;
                        }
                        answersList.add(y);
                    }
                }
                answerOne.setText(Integer.toString(answersList.get(0)));
                answerTwo.setText(Integer.toString(answersList.get(1)));
                answerThree.setText(Integer.toString(answersList.get(2)));
                answerFour.setText(Integer.toString(answersList.get(3)));
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

        // answer chosen by user
        private void answerButton(View view) {
            if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
                Log.i("Answer was", "correct");
                feedBack(1);
            } else {
                Log.i("Answer was", "wrong");
                feedBack(2);
            }
        }

        // convert millis to seconds and set countTextView
        private void countView(int x) {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(x) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(x));
            String secondsString = String.format("%02d" + "s", seconds);
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
                        feedBack(3);
                    }
                }.start();
            }
        }

        // display correct, wrong, and finally the score
        private void feedBack(int x) {
            if (x == 1) {
                feedbackView.setText("Correct!");
                score++;
                problems++;
                scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(problems));
                equationView();
            } else if (x == 2) {
                feedbackView.setText("Wrong!");
                problems++;
                scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(problems));
                equationView();
            } else if (x == 3) {
                problems = 0;
                score = 0;
                scoreTextView.setText("Score");
                feedbackView.setText("You're score is " + Integer.toString(score) + "/" + Integer.toString(problems));
            }
        }

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
                countTextView.setText("30s");
                buttonEnabled(answerButtonLayout);
                feedBack(3);
                countDownTimer.cancel();
                equationView();
            }
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

        answerOne = (Button) findViewById(R.id.answerOne);
        answerTwo = (Button) findViewById(R.id.answerTwo);
        answerThree = (Button) findViewById(R.id.answerThree);
        answerFour = (Button) findViewById(R.id.answerFour);
        playButton = (Button) findViewById(R.id.playButton);
        answerButtonLayout = (GridLayout) findViewById(R.id.answerButtonLayout);
        equationTextView = (TextView) findViewById(R.id.equationTextView);
        countTextView = (TextView) findViewById(R.id.countTextView);
        feedbackView = (TextView) findViewById(R.id.feedbackView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
    }
}
