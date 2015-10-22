package com.thewalkinggame.app.gestionTeam;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.thewalkinggame.app.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marc on 26/04/2014.
 *
 * Permet de créér une équipe.
 */
public class CreateTeam extends AsyncTask<String,String,String> {
    /**
     * Progress bar qui s'affiche pendant
     * que la requete s'effectue.
     */
    private ProgressDialog pDialog;
    /**
     * Json parser
     */
    JSONParser jParser = new JSONParser();
    /**
     * les parametres de la requete.
     */
    JSONObject params = new JSONObject();
    /**
     * L'activity correspondant au recrutement
     * des joueurs.
     */
    RecrutementActivity recrutementActivity;
    /**
     * Preferences settings qui permet la gestion
     * du pseudo du joueur.
     */
    private SharedPreferences settings;
    /**
     * Le pseudo du joueur.
     */
    private String joueur;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private final static String url_create_team = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/new";

    /**
     * Constructeur de la class CreateTeam
     *
     * @param recrutementActivity L'activity correspondant au recrutement des joueurs.
     */
    CreateTeam(RecrutementActivity recrutementActivity)
    {
        this.recrutementActivity = recrutementActivity;
        // On récupère le pseudo du joueur qui lance l'activité
        settings = PreferenceManager.getDefaultSharedPreferences(recrutementActivity.getBaseContext());
        joueur = settings.getString("pseudo","");
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(recrutementActivity);
        pDialog.setMessage("Chargement, veuillez patienter...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(String... args) {
        try {
            params.put("joueur1", joueur);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // getting JSON string from URL
        jParser.makeHttpPostRequest(url_create_team, "POST", params);
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();
    }
}