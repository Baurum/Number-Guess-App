package bau.com.numberguess;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private EditText etName;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initApp();
    }

    /***********************************************************************************************
     * Method to init the app
     **********************************************************************************************/
    private void initApp(){
        mContext = this;
        etName = (EditText) findViewById(R.id.et_user_name);
    }

    /***********************************************************************************************
     * Method to handle button click
     * @param view the view that was click
     **********************************************************************************************/
    public void handleButtonClick(View view){
        switch (view.getId()){
            case R.id.btn_play:
                userName = etName.getText().toString();
                startGame();
                break;
            default:
                break;
        }
    }

    /***********************************************************************************************
     * Method to bring to the Game activity
     **********************************************************************************************/
    private void startGame(){
        Intent i = new Intent(mContext, GameActivity.class);
        String tag = "name";
        i.putExtra(tag, userName );
        startActivity(i);
    }

    /***********************************************************************************************
     * Method to go to the leaderboard Activity
     * @param view
     **********************************************************************************************/
    public void leaderBoard(View view ){
        Intent ii = new Intent(mContext, Leaderboard.class);
        startActivity(ii);
    }

}

