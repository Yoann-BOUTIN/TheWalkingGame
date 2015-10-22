package com.thewalkinggame.app.gestionJeu.Reanimer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.thewalkinggame.app.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Marc on 06/05/2014.
 *
 * Page de chargement du jeu Reanimer.
 */
public class SplashReanimer extends Activity {
    /**
     * le Thread qui permet de lancer
     * le jeu si l'utilisateur click sur
     * l'écran et que les données ont fini
     * d'être récupérées ou après avoir
     * attendu 30secondes.
     */
    Thread timer;
    /**
     * Image qui explique le fonctionnement
     * du jeu.
     */
    ImageView img;
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
     * Le score à atteindre pour gagner
     * le jeu.
     */
    int scoreTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_reanimer);

        scoreTarget = 1000;
        totalTime = 16;
        recupDataTerminated = false;

        new RecupGameData(SplashReanimer.this).execute();

        ImageView img = (ImageView)findViewById(R.id.anim_splash_reanimer);
        img.setBackgroundResource(R.anim.anim_splash_reanimer);
        MyAnimationRoutine mar = new MyAnimationRoutine();
        Timer t = new Timer(false);
        t.schedule(mar, 100);

        /**
         * On laisse au maximum 30 secondes pour que
         * le joueur comprenne en quoi consiste le jeu.
         */
        timer = new Thread(){
            public void run(){
                try{
                    synchronized (this) {
                        wait(30000);
                    }
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally{
                    Intent intent = new Intent (SplashReanimer.this, Reanimer.class);
                    intent.putExtra("score_target", scoreTarget);
                    intent.putExtra("total_time", totalTime);
                    // On lance l'activity Reanimer
                    startActivity(intent);
                    SplashReanimer.this.finish();
                }
            }
        };
        timer.start();

        /**
         * Afin de faire passer le test de cette activity
         * On met un listener sur l'image pour pouvoir click
         * dessus dans le test.
         */
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recupDataTerminated){
                    synchronized (timer){
                        timer.notify();
                    }
                }
            }
        });
    }

    /**
     * Gere l'animation d'explication du jeu.
     */
    class MyAnimationRoutine extends TimerTask
    {
        MyAnimationRoutine()
        {
        }

        public void run(){
            img = (ImageView) findViewById(R.id.anim_splash_reanimer);
            AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
            frameAnimation.start();
        }
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
                // Si l'utilisateur touche l'ecran, alors
                // on sort du synchronized.
                timer.notify();
            }
        }
        return true;
    }

    /**
     * Lorsque le joueur appuie sur le boutn back
     * de sa tablette, rien ne se passe,
     * il n'est pas emmené dans l'activity
     * précedente.
     */
    public void onBackPressed(){
        // nothing
    }


    public void setRecupDataTerminated(boolean bool){
        recupDataTerminated = bool;
    }
    public void setTotalTime(int totalTime){
        this.totalTime = totalTime;
    }
    public void setScoreTarget(int scoreTarget){
        this.scoreTarget = scoreTarget;
    }
}