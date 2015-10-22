package com.thewalkinggame.app.gestionUser;

import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 21/05/2014.
 *
 * Permet d'obtenir l'id d'un joueur via son pseudo.
 */
public class GetIdOf extends AsyncTask<String, String, String>{

    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static final String url_get_id_user = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/user/pseudo";
    /**
     * Json parser
     */
    JSONParser jsonParser = new JSONParser();
    /**
     * Liste des parametres permettant d'obtenir
     * les données voulue.
     */
    List<NameValuePair> params = null;
    /**
     * Object UpdatePosition
     */
    private UpdatePosition updatePosition;
    /**
     * L'id du joueur
     */
    private int userId;
    /**
     * Le pseudo du joueur
     */
    private String userPseudo;

    /**
     * Constructeur de la classe GetIdOf
     *
     * @param memoire Pseudo de l'utilisateur
     * @param updatePosition Object UpdatePosition
     */
    public GetIdOf(String memoire, UpdatePosition updatePosition){
        this.updatePosition = updatePosition;
        this.userPseudo = memoire;
    }

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    /**
    * Get product
    */
    protected String doInBackground(String... args) {
        // Building Parameters
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userPseudo", userPseudo+""));

        // getting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_get_id_user, "GET", params);
        try {
            userId = json.getInt("id");
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
        updatePosition.setUserId(userId);
    }
}
