package com.thewalkinggame.app.admin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.admin.get.FetchTimeFindKeys;
import com.thewalkinggame.app.admin.update.UpdateTimeFindKeys;

/**
 * Created by kevin
 *
 * Activity dans laquelle l'admin peut modifier
 * les données du jeu FindTheKey.
 */
public class ManageFindKeysActivity extends Activity {
    /**
     * Le bouton qui permet la modification
     * des données pour le jeu.
     */
    private Button button_validation_modif_time;
    /**
     * L'EditText du temps autorisé pour le jeu
     */
    private EditText editText_time_limite;
    /**
     * Le text qui apparait lorsque l'update
     * est effectuée avec succés
     */
    private TextView updateSucceed;
    /**
     * L'animation correspondante au succés
     * de l'update.
     */
    private Animation animTextSucceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_find_keys);

        button_validation_modif_time = (Button)findViewById(R.id.validation_modification_time_find_keys);
        editText_time_limite = (EditText)findViewById(R.id.edit_text_limite_of_time_find_keys);

        button_validation_modif_time.setOnClickListener(listenerUpdateTime);

        updateSucceed = (TextView)findViewById(R.id.text_update_time_find_key_succeed);
        animTextSucceed = AnimationUtils.loadAnimation(this, R.anim.anim_update_time_findkey);

        new FetchTimeFindKeys(ManageFindKeysActivity.this).execute();
    }

    private View.OnClickListener listenerUpdateTime = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = String.valueOf(editText_time_limite.getText());
            new UpdateTimeFindKeys(ManageFindKeysActivity.this, text).execute();
        }
    };

    /**
     * Modifie l'EditText correspondant au temps autorisé
     * pour le jeu FindTheKey
     *
     * @param time_limite le temps autorisé que l'on veut attribuer au jeu
     *                    FindTheKeys.
     */
    public void display_time(String time_limite) {
        editText_time_limite.setText(time_limite);
    }

    /**
     * Affiche l'animation de l'update des données
     * du jeu.
     */
    public void displayUpdateSucceed() {
        updateSucceed.setText("Mise à jour réussie !");
        updateSucceed.setVisibility(View.VISIBLE);
        updateSucceed.startAnimation(animTextSucceed);
        updateSucceed.setVisibility(View.INVISIBLE);
    }
}
