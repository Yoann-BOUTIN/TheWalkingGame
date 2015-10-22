package com.thewalkinggame.app.gestionTeam;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marc on 20/04/2014.
 *
 * Permet de récuperer toutes les invitations,
 * afin de les afficher ensuite dans le journal
 * des invitations.
 */
public class RecupInvitations extends AsyncTask<String,String,String>{
    /**
     * Progress bar qui va s'afficher
     * pendant la récuperation des invitations
     * en BDD.
     */
    private ProgressDialog pDialog;
    /**
     * Json parser
     */
    JSONParser jParser = new JSONParser();
    /**
     * Json object
     */
    JSONObject json;
    /**
     * La liste des invitations
     * en Json.
     */
    JSONArray invitations = null;
    /**
     * L'activity correspondante au journal
     * des invitations.
     */
    InvitationActivity invitationActivity;
    /**
     * La liste chainée.
     */
    private LinkedList<HashMap<String, String>> linkedList;
    /**
     * Le hashmap
     */
    private HashMap<String, String> hashMap;
    /**
     * Preferences settings qui permet la
     * gestion du pseudo du joueur.
     */
    private SharedPreferences settings;
    /**
     * Le pseudo du joueur invité.
     */
    private String joueurInvite;
    /**
     * Le pseudo du joueur qui a envoyé
     * l'invitation.
     */
    private String joueurInviteur;
    /**
     * La date d'envoie de l'invitation.
     */
    private String dateInvitation;
    /**
     * L'identifiant de l'invitation
     * en BDD.
     */
    private int idInvitation;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private final static String url_load_invitations = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/invitation/byInvite";

    /**
     * Constructeur de la class RecupInvitations
     *
     * @param invitationActivity L'activity correspondante au journal
     *                           des invitations.
     */
    RecupInvitations (InvitationActivity invitationActivity){
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
        pDialog.setMessage("Chargement des invitations, veuillez patienter...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(String... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("invite", joueurInvite));
        // getting JSON string from URL
        json = jParser.makeHttpRequest(url_load_invitations, "GET", params);
        try {
            invitations = json.getJSONArray("invitations");
            for (int i = 0; i < invitations.length(); i++) {
                JSONObject invit = invitations.getJSONObject(i);
                idInvitation = invit.getInt("id");
                joueurInviteur = invit.getString("inviteur");
                dateInvitation = invit.getString("date");
                hashMap = new HashMap<String, String>();
                hashMap.put("id",String.valueOf(idInvitation));
                hashMap.put("date", dateInvitation);
                hashMap.put("titre", joueurInviteur + " vous invite à rejoindre son équipe");
                hashMap.put("accepter", ""+i);
                hashMap.put("refuser", ""+i);
                linkedList.addFirst(hashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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


