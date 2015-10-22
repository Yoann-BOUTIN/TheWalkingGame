package com.thewalkinggame.app.gestionJeu.JeuSimon;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionUser.Etat;

public class SimonGameActivity extends Activity {
    /**
     * Bouton qui va servir au motif
     */
    public Button l1;
    /**
     * Bouton qui va servir au motif
     */
    public Button l2;
    /**
     * Bouton qui va servir au motif
     */
    public Button l3;
    /**
     * Bouton qui va servir au motif
     */
    public Button l4;
    /**
     * Bouton qui lancera le motif presente au joueur
     */
    public Button bready;
    /**
     * Nouvelle sequence de nombres genere aleatoirement qui representera la sequence a suivre
     */
    public int seq[];
    /**
     * Position actuelle dans la sequence
     */
    public int seq_pos = 0;
    /**
     * Boolean permettant de connaitre si le motif est termina ou pas
     */
    private Boolean finishedSchema = false;
    /**
     * Etat du joueur lorsqu'il arrive sur le jeu
     */
    private Etat etat = Etat.EN_DEFI;
    /**
     * Texte montrant la fin du motif genere et indiquant que le joueur peut commencer a le reproduire
     */
    public TextView txt;
    /**
     * Longueur du motif
     */
    private int seqLength;
    /**
     * Boolean qui definira si le joueur a reussi ou non a reproduire le motif
     */
    private boolean isSucceed;
    /**
     * True si la sequence est construite.
     */
    private boolean seqConstructed;
    /**
     * True si l'état doit être changé en
     * fin de partie.
     */
    private boolean goChangeEtat;
    /**
     * Boolean permettant de savori si les donnees ont bien etaient recupere sur le serveur
     */
    private boolean recupDataTerminated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_game);

        recupDataTerminated = false;
        seqConstructed = false;
        new RecupGameData(SimonGameActivity.this).execute();

        isSucceed = false;
        goChangeEtat = true;

        /**
         * declaration des 5 OnClickListener correspondant aux differents boutons
         */
        bready = (Button) findViewById(R.id.readySimon);
        l1 = (Button) findViewById(R.id.layout_green);
        l1.setEnabled(false);
        l2 = (Button) findViewById(R.id.layout_red);
        l2.setEnabled(false);
        l3 = (Button) findViewById(R.id.layout_yellow);
        l3.setEnabled(false);
        l4 = (Button) findViewById(R.id.layout_blue);
        l4.setEnabled(false);
        txt = (TextView) findViewById(R.id.textCAVSimon);


        /**
         * Lorsqu'on click sur le bouton READY la liste seq va etre rempli de nombre genere aleatoirement qui vont represente
         * le motif a reproduire.
         * On montre ensuite au joueur le motif.
         */
        bready.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(recupDataTerminated) {
                    seq = new int[seqLength];
                    seqConstructed = true;
                    for (int i = 0; i < seqLength; i++) {
                        seq[i] = (int) (Math.random() * 4) + 1;
                    }
                    v.setVisibility(View.GONE);
                    seq_show();
                    seq_write(seqLength);
                } else {
                    Log.v(">>>>>>", "Données non recup");
                }
            }
        });
    }

    /**
     * Fonction pour recuperer la sequence genere
     * @return liste
     */
    public int[] getSeq(){
        return seq;
    }

    public void setSeq(int[] seq){
        this.seq = seq;
    }

    /**
     * Fonction qui donne la longueur de la liste du motif
     * @return int
     */
    public int getSeqLength(){
        return seqLength;
    }

    /**
     * Fonction qui donne le Boolean pour savoir si le joueur a reussi a reproduire le motif ou non
     * @return Boolean
     */
    public boolean getIsSucceed(){
        return isSucceed;
    }

    /**
     * Si le joueur presse le bouton de retour sur la tablette rien ne se passe
     */
    public void onBackPressed(){
        // nothing
    }

    /**
     * Fonction permettant de montrer au joueur le motif qu'il va reproduire
     * Pour cela on utilise un thread qui va lire la liste seq element par element et associer un nombre entre 1 et 4 aux
     * differents boutons.
     * Une fois arrive au dernier element les boutons vont devenir actif afin que le joueur puisse reproduire le meme motif
     * et un texte "C'est a vous" va s'afficher pour indiquer au joueur qu'il peut jouer.
     */
    public void seq_show() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v(">>>", "length : " + seq.length);
                for (int i = 0; i < seq.length; i++) {
                    Log.v(">>>", "i = " + i);
                    if(i == (seqLength-1)){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                l1.setEnabled(true);
                                l2.setEnabled(true);
                                l3.setEnabled(true);
                                l4.setEnabled(true);
                                txt.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    switch (seq[i]) {
                        case 1:
                            Log.v("0 >>>", "Green");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    l1.setBackgroundResource(R.drawable.green2);
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    l1.setBackgroundResource(R.drawable.green1);
                                }
                            });
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            Log.v("1 >>>", "Red");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    l2.setBackgroundResource(R.drawable.red2);
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    l2.setBackgroundResource(R.drawable.red1);
                                }
                            });
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            Log.v("2 >>>", "Yellow");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    l3.setBackgroundResource(R.drawable.yellow2);
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    l3.setBackgroundResource(R.drawable.yellow1);
                                }
                            });
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            Log.v("3 >>>", "Blue");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    l4.setBackgroundResource(R.drawable.blue2);
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    l4.setBackgroundResource(R.drawable.blue1);
                                }
                            });
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * Fonction qui va permettre au joueur de reproduire le motif qui lui a ete presente.
     * Lorsque le joueur va appuyer sur un des 4 boutons qui lui sont presente ce bouton va lancer une autre fonction
     * pour savoir si c'etait le bon bouton a presser.
     *
     * @param seq_end correspond a la longueur de la sequence
     */
    public void seq_write(int seq_end){
        for (int i = 0; i < seq_end; i++){
            l1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    seq_input(1);
                }
            });
            l2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    seq_input(2);
                }
            });
            l3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    seq_input(3);
                }
            });
            l4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    seq_input(4);
                }
            });
        }
    }

    /**
     * Lorsque le joueur va appuyer sur un des 4 boutons qui lui sont presente ce bouton, qui est associe a un nombre,
     * va etre compare a l'element actuel de la liste, si c'est le meme on avance dans la liste jusqu'a ce que le joueur gagne,
     * si ce nombre est different de celui de la liste alors le joueur a perdu.
     *
     * @param but_pushed bouton qui vient d'etre presse
     */
    public void seq_input(int but_pushed) {
        if (seq[seq_pos] == but_pushed) {
            seq_pos++;
            but_show(but_pushed);
            if(seq_pos == seqLength){
                isSucceed = true;
                Toast.makeText(SimonGameActivity.this, "Bien joué !", Toast.LENGTH_LONG).show();
                if(goChangeEtat) {
                    etat.defiTermine(true);
                }
                SimonGameActivity.this.finish();
            }

        } else {
            isSucceed = false;
            if(goChangeEtat) {
                etat.defiTermine(false);
            }
            SimonGameActivity.this.finish();
            Toast.makeText(SimonGameActivity.this, "Désolé vous avez perdu.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Fonction qui permet le changement de couleur lorsque un bouton est presse, ou pour la presentation du motif
     * Cette fonction change le background du bouton pendant un instant puis reviens sur son background de base.
     *
     * @param but_num numero du bouton actuel
     */
    public void but_show(int but_num) {
        if (but_num == 1) {
            l1.setBackgroundResource(R.drawable.green2);
            new CountDownTimer(200, 50) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    l1.setBackgroundResource(R.drawable.green1);
                }
            }.start();
        } else if (but_num == 2) {
            l2.setBackgroundResource(R.drawable.red2);
            new CountDownTimer(200, 50) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    l2.setBackgroundResource(R.drawable.red1);
                }
            }.start();
        } else if (but_num == 3) {
            l3.setBackgroundResource(R.drawable.yellow2);
            new CountDownTimer(200, 50) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    l3.setBackgroundResource(R.drawable.yellow1);
                }
            }.start();
        } else if (but_num == 4) {
            l4.setBackgroundResource(R.drawable.blue2);
            new CountDownTimer(200, 50) {

                @Override
                public void onTick(long arg0) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onFinish() {
                    l4.setBackgroundResource(R.drawable.blue1);
                }
            }.start();
        }
    }

    /**
     * Fonction qui permet de modifier la longueur de la sequence que l'on veut creer
     *
     * @param seq longueur de la sequence
     */
    public void setSeqLength(int seq){
        seqLength = seq;
    }

    /**
     * Fonction qui permet de savoir si les donnees ont bien ete recupere sur le serveur
     *
     * @param recupDataTerminated Boolean
     */
    public void setRecupDataTerminated(boolean recupDataTerminated){
        this.recupDataTerminated = recupDataTerminated;
    }

    public boolean getRecupDataTerminated(){
        return recupDataTerminated;
    }

    public boolean getSeqConstructed(){
        return seqConstructed;
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
}