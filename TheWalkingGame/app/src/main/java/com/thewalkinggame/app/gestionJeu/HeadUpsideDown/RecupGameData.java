package com.thewalkinggame.app.gestionJeu.HeadUpsideDown;

import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 12/05/2014.
 *
 * Permet de récuperer les données du jeu HeadUpsideDown en BDD.
 */
public class RecupGameData extends AsyncTask<String,String,String> {
    /**
     * Json parser
     */
    JSONParser jParser = new JSONParser();
    /**
     * Json object
     */
    JSONObject json;
    /**
     * instance de l'activity SplashHeadUpsideDown
     */
    SplashHeadUpsideDown splashHeadUpsideDown;
    /**
     * Temps autorisé qu'à le joueur pour
     * réussir le défi.
     */
    int tempsAutorise;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private final static String url_load_total_time = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/headupsidedown/id";

    /**
     * Constructeur de la class RecupGameData
     *
     * @param splashHeadUpsideDown instance de l'activity SplashHeadUpsideDown
     */
    RecupGameData(SplashHeadUpsideDown splashHeadUpsideDown){
        this.splashHeadUpsideDown = splashHeadUpsideDown;
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
        params.add(new BasicNameValuePair("id", ""+1));
        // getting JSON string from URL
        json = jParser.makeHttpRequest(url_load_total_time, "GET", params);
        try {
            tempsAutorise = json.getInt("tempsAutorise");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        splashHeadUpsideDown.setTotalTime(tempsAutorise);
        splashHeadUpsideDown.setRecupDataTerminated(true);
    }
}