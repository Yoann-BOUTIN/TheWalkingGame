package com.thewalkinggame.app.gestionJeu.Reanimer;

import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 08/05/2014.
 *
 * Permet de récuperer les données du jeu Reanimer en BDD.
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
     * Ativity correspondant à celle SplashReanimer
     */
    SplashReanimer splashReanimerActivity;
    /**
     * Le score à atteindre pour réussir
     * le défi
     */
    int scoreTarget;
    /**
     * Le temps autorisé pour le défi.
     */
    int tempsAutorise;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private final static String url_load_score_target = "http://86.203.95.231:8080/thewalkinggame-0.0.1-SNAPSHOT/reanimer/id";

    /**
     * Constructeur de la class RecupGameData
     *
     * @param splashReanimerActivity Ativity correspondant à celle SplashReanimer
     */
    RecupGameData(SplashReanimer splashReanimerActivity){
        this.splashReanimerActivity = splashReanimerActivity;
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
        json = jParser.makeHttpRequest(url_load_score_target, "GET", params);
        try {
            scoreTarget = json.getInt("scoreTarget");
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
        splashReanimerActivity.setScoreTarget(scoreTarget);
        splashReanimerActivity.setTotalTime(tempsAutorise);
        splashReanimerActivity.setRecupDataTerminated(true);
    }
}