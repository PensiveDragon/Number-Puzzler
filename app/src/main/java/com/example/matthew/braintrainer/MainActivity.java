package com.example.matthew.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timerView;
    TextView questionBox;
    TextView scoreView;
    TextView highScoreBox;
    TextView feedbackBoxCorrect;
    TextView feedbackBoxWrong;
    Button startButton;
    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonFour;
    CountDownTimer countDownTimer;
    int x;
    int y;
    int correctAnswer;
    int scoreCorrect;
    int scoreTries;
    int highScoreCorrect;
    int highScoreTries = 1;
    int scoreRatio;
    int highScoreRatio;
    List incorrectAnswers;



    public void startGame(View view){

        startButton.setVisibility(View.INVISIBLE);
        timerView.setVisibility(View.VISIBLE);
        questionBox.setVisibility(View.VISIBLE);
        scoreView.setVisibility(View.VISIBLE);
        buttonOne.setVisibility(View.VISIBLE);
        buttonTwo.setVisibility(View.VISIBLE);
        buttonThree.setVisibility(View.VISIBLE);
        buttonFour.setVisibility(View.VISIBLE);
        generateQuestion();
        generateAnswers();
        buttonOne.setClickable(true);
        buttonTwo.setClickable(true);
        buttonThree.setClickable(true);
        buttonFour.setClickable(true);

        countDownTimer = new CountDownTimer(30000, 500) {
            @Override
            public void onTick(long l) {

                updateTimer((int) l / 1000);

            }

            @Override
            public void onFinish() {

                Log.i("Finished", "Timer all done.");
                endScreen();


            }
        }.start();
        Log.i("Timer", "Started.");

    }

    public void updateTimer(int secondsLeft) {

        int seconds = secondsLeft % 60;

        timerView.setText(String.format("%02d"+"s", seconds));

    }

    public void updateScore(){

        scoreView.setText(String.format(Integer.toString(scoreCorrect)) + "/" + Integer.toString(scoreTries));

    }

    public void generateQuestion() {

        Random rand = new Random();
        x = rand.nextInt(20)+1;
        y = rand.nextInt(20)+1;

        Log.i("Random X", Integer.toString(x));
        Log.i("Random Y", Integer.toString(y));

        questionBox.setText(String.format(Integer.toString(x) + " x " + Integer.toString(y)));

    }

    public void generateAnswers() {

        correctAnswer = x*y;
        Log.i("Answer", Integer.toString(correctAnswer));

        incorrectAnswers = new ArrayList();

        wrongAnswerGenerator();

        /*
        incorrectAnswers.add(3);
        incorrectAnswers.add(6);
        incorrectAnswers.add(4);
        */

        Log.d("Incorrect Answers", incorrectAnswers.toString());


        List options = new ArrayList();
        options.add(correctAnswer);
        options.addAll(incorrectAnswers);
        Collections.shuffle(options);

        Log.d("All Options", options.toString());

        buttonOne.setText(String.format(options.get(0).toString()));
        buttonOne.setTag(String.format(options.get(0).toString()));
        buttonTwo.setText(String.format(options.get(1).toString()));
        buttonTwo.setTag(String.format(options.get(1).toString()));
        buttonThree.setText(String.format(options.get(2).toString()));
        buttonThree.setTag(String.format(options.get(2).toString()));
        buttonFour.setText(String.format(options.get(3).toString()));
        buttonFour.setTag(String.format(options.get(3).toString()));
    }

    public void wrongAnswerGenerator () {

        int i = 0;
        int m;
        int n;
        int z;

        //Log.i("List size", Integer.toString(incorrectAnswers.size()));

        int max = 3;

        while (i < max) {

            Log.i("List size", Integer.toString(incorrectAnswers.size()));

            Random rand = new Random();
            m = rand.nextInt(3);

            //Log.i("Rand m", Integer.toString(m) + "   (0 = Modify X / 1 = Modify Y)");

            if (m == 0) {

                z = rand.nextInt(7)-3;
                //Log.i("Rand z", Integer.toString(z));

                n = (x+z)*y;

                Log.i("Wrong Answer Attempt V1", Integer.toString(n));

            } if (m == 1) {

                z = rand.nextInt(7)-3;
                //Log.i("Rand z", Integer.toString(z));

                n = x*(y+z);

                Log.i("Wrong Answer Attempt V2", Integer.toString(n));

            } else {

                z = rand.nextInt(5)-2;

                n = (x*y)+(z*10);

                Log.i("Wrong Answer Attempt V3", Integer.toString(n));
                Log.i("Wrong Answer Attempt V3", Integer.toString(n) + " = (" + Integer.toString(x) + "*" + Integer.toString(y) + ") + (" + Integer.toString(z) + "*10)");

            }

            if (n != correctAnswer) {

                if (n > 0) {

                    if (!incorrectAnswers.contains(n)) {

                        incorrectAnswers.add(n);
                        i = incorrectAnswers.size();
                        Log.i("Wrong answer", Integer.toString(n) + " added.");

                    }

                }

            }

        }

        Log.i("Broken out of loop", "Woo.");

    }

    public void checkAnswer(View view) {

        Button buttonPressed = (Button) view;

        Log.i("Button pressed", buttonPressed.getTag().toString());

        int answer = Integer.valueOf(buttonPressed.getTag().toString());

        if (correctAnswer == answer) {

            Log.i("Answer Chosen", "Correct!");
            scoreCorrect++;
            scoreTries++;
            updateScore();
            generateQuestion();
            generateAnswers();
            feedbackBoxCorrect.setVisibility(View.VISIBLE);
            feedbackBoxWrong.setVisibility(View.INVISIBLE);



        } else {

            Log.i("Answer Chosen", "Wrong!");
            scoreTries++;
            updateScore();
            generateQuestion();
            generateAnswers();
            feedbackBoxCorrect.setVisibility(View.INVISIBLE);
            feedbackBoxWrong.setVisibility(View.VISIBLE);


        }

    }

    public void endScreen () {

        buttonOne.setClickable(false);
        buttonTwo.setClickable(false);
        buttonThree.setClickable(false);
        buttonFour.setClickable(false);
        timerView.setVisibility(View.INVISIBLE);
        questionBox.setVisibility(View.INVISIBLE);
        scoreView.setVisibility(View.INVISIBLE);
        buttonOne.setVisibility(View.INVISIBLE);
        buttonTwo.setVisibility(View.INVISIBLE);
        buttonThree.setVisibility(View.INVISIBLE);
        buttonFour.setVisibility(View.INVISIBLE);
        feedbackBoxCorrect.setVisibility(View.INVISIBLE);
        feedbackBoxWrong.setVisibility(View.INVISIBLE);

        checkHighScore();
        updateScore();

        countDownTimer = new CountDownTimer(3000, 500) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                Log.i("Finished", "Score timer all done.");
                resetGame();


            }
        }.start();
        Log.i("Timer", "Score timer started.");


    }

    public void resetGame () {

        startButton.setVisibility(View.VISIBLE);
        timerView.setText(String.format("30s"));
        scoreCorrect = 0;
        scoreTries = 0;





    }

    public void checkHighScore() {

        Log.i("Final score was ", Integer.toString(scoreCorrect) + " / " + Integer.toString(scoreTries));

        if (scoreTries > 0) {

            scoreRatio = ((scoreCorrect * 100) / scoreTries);
            highScoreRatio = ((highScoreCorrect * 100) / highScoreTries);

            Log.i("Score ratio is ", Integer.toString(scoreRatio));
            Log.i("High Score ratio is ", Integer.toString(highScoreRatio));

            if ((scoreRatio) > 50) {

                Log.i("We made it here", "Past 50%");

                if ((scoreCorrect > highScoreCorrect) || (scoreCorrect == highScoreCorrect) && ((scoreRatio) > (highScoreRatio))) {

                    Log.i("We made it here", "New High Score");

                    highScoreCorrect = scoreCorrect;
                    highScoreTries = scoreTries;
                    highScoreBox.setVisibility(View.VISIBLE);
                    highScoreBox.setText("High Score: " + String.format(Integer.toString(highScoreCorrect) + " / " + Integer.toString(highScoreTries)));
                    Toast.makeText(this, "Congratulations! New High Score!", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(this, "Not a high score - try again!", Toast.LENGTH_SHORT).show();

                }

            } else {

                Toast.makeText(this, "Below 50% correct, take it slower and try again!", Toast.LENGTH_SHORT).show();

            }
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerView = findViewById(R.id.timerView);
        startButton = findViewById(R.id.startButton);
        scoreView = findViewById(R.id.scoreView);
        questionBox = findViewById(R.id.questionBox);
        highScoreBox = findViewById(R.id.highScoreBox);
        feedbackBoxCorrect = findViewById(R.id.feedbackBoxCorrect);
        feedbackBoxWrong = findViewById(R.id.feedbackBoxWrong);
        buttonOne = findViewById(R.id.buttonOne);
        buttonOne.setClickable(false);
        buttonTwo = findViewById(R.id.buttonTwo);
        buttonTwo.setClickable(false);
        buttonThree = findViewById(R.id.buttonThree);
        buttonThree.setClickable(false);
        buttonFour = findViewById(R.id.buttonFour);
        buttonFour.setClickable(false);

    }
}

// start button
// 30 second timer
// randomly generating question phrase
// score tracking
// randomly generating answers including one correct answer