package com.thewalkinggame.app.gestionJeu.JeuSimon;

import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 23/05/2014.
 *
 * Permet de récuperer les données du jeu Simon en BDD.
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
     * Object SimonGameActivity
     */
    SimonGameActivity simonGameActivity;
    /**
     * taille de la sequence
     */
    int seq;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private final static String url_get_simon_data = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/simon/id";

    /**
     * Constructeur de la class RecupGameData
     *
     * @param simonGameActivity Object SimonGameActivity
     */
    RecupGameData(SimonGameActivity simonGameActivity){
        this.simonGameActivity = simonGameActivity;
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
        json = jParser.makeHttpRequest(url_get_simon_data, "GET", params);
        try {
            seq = json.getInt("seqLength");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        simonGameActivity.setSeqLength(seq);
        simonGameActivity.setRecupDataTerminated(true);
    }
}