package com.ut.mainprojectandroidshan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class ScoreActivity extends Activity {
    public static final String USER_NAME = "userName";
    public static final String USER_SCORE = "userScore";
    TextView mUsernameText;
    TextView mTopScoreText;
    TextView mGameOverText;
    int topScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        SharedPreferences prefs = getSharedPreferences("HIGH_SCORE",
                MODE_PRIVATE);
        Intent intent = getIntent();
        String userName = intent.getExtras().getString(USER_NAME);
        int messageScore = intent.getIntExtra(USER_SCORE, 0);

        int calcPercent = (messageScore * 100) / 13;
        mUsernameText = (TextView) findViewById(R.id.scoreTextView);
        mTopScoreText = (TextView) findViewById(R.id.topScoreTextView);
        mGameOverText = (TextView) findViewById(R.id.gameOverTextView);

        mUsernameText.setText(userName + " your score is: " + calcPercent + "%");

        SharedPreferences.Editor editor = getSharedPreferences("HIGH_SCORE",
                MODE_PRIVATE).edit();
        int highScore = prefs.getInt("topScore", 0);
        String highScoreName = prefs.getString("topScoreName","");
        Log.d("QUIZSCORE", "high score is in score Activity is: "+highScore);
        if (calcPercent > highScore) {
            mGameOverText.setText("Wow!");
            topScore = calcPercent;
            editor.putInt("topScore", topScore);
            editor.putString("topScoreName",userName);
            editor.commit();
            mTopScoreText.setText("You currently hold the record with: "+calcPercent+"%");
        }
        else if(calcPercent<highScore){
            mGameOverText.setText("Game Over");
            mTopScoreText.setText(highScoreName+" currently holds the record with: "+highScore+"%");
        }
        else{
            mTopScoreText.setText("No top score available");
        }



    }

    public void onPlayAgain(View view) {
        startActivity(new Intent(this, StartActivity.class));

    }


    public void onClose(View view) {
        this.finishAffinity();
    }


}
