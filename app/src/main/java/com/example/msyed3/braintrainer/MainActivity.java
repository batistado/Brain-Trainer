package com.example.msyed3.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Random random;
    private int[] positions;
    int correctPosition;
    int score;
    int numberOfQuestions;
    TextView timerTextView;
    Button playAgainButton;
    TextView resultView;
    boolean isActive = false;

    public void startGame(View view) {
        Button startButton = (Button) view;
        startButton.setVisibility(View.INVISIBLE);
        ConstraintLayout constraintLayout = findViewById(R.id.gameConstraintView);
        constraintLayout.setVisibility(View.VISIBLE);
        isActive = true;
        resetGame();
    }

    public void resetGame() {
        isActive = true;
        score = 0;
        numberOfQuestions = 0;
        nextQuestion();
        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.format("%02d s", millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                resultView.setText("Your score: " + Integer.toString(score));
                isActive = false;
            }
        }.start();
    }

    public void playAgain(View view) {
        playAgainButton.setVisibility(View.INVISIBLE);
        resetGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new Random();
        positions = new int[4];
        score = 0;
        numberOfQuestions = 0;
        timerTextView = findViewById(R.id.timerTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
    }

    public void nextQuestion(){
        numberOfQuestions++;

        TextView questionTextView = findViewById(R.id.questionTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));

        int a = random.nextInt(20);
        int b = random.nextInt(20);

        questionTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        correctPosition = random.nextInt(4);
        Log.i("Info", Integer.toString(correctPosition));

        for (int i = 0; i < positions.length; i++) {
            if (i == correctPosition) {
                positions[i] = a + b;
            } else {
                int wrongAns = random.nextInt(40);

                while (wrongAns == a + b){
                    wrongAns = random.nextInt(40);
                }

                positions[i] = wrongAns;
            }
        }

        Log.i("Info", Arrays.toString(positions));
        Button btn0 = findViewById(R.id.button0);
        btn0.setText(Integer.toString(positions[0]));
        Button btn1 = findViewById(R.id.button1);
        btn1.setText(Integer.toString(positions[1]));
        Button btn2 = findViewById(R.id.button2);
        btn2.setText(Integer.toString(positions[2]));
        Button btn3 = findViewById(R.id.button3);
        btn3.setText(Integer.toString(positions[3]));
    }

    public void answerSelected(View view) {
        if (isActive) {
            Button button = (Button) view;
            resultView = findViewById(R.id.resultTextView);
            resultView.setVisibility(View.VISIBLE);

            if (Integer.parseInt(button.getTag().toString()) == correctPosition){
                resultView.setText("Correct Answer!");
                score++;
            } else {
                resultView.setText("Wrong Answer :(");
            }
            nextQuestion();
        }

    }
}
