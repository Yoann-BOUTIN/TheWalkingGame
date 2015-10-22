package com.thewalkinggame.app.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thewalkinggame.app.R;

/**
 * L'activity qui répertorie tous les jeux pour lesquels
 * l'admin peut update les données du jeu en question.
 */
public class ManageGamesActivity extends Activity{
    /**
     * Les boutons qui vont rediriger l'admin vers
     * l'activity qui permet de modifier les données du jeu
     * voulue.
     */
    private Button goToManageQuiz,bManageReanimer, bManageHeadUpsideDown, bManageSimon, buttonGoToFindKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_games);

        goToManageQuiz = (Button)findViewById(R.id.button_manage_quiz);
        goToManageQuiz.setOnClickListener(listenerGoToManageQuiz);

        bManageReanimer = (Button) findViewById(R.id.button_manage_reanimer);
        bManageReanimer.setOnClickListener(listenerGoManageReanimer);

        bManageHeadUpsideDown = (Button) findViewById(R.id.button_manage_head_upside_down);
        bManageHeadUpsideDown.setOnClickListener(listenerGoManageHeadUpsideDown);

        bManageSimon = (Button) findViewById(R.id.button_manage_simon);
        bManageSimon.setOnClickListener(listenerGoManageSimon);

        buttonGoToFindKeys = (Button)findViewById(R.id.button_manage_find_key);
        buttonGoToFindKeys.setOnClickListener(listenerGoToFindKeys);
    }

    /**
     * Listener qui envoie sur l'activity ManageReanimerActivity
     */
    View.OnClickListener listenerGoManageReanimer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ManageGamesActivity.this, ManageReanimerActivity.class);
            startActivity(intent);
        }
    };

    /**
     * Listener qui envoie sur l'activity ManageReanimerActivity
     */
    View.OnClickListener listenerGoToManageQuiz = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(ManageGamesActivity.this, ManageQuizActivity.class);
          startActivity(intent);
        }
    };

    /**
     * Listener qui envoie sur l'activity ManageHeadUpsideDownActivity
     */
    View.OnClickListener listenerGoManageHeadUpsideDown = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ManageGamesActivity.this, ManageHeadUpsideDownActivity.class);
            startActivity(intent);
        }
    };

    /**
     * Listener qui envoie sur l'activity ManageSimonActivity
     */
    View.OnClickListener listenerGoManageSimon = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ManageGamesActivity.this, ManageSimonActivity.class);
            startActivity(intent);
        }
    };

    /**
     * Listener qui envoie sur l'activity ManageFindKeysActivity
     */
    View.OnClickListener listenerGoToFindKeys= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ManageGamesActivity.this, ManageFindKeysActivity.class);
            startActivity(intent);
        }
    };
}
