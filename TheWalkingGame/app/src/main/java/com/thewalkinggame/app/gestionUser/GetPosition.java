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
 * Created by Marc on 25/05/2014.
 *
 * Permet d'obtenir la position d'un joueur
 */
public class GetPosition extends AsyncTask<String, String, String> {

    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     * Pour obtenir la position du joueur.
     */
    private static final String url_get_pos_user = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/user/pseudo";
    /**
     * Json parser
     */
    JSONParser jsonParser = new JSONParser();
    /**
     * La liste des parametres permettant
     * d'obtenir les données voulues.
     */
    List<NameValuePair> params = null;
    /**
     * La position en longitude du joueur.
     */
    private double posLongitude;
    /**
     * La position en latitude du joueur.
     */
    private double posLatitude;
    /**
     * Le pseudo du joueur.
     */
    private String userPseudo;
    /**
     * L'instance userLocation
     */
    private UserLocation userLocation;

    /**
     * Constructeur de la class GetPosition
     *
     * @param userLocation L'instance de l'object UserLocation
     */
    public GetPosition(UserLocation userLocation){
        this.userPseudo = userLocation.getUserPseudo();
        this.userLocation = userLocation;
    }

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Update product
     */
    protected String doInBackground(String... args) {

        // Building Parameters
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("userPseudo", userPseudo+""));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_get_pos_user, "GET", params);
        try {
            posLongitude = json.getDouble("userPosLongitude");
            posLatitude = json.getDouble("userPosLatitude");
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
        userLocation.setLongitude(posLongitude);
        userLocation.setLatitude(posLatitude);
    }
}