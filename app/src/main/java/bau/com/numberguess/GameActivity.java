package bau.com.numberguess;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//timer
import android.os.CountDownTimer;
import android.widget.Button;

import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.view.Menu;
import java.util.ArrayList;
import java.util.Scanner;


public class GameActivity extends AppCompatActivity {
    public static final String MARCOS_SHARED_PREFERENCES = "MarcosSharedPreferences";
    private Context mContext;
    private String userName;
    private int userNum;
    private EditText etUserGuess;
    private TextView tvClue;
    private TextView tvScore;
    private int top1;
    private int top2;
    private int top3;
    private int top4;
    private int top5;
    //private ArrayList<Integer> topScore;
    private int solution;
    private int score;
    //Top scores
    String top1string;
    String top2string;
    String top3string;
    String top4string;
    String top5string;
    //Button
    public Button go;
    //timer
    TextView show;
    CountDownT timer;
    private long startPauseTime;
    private long pauseTime = 0L;
    //User second
    private int userSecondsNumber;
    private String userSecond;
    //User dificult
    private String userDificult;
    //last answer
    private TextView tvUserLastAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent i = getIntent();
        userNum = i.getIntExtra("num", userNum);
        userName = i.getStringExtra("name");
        userSecond = i.getStringExtra("sec");
        userDificult = i.getStringExtra("sec");
        getUserSeconds(userSecond);
        getUserDificult(userDificult);
        Log.d("game activity", "game activity started name = " + userName);
        show = (TextView) findViewById(R.id.tv_timer);
        timer = new CountDownT(userSecondsNumber*1000,1000);
        show.setText(String.valueOf(userSecondsNumber));
        go = (Button) findViewById(R.id.btn_go);
        initApp();
        start();
    }

    /***********************************************************************************************
     * Method to get the user dificult
     **********************************************************************************************/
    private void getUserDificult(String userDificult){
        String dificult = userDificult.substring(0,2);

        if (dificult.equals("30")){
            score =  500;
        }
        if (dificult.equals("40")){
            score = 400;
        }
        if (dificult.equals("50")){
            score = 300;
        }
    }

    /***********************************************************************************************
     * Method to get the seconds user
     **********************************************************************************************/
    private void getUserSeconds(String userSeconds){
        String seconds = userSeconds.substring(0,2);
        userSecondsNumber = Integer.parseInt(seconds);
    }

    /***********************************************************************************************
     * Method to star timer
     **********************************************************************************************/
    public void start (){
        timer.start();
    }

    /***********************************************************************************************
     * Method to stop timer
     **********************************************************************************************/
    public void stop() {
        timer.cancel();
    }

//    /***********************************************************************************************
//     * Method pause timer
//     **********************************************************************************************/
//    public void pause(){
//        startPauseTime = System.currentTimeMillis();
//    }

    /***********************************************************************************************
     * Method resume timer
     **********************************************************************************************/
    public void resumen(){
        pauseTime +=  System.currentTimeMillis()-startPauseTime;
    }

    /***********************************************************************************************
     * class timer
     **********************************************************************************************/
    public class CountDownT extends CountDownTimer{
        public CountDownT (long InMilliSeconds, long TimeGap){
            super(InMilliSeconds,TimeGap);
        }

        /*******************************************************************************************
         * Method to show the time
         * @param InMilliSeconds
         ******************************************************************************************/
        @Override
        public void onTick(long InMilliSeconds) {

            show.setText((InMilliSeconds/1000 + ""));
        }

        /*******************************************************************************************
         * Method to end countDown
        *******************************************************************************************/
        @Override
        public void onFinish() {
            show.setText(getString(R.string.text_end_time));
            tvClue.setText(getString(R.string.text_so_bad) + userName);
            tvScore.setText(getString(R.string.text_score_end_time));
            go.setEnabled(false);
            InputMethodManager imm =
                    (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etUserGuess.getWindowToken(), 0);

        }
    }

    /***********************************************************************************************
     * Method to start the second activity.
     **********************************************************************************************/
    private void initApp(){
        mContext = this;
        etUserGuess = (EditText) findViewById(R.id.et_user_guess);
        tvClue = (TextView) findViewById(R.id.tv_clue);
        tvScore = (TextView) findViewById(R.id.tv_score);
        tvUserLastAnswer = (TextView)findViewById(R.id.tv_user_last_answer);
        // Read from shared preferences
        SharedPreferences prefs = getSharedPreferences(MARCOS_SHARED_PREFERENCES, MODE_PRIVATE);

        top1string = prefs.getString("top1", "Robot: 0");
        top2string = prefs.getString("top2", "Robot: 0");
        top3string = prefs.getString("top3", "Robot: 0");
        top4string = prefs.getString("top4", "Robot: 0");
        top5string = prefs.getString("top5", "Robot: 0");

        String[] parts1 = top1string.split(": ");
        String[] parts2 = top2string.split(": ");
        String[] parts3 = top3string.split(": ");
        String[] parts4 = top4string.split(": ");
        String[] parts5 = top5string.split(": ");

        top1 = Integer.parseInt(parts1[1]);
        top2 = Integer.parseInt(parts2[1]);
        top3 = Integer.parseInt(parts3[1]);
        top4 = Integer.parseInt(parts4[1]);
        top5 = Integer.parseInt(parts5[1]);

        Random rn = new Random();
        solution = rn.nextInt(Math.abs(userNum)) ;
    }

    /***********************************************************************************************
     * Method to give the answer an the clue
     * @param view
     **********************************************************************************************/
    public void play(View view) {
        String lastAnswer = etUserGuess.getText().toString();
        tvUserLastAnswer.setText(userName + " " + getString(R.string.last_answer)+" "+lastAnswer);
        if (etUserGuess.getText().toString().equals("")) {
            etUserGuess.setError(getString(R.string.error_not_number));
        } else {
            int userGuess = Integer.parseInt(etUserGuess.getText().toString());
            int absUserGuess = Math.abs(userGuess - solution);
            if (userGuess == solution) {
                stop();
                tvClue.setText
                  (getString(R.string.text_congratulation) + userName + " "+getString(R.string.emoji));
                tvScore.setText
                        (getString(R.string.text_userGuess_is_the_same_solution)+ " " + score);
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etUserGuess.getWindowToken(), 0);
                refreshTop();
                go.setEnabled(false);
            } else if (userGuess < solution && absUserGuess > userNum*0.15) {
                score = score - 5;
                tvClue.setText(getString(R.string.text_cold_higher));
            } else if (userGuess < solution && absUserGuess < userNum*0.15) {
                score = score - 2;
                tvClue.setText(getString(R.string.text_hot_bit_higher));
            } else if (userGuess > solution && absUserGuess >= userNum*0.15) {
                score -= 2;
                tvClue.setText(getString(R.string.text_cold_not_so_high));
            } else if (userGuess > solution && absUserGuess < userNum*0.15) {
                score -= 5;
                tvClue.setText(getString(R.string.text_hot_bit_too_high));
            }
            if (score <= 0) {
                tvClue.setText(getString(R.string.text_so_bad) + userName);
                go.setEnabled(false);
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etUserGuess.getWindowToken(), 0);
            }
            etUserGuess.setText("");
        }

    }

    /***********************************************************************************************
     * Method to restart the Game activity
     * @param view
     **********************************************************************************************/
    public void restart(View view){
        Random rn = new Random();
        solution = rn.nextInt(userNum);
        tvUserLastAnswer.setText("");
        etUserGuess.setText("");
        tvClue.setText("");
        tvScore.setText("");
        Toast.makeText(mContext, getString(R.string.text_toast), Toast.LENGTH_SHORT).show();
        resumen();
        start();
        go.setEnabled(true);
    }

    /***********************************************************************************************
     * Method to bring us to the main activity
     * @param view
     **********************************************************************************************/
    public void backHome(View view){
        finish();
    }

    /***********************************************************************************************
     *Method to update the rankings and give the top5
     **********************************************************************************************/
    public void refreshTop(){

        SharedPreferences.Editor editor =
                            getSharedPreferences(MARCOS_SHARED_PREFERENCES, MODE_PRIVATE).edit();

        if(score > top1){
            top5 = top4;
            top4 = top3;
            top3 = top2;
            top2 = top1;
            top1 = score;
            editor.putString("top1",userName + ": " + String.valueOf(top1));
            editor.putString("top2",top1string);
            editor.putString("top3",top2string);
            editor.putString("top4",top3string);
            editor.putString("top5",top4string);
            top5string = top4string;
            top4string = top5string;
            top3string = top2string;
            top2string = top1string;
            top1string = userName + ": " + String.valueOf(top1);

        }else if(score > top2){
            top5 = top4;
            top4 = top3;
            top3 = top2;
            top2 = score;
            editor.putString("top2",userName + ": " + String.valueOf(top2));
            editor.putString("top3",top2string);
            editor.putString("top4",top3string);
            editor.putString("top5",top4string);
            top5string = top4string;
            top4string = top5string;
            top3string = top2string;
            top2string = userName + ": " + String.valueOf(top2);

        }else if (score > top3){
            top5 = top4;
            top4 = top3;
            top3 = score;
            editor.putString("top3",userName + ": " + String.valueOf(top3));
            editor.putString("top4",top3string);
            editor.putString("top5",top4string);
            top5string = top4string;
            top4string = top5string;
            top3string = userName + ": " + String.valueOf(top3);

        }else if (score > top4){
            top5 = top4;
            top4 = score;
            editor.putString("top4",userName + ": " + String.valueOf(top4));
            editor.putString("top5",top4string);
            top5string = top4string;
            top4string = userName + ": " + String.valueOf(top4);

        }else if (score > top5) {
            top5 = score;
            editor.putString("top5",userName + ": " + String.valueOf(top5));
            top5string = userName + ": " + String.valueOf(top5);
        }
        editor.apply();
    }
}
