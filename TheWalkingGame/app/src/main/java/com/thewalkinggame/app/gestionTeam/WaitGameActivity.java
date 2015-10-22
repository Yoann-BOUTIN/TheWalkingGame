package com.thewalkinggame.app.gestionTeam;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.MainActivity;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.TabActionBar;
import com.thewalkinggame.app.utilitaire.MyAlertDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Zizou on 30/05/2014.
 *
 * Activity qui a pour role de salle d'attente pour
 * les joueurs.
 * Lorsqu'un joueur accepte l'invitation à rejoindre
 * une équipe, si l'équipe n'est pas complete alors
 * il se retrouve dans cette activity en attendant
 * que la team soit full.
 */
public class WaitGameActivity extends Activity {
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private final static String url_recup_equipe = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/byPseudo";
    /**
     * Preferences settings qui permet la gestion
     * du pseudo du joueur.
     */
    SharedPreferences settings;
    /**
     * Preferences editor
     */
    SharedPreferences.Editor editor;
    /**
     * Le pseudo de l'utilisateur
     */
    String memoire;
    /**
     * Le timer
     */
    Timer timer = new Timer();
    /**
     * Peut emmener ou non les joueurs
     * vers la partie.
     */
    boolean goGame = true;
    /**
     * Gestion du background
     * de l'activity.
     */
    private int back;
    /**
     * Image qui s'affiche durant
     * l'attente.
     */
    private ImageView history;
    /**
     * Image qui s'affiche durant
     * l'attente.
     */
    private TextView textHistory;
    /**
     *
     */
    private Boolean bool;
    /**
     * L'id de la team en BDD.
     */
    int idTeam;
    /**
     * Le pseudo du chef de l'équipe.
     */
    String chefTeam;
    /**
     * True si la team n'a pas de chef
     * d'équipe.
     */
    boolean teamWithoutChef = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_game);

        settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = settings.edit();
        memoire = settings.getString("pseudo", "");

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                new RecupJoueurTeam().execute();
            }
        };

        timer.scheduleAtFixedRate(task, 0, 2000);
        back = 0;
        history = (ImageView) findViewById(R.id.history);
        textHistory = (TextView) findViewById(R.id.text_history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(back == 0){
                    history.setBackgroundResource(R.drawable.histoire1);
                    back += 1;
                }
                else if(back == 1){
                    history.setBackgroundResource(R.drawable.histoire2);
                    back += 1;
                }
                else if(back == 2){
                    history.setBackgroundResource(R.drawable.histoire3);
                    back += 1;
                }
                else if(back == 3){
                    history.setBackgroundResource(R.drawable.histoire4);
                    textHistory.setText("Veuillez attendre l'arrivée des autres survivants...");
                }
            }
        });
        bool = true;
    }

    /**
     * Lorsque le joueur click sur le bouton BACK de sa tablette
     * il quitte le recrutement de survivants et cela supprime son equipe
     */
    public void onBackPressed(){
        new MyAlertDialog(
                "Info",
                "Voulez-vous vraiment quitter cette page ? Cela supprimera votre equipe si vous êtes le chef de celle-ci !",
                R.drawable.icon_j1,
                "Non",
                "Oui",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(memoire.equals(chefTeam)){
                            new DeleteTeam().execute(idTeam);
                            Intent intent = new Intent(WaitGameActivity.this, MainActivity.class);
                            startActivity(intent);
                            timer.cancel();
                            timer.purge();
                            WaitGameActivity.this.finish();
                        }
                    }
                },
                WaitGameActivity.this).show();
    }

    /**
     * Permet de récuperer les joueurs
     * qui sont dans l'équipe.
     */
    class RecupJoueurTeam extends AsyncTask<String,Integer,String> {

        JSONParser jParser = new JSONParser();
        int nbJoueurInTeam = 0;
        int id;
        String pseudoChef;

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("pseudo", memoire));
            JSONObject json = jParser.makeHttpRequest(url_recup_equipe, "GET", params);
            try {
                id = json.getInt("id");
                pseudoChef = json.getString("joueur1");
                if(json.isNull("joueur1")){
                    teamWithoutChef = true;
                }
                if(!json.isNull("joueur1") && !json.isNull("joueur2") && !json.isNull("joueur3") && !json.isNull("joueur4")){
                    nbJoueurInTeam = 4;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(String file_url) {
            chefTeam = pseudoChef;
            idTeam = id;
            if(!teamWithoutChef){
                new DeleteTeam().execute(idTeam);
                Intent intent = new Intent(WaitGameActivity.this, MainActivity.class);
                startActivity(intent);
                timer.cancel();
                timer.purge();
                overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
                WaitGameActivity.this.finish();
            }
            if(nbJoueurInTeam == 4 && goGame){
                goGame = false;
                Intent intent = new Intent(WaitGameActivity.this, TabActionBar.class);
                startActivity(intent);
                timer.cancel();
                timer.purge();
                overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
                WaitGameActivity.this.finish();
            }
        }
    }
}
