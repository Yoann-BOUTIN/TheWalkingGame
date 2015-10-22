package com.thewalkinggame.app.admin.update;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.admin.ManageFindKeysActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kevin on 25/05/2014.
 *
 * Permet d'update les données du jeu FindTheKeys en BDD.
 */
public class UpdateTimeFindKeys extends AsyncTask<String,String,String> {
    /**
     * activity correspondante à ManageFindKeysActivity
     */
    private ManageFindKeysActivity manageFindKeysActivity;
    /**
     * Nombre de secondes autorisées pour le jeu
     */
    private String value;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private String url_update_time_limite = "http://86.203.95.231:8080/thewalkinggame-0.0.1-SNAPSHOT/findkeys/update";
    /**
     * La progressDialog qui va s'afficher à l'écran
     * le temps de l'update des données
     */
    private ProgressDialog pDialog;
    /**
     * Json parser
     */
    private JSONParser jsonParser = new JSONParser();
    /**
     * Liste de parametres permettant l'update
     * des données voulue en BDD.
     */
    JSONObject params = new JSONObject();

    /**
     * Constructeur de la class UpdateTimeFindKeys
     *
     * @param manageFindKeysActivity activity correspondante à ManageFindKeysActivity
     * @param value Nombre de secondes autorisées pour le jeu
     */
    public UpdateTimeFindKeys(ManageFindKeysActivity manageFindKeysActivity, String value) {
        this.manageFindKeysActivity = manageFindKeysActivity;
        this.value = value;
    }


    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(manageFindKeysActivity);
        pDialog.setMessage("Update en cours...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    protected String doInBackground(String... args) {

        // Building Parameters
        try {
            params.put("id", 1);
            params.put("timeLimite", Integer.parseInt(value));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Note that update product url accepts PUT method
        jsonParser.makeHttpPostRequest(url_update_time_limite, "PUT", params);
        return null;
    }

    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();
        manageFindKeysActivity.displayUpdateSucceed();
    }
}
