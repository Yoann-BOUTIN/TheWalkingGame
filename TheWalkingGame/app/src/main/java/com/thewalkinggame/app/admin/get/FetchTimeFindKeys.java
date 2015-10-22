package com.thewalkinggame.app.admin.get;

import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.admin.ManageFindKeysActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 25/05/2014.
 *
 * Permet de récupérer le temps autorisé pour le jeu
 * FindTheKeys.
 */
public class FetchTimeFindKeys extends AsyncTask<String,String,String> {

    /**
     * Activity correspondante à celle de ManageFindKeysActivity
     */
    private ManageFindKeysActivity manageFindKeysActivity;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private String url_fetch_time_limite = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/findkeys/id";
    /**
     * Id du jeu en BDD
     */
    private String id;
    /**
     * Temps autorisé pour le jeu
     */
    private int time_limite;
    /**
     * Json object
     */
    private JSONObject json;
    /**
     * Json parser
     */
    private JSONParser jsonParser = new JSONParser();
    /**
     * Liste de parametres qui permettent d'accéder aux
     * bons éléments en BDD
     */
    List<NameValuePair> params;

    /**
     * Constructeur de la class FetchTimeFindKeys
     *
     * @param manageFindKeysActivity Activity correspondante à ManageFindKeysActivity
     */
    public FetchTimeFindKeys(ManageFindKeysActivity manageFindKeysActivity){
        this.manageFindKeysActivity = manageFindKeysActivity;
        id = "1";
    }

    @Override
    protected String doInBackground(String... args) {
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id",id));

        // getting JSON string from URL
        json = jsonParser.makeHttpRequest(url_fetch_time_limite, "GET", params);

        try {
            time_limite = json.getInt("timeLimite");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        manageFindKeysActivity.display_time(String.valueOf(time_limite));
    }
}
