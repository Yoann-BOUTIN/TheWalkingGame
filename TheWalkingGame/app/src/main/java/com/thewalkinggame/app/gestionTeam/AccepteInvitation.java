package com.thewalkinggame.app.gestionTeam;

import android.app.ProgressDialog;
import android.content.Intent;
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
 * Created by Marc on 28/04/2014.
 *
 */
public class AccepteInvitation extends AsyncTask<String,String,String> {
    /**
     * L'activity du journal d'invitations
     */
    InvitationActivity invitationActivity;
    /**
     * Preferences settings pour gerer
     * le pseudo de l'utilisateur courant.
     */
    private SharedPreferences settings;
    /**
     * Le joueur qui est invité
     */
    String joueurInvite;
    /**
     * Le joueur qui a envoyé
     * l'invitation.
     */
    String pseudoInviteur;
    /**
     * L'id de l'invitation en BDD.
     */
    String idInvit;
    /**
     * La progress dialog qui sera
     * affichée le temps de la requete.
     */
    private ProgressDialog pDialog;
    /**
     * Json parser
     */
    JSONParser jParser = new JSONParser();
    String placeLibre;
    /**
     * True si l'insertion du joueur
     * à la team est effectué avec
     * succés.
     */
    boolean success;
    /**
     * True si l'équipe est au complet,
     * false sinon.
     */
    boolean teamComplete = false;
    /**
     * True si la team existe.
     */
    boolean teamExist = true;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static String url_accepte_invitation = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/updateJoueur";
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static String url_recup_equipe = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/byPseudo";

    /**
     * Constructeur de la classe AccepteInvitation.
     *
     * @param invitationActivity L'actvity des invitations
     * @param pseudoInviteur Le pseudo du joueur qui invite (le chef d'équipe)
     */
    public AccepteInvitation(
            InvitationActivity invitationActivity,
            String pseudoInviteur, String idInvit) {
        this.invitationActivity = invitationActivity;
        // On récupère le pseudo du joueur qui lance l'activité
        settings = PreferenceManager.getDefaultSharedPreferences(invitationActivity.getBaseContext());
        joueurInvite = settings.getString("pseudo","");
        this.pseudoInviteur = pseudoInviteur;
        this.idInvit = idInvit;
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute(){
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("pseudo", pseudoInviteur));
        JSONObject json = jParser.makeHttpRequest(url_recup_equipe, "GET", params);
        if(json.isNull("joueur1")){
            teamExist = false;
        }else{
            if(json.isNull("joueur2")){
                url_accepte_invitation = url_accepte_invitation + "2";
                placeLibre = "joueur2";
            }
            else if(json.isNull("joueur3")){
                url_accepte_invitation = url_accepte_invitation + "3";
                placeLibre = "joueur3";
            }
            else if(json.isNull("joueur4")){
                url_accepte_invitation = url_accepte_invitation + "4";
                placeLibre = "joueur4";
            }
            if(!json.isNull("joueur1") && !json.isNull("joueur2") && !json.isNull("joueur3") && !json.isNull("joueur4")){
                teamComplete = true;
            }
        }

        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        if (teamComplete){
            Toast.makeText(invitationActivity, "Equipe déja complete !", Toast.LENGTH_SHORT).show();
            new RefuseInvitation(invitationActivity,idInvit).execute();
        }
        else if(!teamExist){
            Toast.makeText(invitationActivity, "L'équipe n'existe plus !", Toast.LENGTH_SHORT).show();
            new RefuseInvitation(invitationActivity,idInvit).execute();
        }
        else{
            new InsertInTeam().execute();
        }

    }

    class InsertInTeam extends AsyncTask<String,String,String>{
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(invitationActivity);
            pDialog.setMessage("Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(placeLibre, joueurInvite));
            params.add(new BasicNameValuePair("joueur1", pseudoInviteur));
            JSONObject json = jParser.makeHttpRequest(url_accepte_invitation, "GET", params);
            try {
                success = json.getBoolean("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if(success){
                new RefuseAllInvitations(invitationActivity).execute();
                Intent intent = new Intent(invitationActivity, WaitGameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                invitationActivity.getApplicationContext().startActivity(intent);
            }
        }

    }
}