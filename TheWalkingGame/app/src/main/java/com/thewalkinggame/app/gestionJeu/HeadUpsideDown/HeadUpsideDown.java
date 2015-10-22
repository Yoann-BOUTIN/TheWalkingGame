package com.thewalkinggame.app.gestionJeu.HeadUpsideDown;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionUser.Etat;

/**
 * L'activity correspondant au jeu HeadUpsideDown.
 */
public class HeadUpsideDown extends Activity implements SensorEventListener {
    /**
     * Le sensor manager
     */
    SensorManager mSensorManager;
    /**
     * La vue du text corespondant à l'enigme
     */
    TextView mTxtViewEnigme;
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
     * La vue correspondant au temps restant
     */
    TextView mTxtViewTimeRemaining;
    /**
     * Le Tag pour le Log
     */
    private static final String LOG_TAG = "SensorAccelerometer";
    /**
     * Valeur de l'accelerometre en Y
     */
    float yAccelerometer;
    /**
     * Le capteur accelerometre
     */
    Sensor accelerometer;
    /**
     * L'etat du joueur
     */
    private Etat etat = Etat.EN_DEFI;
    /**
     * Temps total pour la progress bar ronde
     */
    private int totalCircle;
    /**
     * Temps actuel pour la progress bar ronde
     */
    private int chronoCircle;
    /**
     * True si l'état doit être changé en
     * fin de partie.
     */
    private boolean goChangeEtat;
    /**
     * Progress bar pour le chrono
     */
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_upside_down);

        // On initialise les donnees du jeu
        chrono = 0;
        progress = (ProgressBar) findViewById(R.id.progress_bar_circle_upside);
        Intent i = getIntent();

        /** Ajout de ce if afin de pouvoir tester cette activity **/

        if (i.getIntExtra("total_time", 0) == 0){
            totalTime = 17;
        } else {
            totalTime = i.getIntExtra("total_time",0);
        }

        goChangeEtat = true;
        chronoCircle = 0;
        totalCircle = totalTime * 100;
        // mon objet mSensorManager de type  SensorManager qui prend la main
        mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        // Instancie le capteur accelerometre
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //les objet de la fenetre xml
        mTxtViewTimeRemaining = (TextView) findViewById(R.id.head_upside_down_time_remaining);
        mTxtViewEnigme = (TextView) findViewById(R.id.enigme_head_upside_down_text);
        mTxtViewEnigme.setText("Je me sens mieux la tête à l'envers");


        runChrono();
        runCircle();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // met à jour la valeur de l'accelerometre en Y
        yAccelerometer = sensorEvent.values[1];
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
            Log.d(LOG_TAG, "Sensor's values (" + yAccelerometer + ") and accuracy : "
                    + accuracyStr);
        }
    }

    /**
     * Lorsque le joueur appuie sur le boutn back
     * de sa tablette, rien ne se passe,
     * il n'est pas emmené dans l'activity
     * précedente.
     */
    public void onBackPressed() {
        // nothing
    }

    @Override
    protected void onPause() {
        // On se desabonne
        mSensorManager.unregisterListener(this, accelerometer);
        super.onPause();
    }

    @Override
    protected void onResume() {
		/*
		 * Abonnement au capteur
		 */
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    /**
     * Permet l'affichage du chrono sous forme de
     * progress bar circulaire.
     */
    private Thread threadCircleBar = new Thread() {
        public void run() {
            while (chronoCircle < totalCircle)
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress((totalCircle - chronoCircle) * 100 / totalCircle);
                            if(totalTime - chrono < 10){
                                RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                                llp.setMargins(640, 40, 0, 0); // llp.setMargins(left, top, right, bottom);
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
     * Permet la gestion du chrono, et de la partie.
     * Lorsque le joueur réussi le défi (que la valeur de
     * l'accelerometre est < -8).
     */
    private Thread threadChrono = new Thread(){
        public void run(){
            try {
                while (chrono < totalTime && yAccelerometer > -8) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTxtViewTimeRemaining.setText("" + (totalTime - chrono));
                        }
                    });
                    Thread.sleep(1000);
                    chrono++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (chrono == totalTime) {
                            mTxtViewTimeRemaining.setText("" + 0);
                        }
                        mSensorManager.unregisterListener(HeadUpsideDown.this, accelerometer);
                        if(gameSucceed())
                        {
                            if(goChangeEtat) {
                                etat.defiTermine(true);
                            }
                            HeadUpsideDown.this.finish();
                            Toast.makeText(HeadUpsideDown.this, "Succeed ! ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(goChangeEtat) {
                                etat.defiTermine(false);
                            }
                            HeadUpsideDown.this.finish();
                            Toast.makeText(HeadUpsideDown.this, "Fail ! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    };

    /**
     * Permet de lancer le thread threadChrono
     */
    public void runChrono() {
        threadChrono.start();
    }

    /**
     *
     * @return le text dans le TextView correspondant
     * à l'énigme
     */
    public CharSequence getTextOfTextView() {
        return mTxtViewEnigme.getText();
    }

    /**
     * Permet de modifier la valeur de l'accelerometre
     * en y
     *
     * @param yValue Valeur de l'accelerometre en y
     */
    public void setyAccelerometer(float yValue) {
        yAccelerometer = yValue;
    }

    /**
     * Permet de lancer le thread correspondant à la
     * progress bar circulaire.
     */
    public void runCircle(){ threadCircleBar.start();}

    /**
     *
     * @return un boolean correspondant à true si
     * la valeur de l'accelerometre en y est <= -8
     * et false sinon
     */
    public boolean gameSucceed(){return yAccelerometer <= -8;}

    /**
     *
     * @param goChangeEtat True si l'état doit être changé en fin
     *                     de partie, false sinon.
     *                     Le cas du false est bon pour les tests.
     */
    public void setGoChangeEtat(boolean goChangeEtat){
        this.goChangeEtat = goChangeEtat;
    }
}