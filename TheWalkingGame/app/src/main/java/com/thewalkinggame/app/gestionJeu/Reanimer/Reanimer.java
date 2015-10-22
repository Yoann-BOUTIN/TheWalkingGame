package com.thewalkinggame.app.gestionJeu.Reanimer;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionUser.Etat;


public class Reanimer extends Activity implements SensorEventListener {
    /**
     * La vue du text correspondant au nombre
     * de secondes restantes avant la fin
     * du mini jeu
     */
    TextView mTxtViewTimeRemaining;
    /**
     * Score courant du joueur
     */
    int score;
    /**
     * Score a atteindre
     */
    int scoreTarget;
    /**
     * Nombre de secondes ecoulees depuis le
     * lancement du mini jeu
     */
    int chrono;
    /**
     * Nombre de secondes que dure le jeu
     */
    int totalTime;
    /**
     * L'etat du joueur
     */
    private Etat etat = Etat.EN_DEFI;
    /**
     * Le Tag pour le Log
     */
    private static final String LOG_TAG = "SensorsGyroscope";
    /**
     * Valeurs courantes des champs du gyroscope
     */
    float xGyroscope, yGyroscope, zGyroscope;
    /**
     * Le sensor manager
     */
    SensorManager sensorManager;
    /**
     * Le gyroscope sensor
     */
    Sensor gyroscope;
    /**
     * Valeur de la progress bar à atteindre
     */
    private int totalCircle;
    /**
     * Valeur de la progress bar
     */
    private int chronoCircle;
    /**
     * progress bar
     */
    private ProgressBar progress;
    /**
     * True si l'état doit être changé en
     * fin de partie.
     */
    private boolean goChangeEtat;
    /**
     * La progress bar correspondant au score
     * comparé au score à atteindre pour réussir
     * l'objectif.
     */
    private ProgressBar lampeProgress;
    /**
     * ImageView correspondant à la torche
     */
    private ImageView torche;

    /**** Les animations pour chaque ImageView ****/

    private Animation animGet3 = null;
    private Animation animGet2 = null;
    private Animation animGet1 = null;
    private Animation animGet0 = null;

    /****** Les ImageView du 3..2..1..GO **********/

    private ImageView imgCountDownGet3 = null;
    private ImageView imgCountDownGet2 = null;
    private ImageView imgCountDownGet1 = null;
    private ImageView imgCountDownGet0 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reanimer);

        // On initialise les donnees du jeu
        score = 0;
        chrono = 0;
        Intent i = getIntent();

        /** Ajout de ce if afin de pouvoir tester cette activity   *
         *  on donne des données de jeu pour les tests.            **/

        if (i.getIntExtra("total_time", 0) == 0 ||
                i.getIntExtra("score_target", 0) == 0){
            totalTime = 17;
            scoreTarget = 1000;
        } else {
            totalTime = i.getIntExtra("total_time",0);
            scoreTarget = i.getIntExtra("score_target", 0);
        }

        goChangeEtat = true;
        torche = (ImageView) findViewById(R.id.torche_game);
        chronoCircle = 0;
        totalCircle = totalTime * 100;
        progress = (ProgressBar) findViewById(R.id.progress_bar_circle_reanimer);
        lampeProgress = (ProgressBar) findViewById(R.id.progress_bar_vertical);
        //les vues de la fenetre xml
        mTxtViewTimeRemaining = (TextView) findViewById(R.id.timeRemaining);


        // Then manage the sensors and listen for changes
        // Instancie le SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Instancie le capteur gyroscope
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        imgCountDownGet3 = (ImageView) findViewById(R.id.img_countdown_get_3);
        imgCountDownGet2 = (ImageView) findViewById(R.id.img_countdown_get_2);
        imgCountDownGet1 = (ImageView) findViewById(R.id.img_countdown_get_1);
        imgCountDownGet0 = (ImageView) findViewById(R.id.img_countdown_get_0);

        animGet3 = AnimationUtils.loadAnimation(this, R.anim.anim_game_countdown_3);
        animGet2 = AnimationUtils.loadAnimation(this, R.anim.anim_game_countdown_2);
        animGet1 = AnimationUtils.loadAnimation(this, R.anim.anim_game_countdown_1);
        animGet0 = AnimationUtils.loadAnimation(this, R.anim.anim_game_countdown_0);

        runCountDown();
        runGame();
    }

    @Override
    protected void onPause() {
        // On se desabonne
        sensorManager.unregisterListener(this, gyroscope);
        super.onPause();
    }

    @Override
    protected void onResume() {
		/*
		 * Abonnement au capteur
		 */
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            String accuracyStr;
            if (SensorManager.SENSOR_STATUS_ACCURACY_HIGH == accuracy) {
                accuracyStr = "SENSOR_STATUS_ACCURACY_HIGH";
            } else if (SensorManager.SENSOR_STATUS_ACCURACY_LOW == accuracy) {
                accuracyStr = "SENSOR_STATUS_ACCURACY_LOW";
            } else if (SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM == accuracy) {
                accuracyStr = "SENSOR_STATUS_ACCURACY_MEDIUM";
            } else {
                accuracyStr = "SENSOR_STATUS_UNRELIABLE";
            }
            Log.d(LOG_TAG, "Sensor's values (" + xGyroscope + "," + yGyroscope + "," + zGyroscope + ") and accuracy : "
                    + accuracyStr);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // mise a jour seulement quand on est dans le bon cas
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // vitesse angulaire autour de chaque axes
            xGyroscope = event.values[0];
            yGyroscope = event.values[1];
            zGyroscope = event.values[2];

            score += (int) (Math.abs(xGyroscope) + Math.abs(yGyroscope) + Math.abs(zGyroscope));

            // Tant qu'il reste du score à obtenir pour atteindre le score max
            if(score < scoreTarget + 9) {
                float fraction = (float)score / scoreTarget;
                lampeProgress.setProgress((int) (fraction * 100));
            }
        }
    }

    /**
     * Affiche la progress bar ciculaire correspondant au
     * temps restant.
     */
    private Thread threadCircleBar = new Thread() {
        public void run() {
            while (chronoCircle < totalCircle)
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress((totalCircle - chronoCircle) * 100 / totalCircle);
                            if (totalTime - chrono < 10) {
                                RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                llp.setMargins(637, 780, 0, 0); // llp.setMargins(left, top, right, bottom);
                                mTxtViewTimeRemaining.setLayoutParams(llp);
                            }
                        }
                    });
                    Thread.sleep(10);
                    chronoCircle++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    };

    /**
     * Le thread qui gere la partie et incrémente
     * le score.
     */
    private Thread threadChrono = new Thread(){
        public void run(){
            while (chrono < totalTime)
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTxtViewTimeRemaining.setText("" + (totalTime - chrono));
                    }
                });
                if (gameSucceed()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // succeed
                            torche.setImageResource(R.drawable.lampe_torche_allume);
                            torche.invalidate();
                            mTxtViewTimeRemaining.setVisibility(View.INVISIBLE);
                            progress.setVisibility(View.INVISIBLE);
                        }
                    });
                    try {
                        threadChrono.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(Reanimer.this, "Succeed ! " + score, Toast.LENGTH_SHORT).show();
                    if(goChangeEtat) {
                        etat.defiTermine(true);
                    }
                    Reanimer.this.finish();
                }


                Thread.sleep(1000);
                chrono++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTxtViewTimeRemaining.setText("" + 0);
                    // unregister every body
                    sensorManager.unregisterListener(Reanimer.this, gyroscope);

                    // fail
                    if(goChangeEtat) {
                        etat.defiTermine(false);
                    }
                    Reanimer.this.finish();
                    Toast.makeText(Reanimer.this, "Fail ! " + score, Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    /**
     * Affiche le compte à rebours avant que
     * le jeu ne débute.
     */
    private Thread threadCountDown = new Thread(new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imgCountDownGet3.setVisibility(View.VISIBLE);
                    imgCountDownGet3.startAnimation(animGet3);
                    imgCountDownGet3.setVisibility(View.INVISIBLE);

                    imgCountDownGet2.setVisibility(View.VISIBLE);
                    imgCountDownGet2.startAnimation(animGet2);
                    imgCountDownGet2.setVisibility(View.INVISIBLE);

                    imgCountDownGet1.setVisibility(View.VISIBLE);
                    imgCountDownGet1.startAnimation(animGet1);
                    imgCountDownGet1.setVisibility(View.INVISIBLE);

                    imgCountDownGet0.setVisibility(View.VISIBLE);
                    imgCountDownGet0.startAnimation(animGet0);
                    imgCountDownGet0.setVisibility(View.INVISIBLE);
                }
            });
        }
    });

    /**
     * Lance la partie une fois que le compte à rebours
     * est terminé.
     */
    private Thread threadLaunchGame = new Thread(new Runnable() {
        @Override
        public void run() {
            long msEcoulees = 0;
            long durationCountDown = animGet3.getDuration()
                    + animGet2.getDuration()
                    + animGet1.getDuration()
                    + animGet0.getDuration()/4;
            while(msEcoulees < durationCountDown){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                msEcoulees+=100;
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    runChrono();
                    runCircle();
                }
            });
        }
    });

    /**
     * Lorsque le joueur appuie sur le boutn back
     * de sa tablette, rien ne se passe,
     * il n'est pas emmené dans l'activity
     * précedente.
     */
    public void onBackPressed(){
        // nothing
    }

    /**
     * Lance le compte à rebours
     */
    public void runCountDown(){ threadCountDown.start(); }

    /**
     * Lance le chrono du jeu, ainsi que la gestion
     * du jeu.
     */
    public void runChrono(){
        threadChrono.start();
    }

    /**
     * Lance la progress bar circulaire
     */
    public void runCircle(){ threadCircleBar.start();}

    /**
     * Démarre le jeu
     */
    public void runGame(){ threadLaunchGame.start();}

    /**
     *
     * @param goChangeEtat True si l'état doit être changé en fin
     *                     de partie, false sinon.
     *                     Le cas du false est bon pour les tests.
     */
    public void setGoChangeEtat(boolean goChangeEtat){
        this.goChangeEtat = goChangeEtat;
    }


    public boolean gameSucceed(){return score >= scoreTarget;}
    public int getScore(){return score;}
    public void setScore(int value){score = value;}
    public void setScoreTarget(int value){scoreTarget = value;}
}
