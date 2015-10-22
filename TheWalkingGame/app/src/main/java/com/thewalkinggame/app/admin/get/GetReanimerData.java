package com.thewalkinggame.app.admin.get;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.EditText;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.admin.ManageReanimerActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 01/05/2014.
 *
 * Permet de récuperer les données du jeu Reanimer en BDD.
 */
public class GetReanimerData extends AsyncTask<String, String, String> {
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
     * Activity correspondant à ManagerReanimerActivity
     */
    private ManageReanimerActivity manageReanimerActivity;
    /**
     * L'EditText correspondant au Score à atteindre pour
     * réussir le jeu.
     */
    private EditText editTextScore;
    /**
     * L'EditText correspondant au temps autorisé
     * pour le jeu.
     */
    private EditText editTextTimer;
    /**
     * Le score à atteindre pour
     * réussir le jeu.
     */
    private int score;
    /**
     * Le temps autorisé pour
     * le jeu.
     */
    private int timer;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static final String url_get_reanimer_data = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/reanimer/id";

    /**
     * Constructeur de la class GetReanimerData
     *
     * @param manageReanimerActivity Activity correspondant à ManageReanimerActivity
     */
    public GetReanimerData(ManageReanimerActivity manageReanimerActivity) {
        this.manageReanimerActivity = manageReanimerActivity;
        editTextScore = (EditText) manageReanimerActivity.findViewById(R.id.edit_text_reanimer_score_a_atteindre);
        editTextTimer = (EditText) manageReanimerActivity.findViewById(R.id.edit_text_reanimer_temps_autorise);
        score = 0;
        timer = 0;
    }

    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pDialog = new ProgressDialog(manageReanimerActivity);
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
        JSONObject json = jsonParser.makeHttpRequest(url_get_reanimer_data, "GET", params);
        try {
            timer = json.getInt("tempsAutorise");
            score = json.getInt("scoreTarget");
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
        editTextScore.setText(""+score);
        // dismiss the dialog after getting all products
        pDialog.dismiss();
    }
}
