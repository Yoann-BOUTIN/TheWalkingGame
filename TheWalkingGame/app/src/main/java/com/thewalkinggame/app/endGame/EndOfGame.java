package com.thewalkinggame.app.endGame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thewalkinggame.app.MainActivity;
import com.thewalkinggame.app.R;

public class EndOfGame extends Activity {

    /**
     * Button to go on main menu
     */
    Button back_to_main;
    /**
     * Pseudo: TextView display user pseudi
     * numberSuccess: TextView display number of games win
     * numberFail: TextView display number of failed games
     * numberTotal: TextView display total games done
     * percentageSuccess: TextView display percentage of wins
     * percentageFail; TextView display percentage of loose
     */
    TextView pseudo, numberSuccess, numberFail,
             numberTotal, percentageSuccess, percentageFail, distance;
    /**
     * number of wins and looses
     */
    float nbdefi_ok,nbdefi_fail;
    /**
     * Préférences
     */
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);

        settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String user_pseudo = settings.getString("pseudo","");

        pseudo = (TextView)findViewById(R.id.text_view_pseudo_endgame);
        numberSuccess = (TextView)findViewById(R.id.text_view_defi_reussi);
        numberFail = (TextView)findViewById(R.id.text_view_defi_fail);
        numberTotal = (TextView)findViewById(R.id.text_view_defi_total);
        percentageFail = (TextView)findViewById(R.id.text_view_pourcent_fail);
        percentageSuccess = (TextView)findViewById(R.id.text_view_pourcent_reussi);

        back_to_main = (Button)findViewById(R.id.button_end_of_game_back_main_menu);
        back_to_main.setOnClickListener(backToMain);

        pseudo.setText(user_pseudo);
        Intent i = getIntent();
        nbdefi_fail = (float) (i.getIntExtra("defi_fail",0));
        nbdefi_ok = (float) (i.getIntExtra("defi_ok",0));

        int nbTotaldefi = (int)nbdefi_ok + (int)nbdefi_fail;

        numberSuccess.setText(String.valueOf((int)nbdefi_ok));
        numberFail.setText(String.valueOf((int)nbdefi_fail));
        numberTotal.setText(String.valueOf(nbTotaldefi));
        float defiOkPourcent = (nbTotaldefi == 0)? 0 : (nbdefi_ok / nbTotaldefi) * 100;
        float defiFailPourcent = (nbTotaldefi == 0)? 0 : (nbdefi_fail/nbTotaldefi)*100;
        percentageSuccess.setText(String.valueOf(defiOkPourcent));
        percentageFail.setText(String.valueOf(defiFailPourcent));

        distance = (TextView)findViewById(R.id.text_view_distance_parcourue);
        distance.setText(String.valueOf(0));
    }

    /**
     * Listener to go on the main menu
     */
    View.OnClickListener backToMain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(EndOfGame.this, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        //Nothing
    }
}
