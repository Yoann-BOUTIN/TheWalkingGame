package com.thewalkinggame.app.gestionJeu.FindTheKey;

import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by law on 26/05/2014.
 *
 * Permet de récuperer les données du jeu FindTheKey
 */
public class FetchDataFindKey extends AsyncTask<String,String,String> {
    /**
     * Activity de SplashFindTheKey
     */
    private SplashFindTheKey splashFindTheKey;
    /**
     * id du jeu
     */
    private String id;
    /**
     * Temps autorisé qu'à le joueur pour
     * réussir le défi.
     */
    private int time_limite;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private String url_fetch_data_finkey = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/findkeys/id";
    /**
     * Json object
     */
    private JSONObject json;
    /**
     * Json parser
     */
    private JSONParser jsonParser = new JSONParser();
    /**
     * Liste de parametres permettant d'obtenir
     * les données voulue.
     */
    List<NameValuePair> params;

    /**
     * Constructeur de la classe FetchDataFindKey
     *
     * @param splashFindTheKey object SplashFindTheKey
     */
    public FetchDataFindKey(SplashFindTheKey splashFindTheKey)
    {
        this.splashFindTheKey = splashFindTheKey;
        id = "1";
    }

    @Override
    protected String doInBackground(String... args) {
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id",id));

        // getting JSON string from URL
        json = jsonParser.makeHttpRequest(url_fetch_data_finkey, "GET", params);

        try {
            time_limite = json.getInt("timeLimite");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        splashFindTheKey.setTotalTime(time_limite);
        splashFindTheKey.isFetchDataTerminated(true);
    }
}
