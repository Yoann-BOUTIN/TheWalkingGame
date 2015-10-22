package com.thewalkinggame.app.gestionTeam;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 24/04/2014.
 *
 * Permet de refuser une invitation.
 */
public class RefuseInvitation extends AsyncTask<String,String,String> {
    /**
     * L'activity correspondante au journal
     * des invitations.
     */
    InvitationActivity invitationActivity;
    /**
     * L'id de l'invitation en BDD.
     */
    private String id;
    /**
     * La progress bar qui sera affichée
     * pendant que la requete s'effectue.
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
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private final static String url_delete_one_invitation = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/invitation/delete";

    /**
     * Constructeur de la classe RefuseInvitation.
     *
     * @param invitationActivity L'actvity des invitations
     */
    public RefuseInvitation(
            InvitationActivity invitationActivity,
            String id) {
        this.invitationActivity = invitationActivity;
        this.id = id;
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(invitationActivity);
        pDialog.setMessage("Suppression de l'invitation, veuillez patienter...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(String... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", id));
        json = jParser.makeHttpRequest(url_delete_one_invitation, "DELETE", params);
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();
        new RecupInvitations(invitationActivity).execute();
    }
}
