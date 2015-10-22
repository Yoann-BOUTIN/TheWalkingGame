package com.thewalkinggame.app.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thewalkinggame.app.R;

/**
 * Activity qui donne accés à la partie admin des jeux,
 * des parties, des utilisateurs
 */
public class MainManageActivity extends Activity {
    /**
     * Le bouton manage games
     */
    private Button manageGames;
    /**
     * Le bouton manage players
     */
    private Button managePlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manage);

        // On récupere la vue et on lui attribue le listener
        manageGames = (Button)findViewById(R.id.button_manage_games);
        manageGames.setOnClickListener(goToManageGames);

        managePlayers = (Button)findViewById(R.id.button_manage_players);
        managePlayers.setOnClickListener(goToManagePlayers);

    }

    /**
     * Listener qui envoie sur l'activity ManagerGamesActivity
     */
    View.OnClickListener goToManageGames = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainManageActivity.this, ManageGamesActivity.class);
            startActivity(intent);
        }
    };

    /**
     * Listener qui envoie sur l'activity ManagePlayersActivity
     */
    View.OnClickListener goToManagePlayers = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainManageActivity.this, ManagePlayersActivity.class);
            startActivity(intent);
        }
    };
}
