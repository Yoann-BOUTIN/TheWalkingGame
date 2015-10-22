package com.thewalkinggame.app.gestionJeu.FindTheKey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.thewalkinggame.app.R;

/**
 * Page de chargement du jeu FindTheKey
 */
public class SplashFindTheKey extends Activity {
    /**
     * Thread qui boucle tant que
     * les données ne sont pas chargées
     * et que l'utilisateur n'a pas
     * appuyé sur l'écran.
     */
    Thread timer;
    /**
     * Temps autorisé pour le jeu
     */
    private int totalTime;
    /**
     * True si les données ont fini
     * d'être récupérées de la BDD.
     */
    private boolean fetchDataTerminated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_find_the_key);

        fetchDataTerminated = false;
        new FetchDataFindKey(SplashFindTheKey.this).execute();

        timer = new Thread(){
            public void run(){
                try{
                    synchronized (this) {
                        wait(90000);
                    }
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally{
                    Intent intent = new Intent (SplashFindTheKey.this, FindTheKey.class);
                    intent.putExtra("total_time",totalTime);
                    startActivity(intent);
                    SplashFindTheKey.this.finish();
                }
            }
        };
        timer.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN && fetchDataTerminated) {
            synchronized(timer){
                // Si l'utilisateur touche l'ecran, alors
                // on sort du synchronized.
                timer.notify();
            }
        } else {
            Toast.makeText(SplashFindTheKey.this, "Chargement", Toast.LENGTH_SHORT).show();
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
    public void setTotalTime(int time){totalTime = time;}
    public void isFetchDataTerminated(boolean terminate){fetchDataTerminated = terminate;}

    public void setFetchDataTerminated(boolean fetchDataTerminated){
        this.fetchDataTerminated = fetchDataTerminated;
    }

}
