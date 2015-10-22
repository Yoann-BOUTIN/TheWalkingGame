package com.thewalkinggame.app.admin.delete;

import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.admin.ManagePlayersActivity;
import com.thewalkinggame.app.admin.get.FetchUsers;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by law on 11/05/2014.
 *
 * Permet de supprimer tous les joueurs du jeu TheWalkingGame
 */
public class DeleteAllPlayers extends AsyncTask<String,String,String>{
    /**
     * Correspond à l'activity  ManagePlayersActivity
     */
    private ManagePlayersActivity managePlayersActivity;
    /**
     * Json object
     */
    private JSONObject json;
    /**
     * Json parser
     */
    private JSONParser jParser = new JSONParser();
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private String url_delete_all_users = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/user/deleteAllUsers";

    /**
     * Constructeur de la class DeleteAllPlayers
     *
     * @param managePlayersActivity Correspond à l'activity ManagePlayersActivity
     */
    public DeleteAllPlayers(ManagePlayersActivity managePlayersActivity) {
        this.managePlayersActivity = managePlayersActivity;
    }

    @Override
    protected String doInBackground(String... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        json = jParser.makeHttpRequest(url_delete_all_users, "DELETE", params);
        return null;
    }


    protected void onPostExecute(String file_url) {
        new FetchUsers(managePlayersActivity).execute();
    }
}
