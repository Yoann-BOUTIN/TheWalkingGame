package com.thewalkinggame.app.admin.update;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.admin.ManageHeadUpsideDownActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marc on 19/05/2014.
 *
 * Permet d'update les données du jeu HeadUpsideDown en BDD.
 */
public class UpdateHeadUpsideDownTimer extends AsyncTask<String, String, String> {
    /**
     * Json parser
     */
    JSONParser jsonParser = new JSONParser();
    /**
     * Liste de parametres permettant l'update
     * des données voulue en BDD.
     */
    JSONObject params = new JSONObject();
    /**
     * La progressDialog qui va s'afficher à l'écran
     * le temps de l'update des données
     */
    private ProgressDialog pDialog;
    /**
     * Activity correspondant à ManageHeadUpsideDownActivity
     */
    private ManageHeadUpsideDownActivity manageHeadUpsideDownActivity;
    /**
     * Nombres de secondes autorisées pour le jeu
     */
    private int timer;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static final String url_update_new_timer = "http://86.203.95.231:8080/thewalkinggame-0.0.1-SNAPSHOT/headupsidedown/update";

    /**
     * Constructeur de la class UpdateHeadUpsideDownTimer
     *
     * @param manageHeadUpsideDownActivity Activity correspondant à ManageHeadUpsideDownActivity
     * @param timer Nombre de secondes autorisées pour le jeu
     */
    public UpdateHeadUpsideDownTimer(ManageHeadUpsideDownActivity manageHeadUpsideDownActivity, int timer) {
        this.manageHeadUpsideDownActivity = manageHeadUpsideDownActivity;
        this.timer = timer;
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(manageHeadUpsideDownActivity);
        pDialog.setMessage("Update en cours...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * Update product
     */
    protected String doInBackground(String... args) {

        // Building Parameters
        try {
            params.put("id", 1 + "");
            params.put("tempsAutorise", timer + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // getting JSON Object
        // Note that update product url accepts PUT method
        jsonParser.makeHttpPostRequest(url_update_new_timer, "PUT", params);
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * *
     */
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();
        Toast.makeText(manageHeadUpsideDownActivity, "Update effectuee", Toast.LENGTH_SHORT).show();
    }
}