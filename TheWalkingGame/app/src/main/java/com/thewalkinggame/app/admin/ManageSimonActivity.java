package com.thewalkinggame.app.admin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.admin.get.GetSimonData;
import com.thewalkinggame.app.admin.update.UpdateSimonSeqLength;

/**
 * Created by Marc on 23/05/2014.
 *
 * Activity dans laquelle l'admin peut modifier
 * les données du jeu Simon.
 */
public class ManageSimonActivity extends Activity {
    /**
     * Le bouton qui permet la modification
     * du nombre de boutons sur lequel
     * le joueur doit appuyer pour réussir
     * le défi.
     */
    private Button bValiderNewSeqLength;
    /**
     * L'EditText correspondant au nombre
     * de boutons à appuyer pour réussir
     * le défi.
     */
    private EditText editTextSeqLength;
    /**
     * Le TextView donnant des informations
     * supplémentaires sur l'EditText.
     */
    private TextView textViewSeqLength;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_simon);

        // On recupere les views
        bValiderNewSeqLength = (Button) findViewById(R.id.valider_modification_simon_seq_length);
        editTextSeqLength = (EditText) findViewById(R.id.edit_text_modification_simon_seq_length);
        textViewSeqLength = (TextView) findViewById(R.id.text_modification_simon_seq_length);

        // On recupere les données du jeu pour les afficher dans
        // les EditText.
        new GetSimonData(ManageSimonActivity.this).execute();

        textViewSeqLength.setText("Taille de la séquence");

        // On ajoute les listeners aux boutons
        bValiderNewSeqLength.setOnClickListener(listenerValiderNewSeqLength);
    }


    /**
     * Listener pour valider la nouvelle taille de la sequence pour le jeu.
     */
    View.OnClickListener listenerValiderNewSeqLength = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String editText = String.valueOf(editTextSeqLength.getText());
            new UpdateSimonSeqLength(ManageSimonActivity.this, Integer.parseInt(editText)).execute();
        }
    };
}
