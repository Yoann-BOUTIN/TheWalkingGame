package com.thewalkinggame.app.gestionTeam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Permet d'ajouter dans la BDD une invitation.
 * Dans la BDD une invitation a un id, un inviteur, un invité, une date d'invitation.
 * (l'id et la date d'invitation sont générés automatiquement).
 *
 * Created by Marc on 20/04/2014.
 */
public class UserInvitation extends AsyncTask<String, String, String> {
    /**
     * Progress bar qui s'affiche pendant
     * que la requete s'effectue.
     */
    private ProgressDialog pDialog;
    /**
     * Json parser
     */
    JSONParser jsonParser = new JSONParser();
    /**
     * Les parametres de la requete.
     */
    JSONObject params = new JSONObject();
    /**
     * L'activity correspondant au recrutement
     * des joueurs.
     */
    Activity recrutementActivity;
    /**
     * Preferences settings qui permet la
     * gestion du pseudo de l'utilisateur.
     */
    private SharedPreferences settings;
    /**
     * Le pseudo de l'utilisateur
     */
    private String memoire;
    /**
     * Le pseudo du joueur qui est invité.
     */
    private String joueurInvite;
    /**
     * Le nombre d'emplacements restants
     */
    private int nbEmplacementsRestants;
    /**
     * True si l'invitation existe.
     */
    boolean invitationExist;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static final String url_exist_invitation = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/invitation/exist";
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static final String url_create_invitation = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/invitation/new";

    /**
     * Constructeur de la classe UserInvitation
     *
     * @param recrutementActivity L'activité correspondante à RecrutementAcrivity
     * @param joueurInvite Le pseudo du joueur invité
     */
    UserInvitation(RecrutementActivity recrutementActivity, CharSequence joueurInvite){
        this.recrutementActivity = recrutementActivity;
        this.settings = PreferenceManager.getDefaultSharedPreferences(recrutementActivity.getBaseContext());
        this.memoire = settings.getString("pseudo","");
        this.joueurInvite = joueurInvite + "";
        nbEmplacementsRestants = RecrutementActivity.getnbEmplacementsRestants();

    }

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    /**
     * Creating product
     */
    protected String doInBackground(String... args) {

        List<NameValuePair> paramsExist = new ArrayList<NameValuePair>();
        paramsExist.add(new BasicNameValuePair("inviteur", memoire));
        paramsExist.add(new BasicNameValuePair("invite", joueurInvite));
        // Building Parameters

        JSONObject json = jsonParser.makeHttpRequest(url_exist_invitation, "GET", paramsExist);

        try {
            invitationExist = json.getBoolean("exist");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * *
     */
    protected void onPostExecute(String file_url) {

        // dismiss the dialog once done
        if(!invitationExist){
            new SendInvitation().execute();
        }
        else{
            Toast.makeText(recrutementActivity, "Joueur déjà invité, veuillez en trouver un autre", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Permet l'envoie d'une invitation
     */
    class SendInvitation extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(recrutementActivity);
            pDialog.setMessage("Invitation du survivant : " + joueurInvite);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            // Building Parameters
            try {
                params.put("inviteur", memoire);
                params.put("invite", joueurInvite);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonParser.makeHttpPostRequest(url_create_invitation, "POST", params);

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {

            // dismiss the dialog once done
            pDialog.dismiss();
            nbEmplacementsRestants--;
            Toast.makeText(
                recrutementActivity,
                "Invitation envoyée",
                Toast.LENGTH_SHORT).show();
            RecrutementActivity.setnbEmplacementsRestants(nbEmplacementsRestants);
        }
    }

}