package com.thewalkinggame.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Activity correspondante au générique du jeu
 * The Walking Game.
 */
public class GeneriqueActivity extends Activity {
    /**
     * L'animation qui permet d'afficher
     * de haut en bas.
     */
    Animation translateText;
    /**
     * Le text qui apparait à
     * l'écran.
     */
    TextView textToTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generique);

        /** Text qui défile  **/

        textToTranslate = (TextView) findViewById(R.id.textToTranslate);
        textToTranslate.setText(
                "The Walking Game\n\n\n Projet de licence en informatique\n\n\n\n" +
                        "Codé par :\n\n\n Yoann Boutin\n\n Kevin Varandas\n\n" +
                        " Zied Benzarti\n\n Marc Destefanis\n\n\n\n Année 2013-2014");

        /** Gestion de l'animation **/

        textToTranslate.setVisibility(View.VISIBLE);
        translateText = AnimationUtils.loadAnimation(this, R.anim.anim_translate_text);
        textToTranslate.startAnimation(translateText);
        textToTranslate.setVisibility(View.INVISIBLE);

        thread.start();
    }

    /**
     * Lorsque l'animation est terminée,
     * on renvoie l'utilisateur sur la page
     * principale du jeu.
     */
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            long chrono = 0;
            while (chrono < 23) {
                try {
                    Thread.sleep(1000);
                    chrono++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(GeneriqueActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
            GeneriqueActivity.this.finish();
        }
    });

    @Override
    public void onBackPressed() {
        // nothing
    }
}
