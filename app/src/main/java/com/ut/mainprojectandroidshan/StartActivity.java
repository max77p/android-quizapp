package com.ut.mainprojectandroidshan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class StartActivity extends AppCompatActivity {
    public static final String USER_NAME = "userName";
    int topScore;
    TextView mTopScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        SharedPreferences prefs = getSharedPreferences("HIGH_SCORE",
                MODE_PRIVATE);
        int highScore= prefs.getInt("topScore", 0);
        String highScoreName = prefs.getString("topScoreName","");
        Log.d("quizhighscore", String.valueOf(highScore));
        mTopScoreText = (TextView) findViewById(R.id.topScoreTextViewMain);
        if(highScore!=0) {
            mTopScoreText.setText("User: " + highScoreName + "     Score: " + highScore);
        }
        else{
            mTopScoreText.setText("No top score available");
        }

        if (savedInstanceState != null) {
            topScore = savedInstanceState.getInt("TopScore");
            System.out.println("The top score is: " + topScore);
        } else {
            topScore = 0;
            System.out.println("The top score is: " + topScore);
        }
}

    public void onSendMessage(View view) {
        EditText messageView = (EditText) findViewById(R.id.username);
        String messageText = messageView.getText().toString();

        if (messageView.getText().toString().isEmpty()) {
            System.out.println("EMPTY!!!!");
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Username missing");
            alert.setCancelable(false);
            alert.setMessage("Please enter a name to start!");
            alert.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        }
        else {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            intent.putExtra(USER_NAME, messageText);
            startActivity(intent);
        }
    }
}
