package com.thewalkinggame.app;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thewalkinggame.app.gestionTeam.RefuseInvitation;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zizou on 01/06/2014.
 *
 * Fragment qui recapitule les 2 teams
 */
public class ScoreFragment extends Fragment {

    /**
     * pseudo garder en memoire du telephone
     */
    String memoirePseudoJoueur1;

    /**
     * Preferences
     */
    SharedPreferences settings;

    /**
     * Permet d'editer les preferences
     */
    SharedPreferences.Editor editor;

    /**
     * View du pseudo du joueur1
     */
    static TextView pseudoJoueur1;

    /**
     * View du pseudo du joueur2
     */
    static TextView pseudoJoueur2;

    /**
     * View du pseudo du joueur3
     */
    static TextView pseudoJoueur3;

    /**
     * View du pseudo du joueur4
     */
    static TextView pseudoJoueur4;

    /**
     * View du pseudo de l'equipe1
     */
    static TextView viewEquipe1;

    /**
     * pseudo du joueur1
     */
    String joueur1;

    /**
     * pseudo du joueur2
     */
    String joueur2;

    /**
     * pseudo du joueur3
     */
    String joueur3;

    /**
     * pseudo du joueur4
     */
    String joueur4;

    /**
     * nom de l'equipe1
     */
    String equipe1 = "Equipe de ";


    /**
     * true si l'equipe contient bien 4 joueurs
     */
    boolean teamGood = true;

    JSONParser jParser = new JSONParser();
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static String url_recup_equipe = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/byPseudo";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.scoretab, container, false);

        // On va chercher en mémoire le pseudo du joueur qui lance l'activité.
        settings = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getBaseContext());
        editor = settings.edit();
        memoirePseudoJoueur1 = settings.getString("pseudo", "");

        pseudoJoueur1 = (TextView) v.findViewById(R.id.joueur1equipe1);
        pseudoJoueur2 = (TextView) v.findViewById(R.id.joueur2equipe1);
        pseudoJoueur3 = (TextView) v.findViewById(R.id.joueur3equipe1);
        pseudoJoueur4 = (TextView) v.findViewById(R.id.joueur4equipe1);
        viewEquipe1 = (TextView) v.findViewById(R.id.equipe1);

        new RecupTeam().execute();
        return v;
    }

    /**
     * Classe qui recupere les pseudos des membres de l'equipe
     */
    class RecupTeam extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("pseudo", memoirePseudoJoueur1));
            JSONObject json = jParser.makeHttpRequest(url_recup_equipe, "GET", params);
            try {
            if(!json.isNull("joueur1") && !json.isNull("joueur2") && !json.isNull("joueur3") && !json.isNull("joueur4")){

                joueur1 = json.getString("joueur1");
                joueur2 = json.getString("joueur2");
                joueur3 = json.getString("joueur3");
                joueur4 = json.getString("joueur4");
                equipe1 += joueur1;
            }
                else{
                teamGood = false;
            }
            } catch (JSONException e) {
                e.printStackTrace();
                teamGood = false;
            }
            return null;
        }

        /**
         * After completing background
         * *
         */
        protected void onPostExecute(String file_url) {
            if(teamGood) {
                viewEquipe1.setText(equipe1);
                pseudoJoueur1.setText(joueur1);
                pseudoJoueur2.setText(joueur2);
                pseudoJoueur3.setText(joueur3);
                pseudoJoueur4.setText(joueur4);
            }
            else{
                getActivity().finish();
            }
        }
    }
}
