package bau.com.numberguess;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private EditText etName;
    private String userName;
    private EditText etNum;
    private int userNumber;
    //spinner time
    Spinner spUserSeconds;
    private TextView userSecond;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initApp();
        this.spUserSeconds = (Spinner)    findViewById(R.id.spinner_time);
        loadUserSecords();

    }


    /***********************************************************************************************
     * Method to init the app
     **********************************************************************************************/
    private void initApp(){
        mContext = this;
        etName = (EditText) findViewById(R.id.et_user_name);
        etNum = (EditText) findViewById(R.id.et_user_num);

    }


    /***********************************************************************************************
     * Method array adapter
     **********************************************************************************************/

    private void loadUserSecords(){
        final ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource
                        (this, R.array.seconds, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spUserSeconds.setAdapter(adapter);
        this.spUserSeconds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()){
                    case R.id.spinner_time:
                        String[]  arraySeconds = getResources().getStringArray(R.array.seconds);
                        //CharSequence[] seconds = arraySeconds.getTextArray(position);
                        //arraySeconds.recycle();
                        ArrayAdapter<CharSequence> adapter =
                                new ArrayAdapter<CharSequence>
                                (mContext, android.R.layout.simple_spinner_item,
                                        android.R.id.text1, arraySeconds);
                        adapter.setDropDownViewResource
                                (android.R.layout.simple_spinner_dropdown_item);
                        InputMethodManager imm =
                                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(spUserSeconds.getWindowToken(), 0);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    /***********************************************************************************************
     * Method to handle button click
     * @param view the view that was click
     **********************************************************************************************/
    public void handleButtonClick(View view){
        switch (view.getId()){
            case R.id.btn_play:
                userName = etName.getText().toString();
                if (userName.matches("")) {
                    etName.setError(getString(R.string.error_not_name));
                }else if(etNum.getText().toString().matches("0")){
                    etNum.setError(getString(R.string.error_not_main_number));

                }else if(etNum.getText().toString().matches("")){
                    etNum.setError(getString(R.string.error_not_main_number));

                }else {
                    userNumber = Integer.parseInt(etNum.getText().toString());
                    startGame();
                }
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
        String tagNum = "num";
        i.putExtra(tagNum, userNumber);
        String tagSeconds = "sec";
        i.putExtra(tagSeconds, (String) spUserSeconds.getSelectedItem());
        startActivity(i);
    }

    /***********************************************************************************************
     * Method to go to the leaderboard Activity
     * @param view
     **********************************************************************************************/
    public void leaderBoard(View view ){
        Intent ii = new Intent(mContext, LeaderboardActivity.class);
        startActivity(ii);
    }

}

