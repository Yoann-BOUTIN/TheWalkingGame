package com.thewalkinggame.app.gestionTeam;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marc on 21/04/2014.
 *
 * Permet de refuser toutes les invitations reçues.
 */
public class RefuseAllInvitations extends AsyncTask<String,String,String> {
    /**
     * L'activity correspondante au journal
     * des invitations.
     */
    InvitationActivity invitationActivity;
    /**
     * Preferences settings qui permet la
     * gestion du pseudo du joueur.
     */
    private SharedPreferences settings;
    /**
     * Le pseudo du joueur qui est
     * invité.
     */
    private String joueurInvite;
    /**
     * La progress bar qui s'affiche
     * pendant que la requete s'effectue.
     */
    private ProgressDialog pDialog;
    /**
     * La linked list.
     */
    private LinkedList<HashMap<String, String>> linkedList;
    /**
     * Json parser
     */
    JSONParser jParser = new JSONParser();
    /**
     * Json object.
     */
    JSONObject json;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private final static String url_delete_all_invitations = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/invitation/deleteAllByInvite";

    /**
     * Constructeur de la class RefuseAllInvitations
     *
     * @param invitationActivity L'activity correspondante au journal des invitations.
     */
    RefuseAllInvitations (InvitationActivity invitationActivity){
        this.invitationActivity = invitationActivity;
        // On récupère le pseudo du joueur qui lance l'activité
        settings = PreferenceManager.getDefaultSharedPreferences(invitationActivity.getBaseContext());
        joueurInvite = settings.getString("pseudo","");
        linkedList = new LinkedList<HashMap<String, String>>();
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(invitationActivity);
        pDialog.setMessage("Suppression des invitations, veuillez patienter...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(String... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("invite", joueurInvite));
        // getting JSON string from URL
        json = jParser.makeHttpRequest(url_delete_all_invitations, "DELETE", params);
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();
        invitationActivity.updateJournal(linkedList);
    }
}
