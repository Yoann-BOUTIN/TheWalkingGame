package com.thewalkinggame.app.admin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.admin.get.GetReanimerData;
import com.thewalkinggame.app.admin.update.UpdateReanimerData;

/**
 * Created by Marc on 01/05/2014.
 *
 * Activity dans laquelle l'admin peut modifier
 * les données du jeu Reanimer.
 */
public class ManageReanimerActivity extends Activity{
    /**
     * Le bouton qui permet de valider
     * le nouveau score.
     */
    private Button bValiderNewScore;
    /**
     * Le bouton qui permet de valider
     * le nouveau temps autorisé.
     */
    private Button bValiderNewTimer;
    /**
     * L'EditText correspondant au score
     * à atteindre pour réussir le jeu.
     */
    private EditText editTextScore;
    /**
     * L'EditText correspondant au
     * temps autorisé pour le jeu.
     */
    private EditText editTextTimer;
    /**
     * Le TextView qui donne des informations
     * à propos du temps autorisé.
     */
    private TextView textViewTimer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reanimer);

        // On recupere les views
        bValiderNewScore = (Button) findViewById(R.id.valider_modification_reanimer_score_a_atteindre);
        bValiderNewTimer = (Button) findViewById(R.id.valider_modification_reanimer_temps_autorise);
        editTextScore = (EditText) findViewById(R.id.edit_text_reanimer_score_a_atteindre);
        editTextTimer = (EditText) findViewById(R.id.edit_text_reanimer_temps_autorise);
        textViewTimer = (TextView) findViewById(R.id.text_temps_autorise);

        // On recupere les données du jeu pour les afficher dans
        // les EditText.
        new GetReanimerData(ManageReanimerActivity.this).execute();

        textViewTimer.setText("Temps autorisé (en millisecondes)");

        // On ajoute les listeners aux boutons
        bValiderNewScore.setOnClickListener(listenerValiderNewData);
        bValiderNewTimer.setOnClickListener(listenerValiderNewData);
    }

    /**
     * Listener pour valider le nouveau score et le nouveau chrono pour le jeu.
     */
    View.OnClickListener listenerValiderNewData = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String editText = String.valueOf(editTextScore.getText());
            String editText2 = String.valueOf(editTextTimer.getText());
            new UpdateReanimerData(ManageReanimerActivity.this, Integer.parseInt(editText), Integer.parseInt(editText2)).execute();
        }
    };
}
