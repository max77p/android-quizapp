package com.ut.mainprojectandroidshan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String USER_NAME = "userName";
    public static final String USER_SCORE = "userScore";

    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mScoreTextView;
    TextView mQuestionTextView;
    TextView mUsernameTextView;
    ProgressBar mProgressBar;
    int mIndex;
    int mScore;
    int mQuestion;
    String userName;


    // TODO: Create questionbank
    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, false),
            new TrueFalse(R.string.question_3, false),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, false),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, false),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, true),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13, false),
            new TrueFalse(R.string.question_14, false),
            new TrueFalse(R.string.question_15, true),
            new TrueFalse(R.string.question_16, false),
            new TrueFalse(R.string.question_17, true),
            new TrueFalse(R.string.question_18, true)
    };


    // TODO: Create progress bar
    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / 13);

    //TODO: shuffle and array
    public static void shuffleArray(TrueFalse[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static void swap(TrueFalse[] a, int i, int change) {
        TrueFalse helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String messageText = intent.getExtras().getString(USER_NAME);
        userName = messageText;
        mUsernameTextView = (TextView) findViewById(R.id.username_text_view);
        mUsernameTextView.setText("Player: " + userName);

        Log.d("maingame", "Username is: " + messageText);

        shuffleArray(mQuestionBank);
        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");

        } else {
            mScore = 0;
            mIndex = 0;
            System.out.println("The current randomize value is: " + mQuestionBank[0].getmQuestionID());

        }

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mQuestion = mQuestionBank[mIndex].getmQuestionID();
        mQuestionTextView.setText(mQuestion);
        mScoreTextView.setText("Score " + mScore + "/" + (mQuestionBank.length-5));

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quiz", "Button pressed!");
//                Toast.makeText(getApplicationContext(), "True Pressed", Toast.LENGTH_SHORT).show();

                checkAnswer(true);
                updateQuestion();


            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Quiz", "False Button pressed!");
//                Toast myToast = Toast.makeText(getApplicationContext(), "False pressed!", Toast.LENGTH_SHORT);
//                myToast.show();
//                checkAnswer(false)

                checkAnswer(false);
                updateQuestion();

            }
        });
    }

    private void updateQuestion() {

        Log.d("Quiz", "Current index is: " + mIndex);
        if (mIndex == 12) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + " points!");
            alert.setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishGame();
                }
            });
            alert.setPositiveButton("Restart Game", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    restartGame();
                }
            });
            alert.show();
            mQuestionTextView.setText("");
        } else {
            mIndex = (mIndex + 1);
            mQuestion = mQuestionBank[mIndex].getmQuestionID();
            mQuestionTextView.setText(mQuestion);
        }

        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + "/" + (mQuestionBank.length-5));
    }

    private void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mQuestionBank[mIndex].ismAnswer();

        if (userSelection == correctAnswer) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    //Restart the game and reset all counters to 0
    private void restartGame() {
        shuffleArray(mQuestionBank);
        mScore = 0; //set the score back to 0
        mProgressBar.setProgress(0); //reset progress bar
        mIndex = 0;
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
        onRestart();
        mQuestion = mQuestionBank[mIndex].getmQuestionID();
        mQuestionTextView.setText(mQuestion);
    }

    private void finishGame(){
        System.out.println(mScore);
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(USER_NAME, userName);
        intent.putExtra(USER_SCORE, mScore);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
    }

}
