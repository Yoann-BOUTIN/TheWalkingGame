package com.thewalkinggame.app.admin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.admin.get.GetHeadUpsideDownData;
import com.thewalkinggame.app.admin.update.UpdateHeadUpsideDownTimer;

/**
 * Activity dans laquelle l'admin peut modifier
 * les données du jeu HeadUpsideDown.
 */
public class ManageHeadUpsideDownActivity extends Activity {
    /**
     * Le bouton qui permet la modification
     * des données pour le jeu.
     */
    private Button bValiderNewTimer;
    /**
     * L'EditText du temps autorisé pour le jeu
     */
    private EditText editTextTimer;
    /**
     * Le TextView correspondant au temps
     * autorisé pour le jeu.
     */
    private TextView textViewTimer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_head_upside_down);

        // On recupere les views
        bValiderNewTimer = (Button) findViewById(R.id.valider_modification_head_upside_down_temps_autorise);
        editTextTimer = (EditText) findViewById(R.id.edit_text_head_upside_down_temps_autorise);
        textViewTimer = (TextView) findViewById(R.id.text_head_upside_down_temps_autorise);

        // On recupere les données du jeu pour les afficher dans
        // les EditText.
        new GetHeadUpsideDownData(ManageHeadUpsideDownActivity.this).execute();

        textViewTimer.setText("Temps autorisé (en millisecondes)");

        // On ajoute les listeners aux boutons
        bValiderNewTimer.setOnClickListener(listenerValiderNewTimer);
    }

    /**
     * Listener pour valider le nouveau temps autorise pour le jeu.
     */
    View.OnClickListener listenerValiderNewTimer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String editText = String.valueOf(editTextTimer.getText());
            new UpdateHeadUpsideDownTimer(ManageHeadUpsideDownActivity.this, Integer.parseInt(editText)).execute();
        }
    };
}

