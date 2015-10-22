package com.thewalkinggame.app.gestionJeu.HeadUpsideDown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.thewalkinggame.app.R;

/**
 * Page de chargement du jeu HeadUpsideDown.
 */
public class SplashHeadUpsideDown extends Activity {
    /**
     * le Thread qui permet de lancer
     * le jeu si l'utilisateur click sur
     * l'écran et que les données ont fini
     * d'être récupérées ou après avoir
     * attendu 30secondes.
     */
    Thread timer;
    /**
     * Indique si les données ont
     * fini d'être récupérées de la BDD.
     */
    boolean recupDataTerminated;
    /**
     * Le temps autorisé pour le jeu
     */
    int totalTime;
    /**
     * TextView d'explications
     */
    TextView txtViewExplications;
    /**
     * TextView d'explications
     */
    TextView txtViewReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_head_upside_down);

        txtViewExplications = (TextView) findViewById(R.id.splash_head_upside_down_explications);
        txtViewReady = (TextView) findViewById(R.id.splash_head_upside_down_ready);

        txtViewExplications.setText("Dans ce jeu, vous devez tenter de résoudre l'enigme.");
        txtViewReady.setText("Quand vous êtes prêt, appuyez sur l'écran");

        recupDataTerminated = false;

        new RecupGameData(SplashHeadUpsideDown.this).execute();

        // On laisse au maximum 30 secondes pour que
        // le joueur comprenne en quoi consiste le jeu.
        timer = new Thread(){
            public void run(){
                try{
                    synchronized (this) {
                        wait(30000);
                    }
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally{
                    Intent intent = new Intent (SplashHeadUpsideDown.this, HeadUpsideDown.class);
                    intent.putExtra("total_time", totalTime);
                    // On lance l'activity HeadUpsideDown
                    startActivity(intent);
                    SplashHeadUpsideDown.this.finish();
                }
            }
        };
        timer.start();
    }


    /**
     * Lorsque l'utilisateur touche l'écran,
     * si les données ont bien été récupérées en BDD
     * alors on peut lancer l'activity du jeu.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN && recupDataTerminated) {
            synchronized(timer){
                // Si l'utilisateur touche l'ecran,
                // et que l'initialisation des données
                // du jeu est terminée,
                // alors
                // on sort du synchronized.
                timer.notify();
            }
        } else {
            Toast.makeText(SplashHeadUpsideDown.this, "Chargement", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    /**
     * Si l'utilisateur appuie sur le bouton
     * back de la tablette, rien ne se passe.
     */
    public void onBackPressed(){
        // nothing
    }

    /**
     * Permet de modifier la valeur du booleen
     * qui indique si les données ont fini d'être
     * récupérées en BDD ou non.
     *
     * @param bool
     */
    public void setRecupDataTerminated(boolean bool){
        recupDataTerminated = bool;
    }

    /**
     * Permet de modifier le temps autorisé
     * pour le jeu.
     *
     * @param totalTime
     */
    public void setTotalTime(int totalTime){
        this.totalTime = totalTime;
    }
}
