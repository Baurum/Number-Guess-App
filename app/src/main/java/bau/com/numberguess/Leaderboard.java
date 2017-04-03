package bau.com.numberguess;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Leaderboard extends AppCompatActivity {

    private TextView tvTop1;
    private TextView tvTop2;
    private TextView tvTop3;
    private TextView tvTop4;
    private TextView tvTop5;
    String top1string;
    String top2string;
    String top3string;
    String top4string;
    String top5string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        initApp();

    }

    /***********************************************************************************************
     * Method to start the Leaderboard Activity. method to give the ranking top
     **********************************************************************************************/
    private void initApp(){
        tvTop1 = (TextView) findViewById(R.id.tv_leaderboard_top1);
        tvTop2 = (TextView) findViewById(R.id.tv_leaderboard_top2);
        tvTop3 = (TextView) findViewById(R.id.tv_leaderboard_top3);
        tvTop4 = (TextView) findViewById(R.id.tv_leaderboard_top4);
        tvTop5 = (TextView) findViewById(R.id.tv_leaderboard_top5);

        SharedPreferences prefs = getSharedPreferences
                                    (GameActivity.MARCOS_SHARED_PREFERENCES, MODE_PRIVATE);

        top1string = prefs.getString("top1", "Robot: 0");
        top2string = prefs.getString("top2", "Robot: 0");
        top3string = prefs.getString("top3", "Robot: 0");
        top4string = prefs.getString("top4", "Robot: 0");
        top5string = prefs.getString("top5", "Robot: 0");

        tvTop1.setText(top1string);
        tvTop2.setText(top2string);
        tvTop3.setText(top3string);
        tvTop4.setText(top4string);
        tvTop5.setText(top5string);



    }
}
