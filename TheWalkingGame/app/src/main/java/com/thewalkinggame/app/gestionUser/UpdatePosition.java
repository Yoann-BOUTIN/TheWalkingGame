package com.thewalkinggame.app.gestionUser;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.MapFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marc on 14/04/2014.
 */
public class UpdatePosition extends AsyncTask<String, String, String> {

    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     * Pour update la position du joueur
     */
    private static final String url_update_pos_user = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/user/updateUser";

    JSONParser jsonParser = new JSONParser();
    JSONObject params = new JSONObject();
    private MapFragment mapFragment;
    private double posLongitude;
    private double posLatitude;
    private SharedPreferences settings;
    private String memoire;
    private int userId;

    public UpdatePosition(MapFragment mapFragment){
        this.mapFragment = mapFragment;
        this.settings = PreferenceManager.getDefaultSharedPreferences(mapFragment.getActivity().getBaseContext());
        this.memoire = settings.getString("pseudo","");
    }

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        // On va chercher l'id de l'utilisateur qui a pour pseudo
        // la string de la variable memoire.
        new GetIdOf(memoire, UpdatePosition.this).execute();
    }

    /**
     * Update product
     */
    protected String doInBackground(String... args) {
        posLongitude = mapFragment.getUserLocation().getLongitude();
        posLatitude = mapFragment.getUserLocation().getLatitude();

        // Building Parameters
        try {
            params.put("id", userId);
            params.put("userPseudo", memoire);
            params.put("userPosLongitude", posLongitude + "");
            params.put("userPosLatitude", posLatitude + "");
        } catch (JSONException e) {
            e.printStackTrace();
        };

        // getting JSON Object
        // Note that create product url accepts POST method
        jsonParser.makeHttpPostRequest(url_update_pos_user, "PUT", params);
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * *
     */
    protected void onPostExecute(String file_url) {
    }

    public void setUserId(int id){
        userId = id;
    }
}



