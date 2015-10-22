package com.thewalkinggame.app.admin.get;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.admin.ManageHeadUpsideDownActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 19/05/2014.
 *
 * Permet de récuperer les données du jeu HeadUpsideDown en BDD.
 */
public class GetHeadUpsideDownData extends AsyncTask<String, String, String> {
    /**
     * Json parser
     */
    JSONParser jsonParser = new JSONParser();
    /**
     * Liste de parametres permettant la récupération
     * des données voulue en BDD.
     */
    List<NameValuePair> params = null;
    /**
     * La progressDialog qui va s'afficher à l'écran
     * le temps de la récupération des données
     */
    private ProgressDialog pDialog;
    /**
     * Activity correspondant à ManageHeadUpsideDownActivity
     */
    private ManageHeadUpsideDownActivity manageHeadUpsideDownActivity;
    /**
     * L'EditText correspondant au temps autorisé pour
     * le jeu HeadUpsideDown.
     */
    private EditText editTextTimer;
    /**
     * Le temps autorisé pour le jeu HeadUpsideDown
     */
    private int timer;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static final String url_get_head_upside_down_data = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/headupsidedown/id";

    /**
     * Constructeur de la class GetHeadUpsideDownData
     *
     * @param manageHeadUpsideDownActivity Activity correspondant à ManageHeadUpsideDownActivity
     */
    public GetHeadUpsideDownData(ManageHeadUpsideDownActivity manageHeadUpsideDownActivity) {
        this.manageHeadUpsideDownActivity = manageHeadUpsideDownActivity;
        editTextTimer = (EditText) manageHeadUpsideDownActivity.findViewById(R.id.edit_text_head_upside_down_temps_autorise);
        timer = 0;
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(manageHeadUpsideDownActivity);
        pDialog.setMessage("Recuperation des données du jeu");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * Update product
     */
    protected String doInBackground(String... args) {
        // Building Parameters
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", 1+""));

        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_get_head_upside_down_data, "GET", params);
        try {
            timer = json.getInt("tempsAutorise");
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
        editTextTimer.setText(""+timer);
        // dismiss the dialog after getting all products
        pDialog.dismiss();
    }
}
