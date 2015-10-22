package com.thewalkinggame.app.admin.delete;

import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.admin.ManagePlayersActivity;
import com.thewalkinggame.app.admin.get.FetchUsers;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by law on 08/05/2014.
 *
 * Permet de supprimer un joueur.
 */
public class DeletePlayer extends AsyncTask<String,String,String>{

    /**
     * Id correspondant au joueur que l'on veut supprimer
     */
    private int id;
    /**
     * Activity correspondant à ManagePlayersActivity
     */
    private ManagePlayersActivity managePlayersActivity;
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
    final String url_delete_user = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/user/delete";

    /**
     * Constructeur de la class DeletePlayer
     *
     * @param managePlayersActivity Actvity correspondant à ManagePlayersActivity
     * @param id Id du joueur qu'on veut supprimer
     */
    public DeletePlayer(ManagePlayersActivity managePlayersActivity, int id) {
        this.id = id;
        this.managePlayersActivity = managePlayersActivity;
    }

    @Override
    protected String doInBackground(String... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", String.valueOf(id)));

        json = jParser.makeHttpRequest(url_delete_user, "DELETE", params);
        return null;
    }

    protected void onPostExecute(String file_url) {
        new FetchUsers(managePlayersActivity).execute();
    }
}
