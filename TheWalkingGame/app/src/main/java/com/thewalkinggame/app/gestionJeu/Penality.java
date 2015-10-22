package com.thewalkinggame.app.gestionJeu;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thewalkinggame.app.MapFragment;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionUser.Etat;
import com.thewalkinggame.app.gestionUser.UserLocation;

/**
 * Created by Marc on 27/03/2014.
 *
 * Permet la gestion / l'affichage de la pénalité
 * d'un joueur.
 */
public class Penality{
    /**
     * Progress bar circulaire correspondante
     * au temps de pénalité du joueur.
     */
    private ProgressBar m_bar;
    /**
     * Le MapFragment
     */
    private MapFragment activity;
    /**
     * L'état du joueur qui est donc
     * en pénalité.
     */
    private Etat etat = Etat.EN_PENALITE;
    /**
     * La position du joueur.
     */
    private UserLocation userLocation;
    /**
     * La durée de la pénalité en
     * millisecondes.
     */
    private int timePenality = 10000;
    /**
     * Millisecondes avant que la
     * pénalité soit terminée.
     */
    private float millisUntilFinished;

    /** Visuel pour la pénalité **/

    private TextView textPena;
    private TextView textPenal;
    private ImageView fondPena;

    /**
     * L'opacité de l'image affichée
     * à l'écran.
     */
    private float op;
    /**
     * Permet de gérer la vibration
     * de l'appareil.
     */
    private Vibrator vibrator;
    /**
     * Text correspondant à la dstance
     * entre l'objectif et le joueur.
     */
    private TextView textDistBetweenObjAndPlayer;

    /**
     * Constructeur de la classe Penality
     *
     * @param activity L'activité qui va afficher la progress bar
     */
    public Penality(MapFragment activity){
        this.activity = activity;
        op = (float) 0.8;
        // Recupère la vue de la progress bar
        m_bar = (ProgressBar) this.activity.getActivity().findViewById(R.id.progress_bar_circle_penalite);
        textPena = (TextView) this.activity.getActivity().findViewById(R.id.textPenalite);
        textPenal = (TextView) this.activity.getActivity().findViewById(R.id.textPenalit);
        fondPena = (ImageView) this.activity.getActivity().findViewById(R.id.imgPenalite);
        textDistBetweenObjAndPlayer = (TextView) this.activity.getActivity().findViewById(R.id.textDistBetweenObjAndPlayer);
        textDistBetweenObjAndPlayer.setText("");
        fondPena.setAlpha(op);
        // Rend la progress bar visible car de base dans le layout XML
        // elle est invisible
        m_bar.setVisibility(View.VISIBLE);
        textPena.setVisibility(View.VISIBLE);
        textPenal.setVisibility(View.VISIBLE);
        fondPena.setVisibility(View.VISIBLE);
        // Au depart le temps restant = le temps total de la pénalité
        millisUntilFinished = timePenality;
        vibrator = (Vibrator) activity.getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Thread qui affiche une progress bar correspondant au temps
     * de pénalité restant pour le joueur.
     */
    private Thread threadChrono = new Thread(){
        public void run(){
            // Tant qu'il reste du temps de pénalité
            while(millisUntilFinished >= 0) {
                float fraction = millisUntilFinished / (float) timePenality;
                m_bar.setProgress((int)(fraction * 100));
                // On récupere la position du joueur
                userLocation = activity.getUserLocation();
                // Si le joueur est sur l'objectif et qu'il reste du temps de pénalité
                if(userLocation.isOnObjective(etat.getObjectif())) {
                    try {
                        // On décremente son temps de pénalité
                        millisUntilFinished -= 1000;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                // Si le joueur n'est pas sur l'objectif et qu'il reste du temps de pénalité
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // on lance les méthodes suivantes dans le thread principal
            activity.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Une fois sorti du while, le joueur n'est plus en pénalité
                    // On change donc son etat avec l'evenement penaliteTerminee
                    etat = etat.penaliteTerminee();
                    m_bar.setVisibility(View.INVISIBLE);
                    textPena.setVisibility(View.INVISIBLE);
                    textPenal.setVisibility(View.INVISIBLE);
                    fondPena.setVisibility(View.INVISIBLE);

                    // Debute sans délais
                    // On alterne entre sleep, vibration, sleep, vibration.
                    long[] pattern = {0, 150, 80, 400};

                    // -1 car on veut que le pattern s'execute une seule fois.
                    vibrator.vibrate(pattern, -1);
                }
            });
        }
    };

    /**
     * Methode qui permet de lancer le thread qui s'occupe de la
     * pénalité d'un joueur.
     */
    public void runThread(){
        threadChrono.start();
    }
    public Etat getEtat (){return etat;}
}
