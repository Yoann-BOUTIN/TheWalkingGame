package com.thewalkinggame.app.gestionTeam;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.TabActionBar;
import com.thewalkinggame.app.Team;
import com.thewalkinggame.app.utilitaire.MyAlertDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class RecrutementActivity extends Activity {
    private static int nbEmplacementsRestants;
    SearchView searchView;

    int nbJoueursManquants;

    // Les textes
    TextView textNbJoueursManquants;
    static TextView pseudoJoueur1;
    static TextView pseudoJoueur2;
    static TextView pseudoJoueur3;
    static TextView pseudoJoueur4;

    int idTeam = 0;
    String joueur2 = "Attente";
    String joueur3 = "Attente";
    String joueur4 = "Attente";
    String memoirePseudoJoueur1;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    // Les boutons
    Button inviterJoueur;

    CharSequence joueurInvite;

    JSONParser jParser = new JSONParser();

    Team team;
    Timer timer = new Timer();
    boolean goGame = true;
    private final static String url_recup_equipe = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/byPseudo";
    private final static String url_team_exist = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/exist";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recrutement);

        team = new Team(memoirePseudoJoueur1, RecrutementActivity.this);

        // Au départ il manque 3 joueurs pour pouvoir former l'equipe.
        nbJoueursManquants = team.getNbJoueursManquants();

        // Au départ le chef d'équipe n'a pas envoyé d'invitations.
        nbEmplacementsRestants = 3;

        searchView = (SearchView) findViewById(R.id.search_view);

        pseudoJoueur1 = (TextView) findViewById(R.id.pseudo_joueur1);
        pseudoJoueur2 = (TextView) findViewById(R.id.pseudo_joueur2);
        pseudoJoueur3 = (TextView) findViewById(R.id.pseudo_joueur3);
        pseudoJoueur4 = (TextView) findViewById(R.id.pseudo_joueur4);

        inviterJoueur = (Button) findViewById(R.id.buttonInviterJoueur);

        // On va chercher en mémoire le pseudo du joueur qui lance l'activité.
        settings = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        editor = settings.edit();
        memoirePseudoJoueur1 = settings.getString("pseudo", "");

        // On l'affiche en tant que chef d'équipe
        pseudoJoueur1.setText(memoirePseudoJoueur1 + " (Chef de l'equipe)");

        // On met à jour le text nb_survivants_manquants
        textNbJoueursManquants = (TextView) findViewById(R.id.nb_survivants_manquants);
        textNbJoueursManquants.setText(nbJoueursManquants + "");

        inviterJoueur.setOnClickListener(listenerInvitation);

        new TeamExist().execute();



        /**
        new RecupTeam(RecrutementActivity.this).execute();
         */

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                new RecupJoueurTeam().execute();
            }
        };

        timer.scheduleAtFixedRate(task, 0, 2000);
    }

    /**
     * Lorsque le joueur click sur le bouton BACK de sa tablette
     * il quitte le recrutement de survivants et cela supprime son equipe
     */
    public void onBackPressed(){
        new MyAlertDialog(
                "Info",
                "Voulez-vous vraiment quitter la recherche de survivants ?",
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
                        // Emmene le joueur au menu
                        timer.cancel();
                        timer.purge();
                        new DeleteTeam().execute(idTeam);
                        RecrutementActivity.this.finish();
                    }
                },
                RecrutementActivity.this).show();
    }

    /**
     * Lance une boite de dialogue qui demande la confirmation de
     * l'invitation du joueur dans le champs de recherche de la
     * SearchView.
     *
     * Click sur "Non" :
     *      - Rien ne se passe.
     *
     * Click sur "Oui" :
     *      - Le champs text joueur manquant correspondant est modifié.
     *      - Le nombre d'invitations envoyées s'incrémente de 1.
     */
    View.OnClickListener listenerInvitation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Récupère la recherche courante dans la SearchView.
            joueurInvite = searchView.getQuery();
            new MyAlertDialog(
                    "Invitation",
                    "Voulez-vous inviter " + joueurInvite +" ?",
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
                            if(nbEmplacementsRestants > 0) {
                                new UserInvitation(RecrutementActivity.this, joueurInvite).execute();
                                setTextJoueurManquant();
                                nbEmplacementsRestants--;
                            } else {
                                Toast.makeText(
                                        RecrutementActivity.this,
                                        "Nombre maximum d'invitations envoyées atteint",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    RecrutementActivity.this).show();
        }
    };

    /**
     * Modifie le text correspondant au pseudo du joueur 2, 3, ou 4
     * en fonction du nombre d'invitations que le chef d'équipe
     * a envoyé.
     *
     * Modifie le text "joueur manquant" en
     *                 "Attente de réponse de pseudoDuJoueur"
     */
    public void setTextJoueurManquant(){
        switch (nbEmplacementsRestants){
            case 3 :
                pseudoJoueur2.setText("En attente de " + joueurInvite);
                break;
            case 2 :
                pseudoJoueur3.setText("En attente de " + joueurInvite);
                break;
            case 1 :
                pseudoJoueur4.setText("En attente de " + joueurInvite);
                break;
        }
    }

    public static void setnbEmplacementsRestants(int nbEmplacementsRestants){
        RecrutementActivity.nbEmplacementsRestants = nbEmplacementsRestants;
    }

    public static int getnbEmplacementsRestants(){
        return nbEmplacementsRestants;
    }

    public int getNbJoueursManquants(){
        return nbJoueursManquants;
    }

    public void setNbJoueursManquants(int nbJoueursManquants){
        this.nbJoueursManquants = nbJoueursManquants;
    }


    class RecupJoueurTeam extends AsyncTask<String,Integer,String> {

        int nbJoueurInTeam = 0;
        int id;

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("pseudo", memoirePseudoJoueur1));
            JSONObject json = jParser.makeHttpRequest(url_recup_equipe, "GET", params);
            try {
                id = json.getInt("id");
                if(!json.isNull("joueur2")){
                    joueur2 = json.getString("joueur2");
                }
                if(!json.isNull("joueur3")){
                    joueur3 = json.getString("joueur3");
                }
                if(!json.isNull("joueur4")){
                    joueur4 = json.getString("joueur4");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(!json.isNull("joueur1") && !json.isNull("joueur2") && !json.isNull("joueur3") && !json.isNull("joueur4")){
                nbJoueurInTeam = 4;
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            idTeam = id;
            if(!joueur2.equals("Attente")){
                pseudoJoueur2.setText(joueur2);
            }
            if(!joueur3.equals("Attente")){
                pseudoJoueur3.setText(joueur3);
            }
            if(!joueur3.equals("Attente")){
                pseudoJoueur4.setText(joueur3);
            }
            if(nbJoueurInTeam == 4 && goGame){
                goGame = false;
                Intent intent = new Intent(RecrutementActivity.this, TabActionBar.class);
                startActivity(intent);
                timer.cancel();
                timer.purge();
                RecrutementActivity.this.finish();
            }
        }



    }

    class TeamExist extends AsyncTask<String,String,String>{

        boolean teamExist;
        @Override
        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("pseudo", memoirePseudoJoueur1));
            JSONObject json = jParser.makeHttpRequest(url_team_exist, "GET", params);

            try {
                teamExist = json.getBoolean("exist");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            if(teamExist){
                Intent intent = new Intent(RecrutementActivity.this, WaitGameActivity.class);
                startActivity(intent);
                timer.cancel();
                timer.purge();
                RecrutementActivity.this.finish();
            }
            else{
                new CreateTeam(RecrutementActivity.this).execute();
            }
        }

    }

}
