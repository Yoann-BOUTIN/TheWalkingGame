package com.thewalkinggame.app.gestionJeu.FindTheKey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionUser.Etat;

public class FindTheKey extends Activity {
    /**
     * ImageButton de la premiere cle a trouver
     */
    private ImageButton key1;
    /**
     * ImageButton de la deuxieme cle a trouver
     */
    private ImageButton key2;
    /**
     * ImageButton de la troisieme cle a trouver
     */
    private ImageButton key3;
    /**
     * ImageButton de la quatrieme cle a trouver
     */
    private ImageButton key4;
    /**
     * ImageButton de la cinquieme cle a trouver
     */
    private ImageButton key5;
    /**
     * ImageButton de la premiere cle trouvee
     */
    private ImageButton keyFound1;
    /**
     * ImageButton de la deuxieme cle trouvee
     */
    private ImageButton keyFound2;
    /**
     * ImageButton de la troisieme cle trouvee
     */
    private ImageButton keyFound3;
    /**
     * ImageButton de la quatrieme cle trouvee
     */
    private ImageButton keyFound4;
    /**
     * ImageButton de la cinquieme cle trouvee
     */
    private ImageButton keyFound5;
    /**
     * Nombre de cle trouvee
     */
    private int countKeyFound;
    /**
     * Temps restant avant la fin du mini jeu
     */
    private TextView mTxtViewTimeRemaining;
    /**
     * Temps total pour le mini jeu
     */
    private int totalTime;
    /**
     * Temps passe sur le mini jeu
     */
    private int chrono;
    /**
     * Temps total pour le chrono en cercle
     */
    private int totalCircle;
    /**
     * Temps passe pour le chrono en cercle
     */
    private int chronoCircle;
    /**
     * Chrono en cercle
     */
    private ProgressBar progress;
    /**
     * True si l'état doit être changé en
     * fin de partie.
     */
    private boolean goChangeEtat;
    /**
     * True si le jeu est réussi, false
     * sinon.
     */
    private boolean isSucceed;
    /**
     * Etat du joueur au debut du mini jeu
     */
    private Etat etat = Etat.EN_DEFI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_the_key);

        Intent i = getIntent();

        /** Ajout de ce if afin de pouvoir tester cette activity **/

        chrono = 0;
        if (i.getIntExtra("total_time", 0) == 0){
            totalTime = 17;
        } else {
            totalTime = i.getIntExtra("total_time",0);
        }

        chronoCircle = 0;
        totalCircle = totalTime*100;
        mTxtViewTimeRemaining = (TextView) findViewById(R.id.timeChronoKey);
        progress = (ProgressBar) findViewById(R.id.progress_bar_circle);
        goChangeEtat = true;
        isSucceed = false;

        /**
         * Definition des images a trouver et disparition des images lorsqu'elles sont trouvees
         */
        key1 = (ImageButton) findViewById(R.id.first_key_to_find);
        keyFound1 = (ImageButton) findViewById(R.id.first_key_found);
        key1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                key1.setVisibility(View.GONE);
                countKeyFound += 1;
                keyFoundNumber(countKeyFound);
            }
        });

        key2 = (ImageButton) findViewById(R.id.second_key_to_find);
        keyFound2 = (ImageButton) findViewById(R.id.second_key_found);
        key2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                key2.setVisibility(View.GONE);
                countKeyFound += 1;
                keyFoundNumber(countKeyFound);
            }
        });

        key3 = (ImageButton) findViewById(R.id.third_key_to_find);
        keyFound3 = (ImageButton) findViewById(R.id.third_key_found);
        key3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                key3.setVisibility(View.GONE);
                countKeyFound += 1;
                keyFoundNumber(countKeyFound);
            }
        });

        key4 = (ImageButton) findViewById(R.id.fourth_key_to_find);
        keyFound4 = (ImageButton) findViewById(R.id.fourth_key_found);
        key4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                key4.setVisibility(View.GONE);
                countKeyFound += 1;
                keyFoundNumber(countKeyFound);
            }
        });

        key5 = (ImageButton) findViewById(R.id.fifth_key_to_find);
        keyFound5 = (ImageButton) findViewById(R.id.fifth_key_found);
        key5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                key5.setVisibility(View.GONE);
                countKeyFound += 1;
                keyFoundNumber(countKeyFound);
            }
        });
        /**
         * Lance les deux chronos
         */
        runChrono();
        runCircle();
    }

    /**
     * Fonction qui affiche au fur et a mesure les clees trouvee
     *
     * @param nb nombre de cle trouvee
     */
    public void keyFoundNumber(int nb){
        if(nb == 1){
            keyFound1.setBackgroundResource(R.drawable.keyv);
            //keyFound1.setBackground(this.getResources().getDrawable(R.drawable.keyv));
            keyFound1.setEnabled(false);
        }
        else if(nb == 2){
            keyFound2.setBackgroundResource(R.drawable.keyv);
            //keyFound2.setBackground(this.getResources().getDrawable(R.drawable.keyv));
            keyFound2.setEnabled(false);
        }
        else if(nb == 3){
            keyFound3.setBackgroundResource(R.drawable.keyv);
            //keyFound3.setBackground(this.getResources().getDrawable(R.drawable.keyv));
            keyFound3.setEnabled(false);
        }
        else if(nb == 4){
            keyFound4.setBackgroundResource(R.drawable.keyv);
            //keyFound4.setBackground(this.getResources().getDrawable(R.drawable.keyv));
            keyFound4.setEnabled(false);
        }
        else if(nb == 5){
            keyFound5.setBackgroundResource(R.drawable.keyv);
            //keyFound5.setBackground(this.getResources().getDrawable(R.drawable.keyv));
            keyFound5.setEnabled(false);
        }
    }

    /**
     * Thread qui met en place le chrono du jeu en cercle
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
                                llp.setMargins(652, 40, 0, 0); // llp.setMargins(left, top, right, bottom);
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
     * Thread qui met en place le chrono du mini jeu
     */
    private Thread threadChrono = new Thread(){
        public void run(){
            while (chrono < totalTime && countKeyFound < 5) {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTxtViewTimeRemaining.setText("" + (totalTime - chrono));
                        }
                    });
                    Thread.sleep(1000);
                    chrono++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (countKeyFound == 5) {
                        // succeed
                        if(goChangeEtat) {
                            etat.defiTermine(true);
                        }
                        isSucceed = true;
                        FindTheKey.this.finish();
                    } else {
                        mTxtViewTimeRemaining.setText("" + 0);
                        // fail
                        if(goChangeEtat) {
                            etat.defiTermine(false);
                        }
                        isSucceed = false;
                        FindTheKey.this.finish();
                    }
                }
            });
        }
    };

    /**
     * Fonction qui lance le chrono
     */
    public void runChrono(){
        threadChrono.start();
    }

    /**
     * Fonction qui lance le chrono circulaire
     */
    public void runCircle(){ threadCircleBar.start();}

    /**
     * Lorsque le joueur presse la touche retour rien ne se passe
     */
    public void onBackPressed(){
        // nothing
    }

    /**
     *
     * @return Le nombre de clés trouvées
     */
    public int getCountKeyFound(){
        return countKeyFound;
    }

    /**
     *
     * @param goChangeEtat True si l'état doit être changé en fin
     *                     de partie, false sinon.
     *                     Le cas du false est bon pour les tests.
     */
    public void setGoChangeEtat(boolean goChangeEtat){
        this.goChangeEtat = goChangeEtat;
    }

    public boolean getIsSucceed(){
        return isSucceed;
    }

    public void setTotalTime(int totalTime){
        this.totalTime = totalTime;
    }
}
