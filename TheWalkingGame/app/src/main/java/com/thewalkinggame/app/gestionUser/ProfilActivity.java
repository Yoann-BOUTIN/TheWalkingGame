package com.thewalkinggame.app.gestionUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Permet l'affichage du pseudo et du joueur ainsi que de son score.
 * Activity du profil du joueur.
 */
public class ProfilActivity extends Activity {
    /**
     * preferences settings
     */
    SharedPreferences settings;
    /**
     * preferences editor
     */
    SharedPreferences.Editor editor;
    /**
     * TextView correspondant
     * au pseudo du joueur
     */
    TextView view_pseudo;
    /**
     * TextView correspondant au
     * score du joueur
     */
    TextView view_score;
    /**
     * Le pseudo du joueur
     */
    String memoire;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private final static String url_load_info_user = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/user/pseudo";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        view_pseudo = (TextView)findViewById(R.id.pseudo);
        view_score = (TextView)findViewById(R.id.score);
        settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = settings.edit();
        memoire = settings.getString("pseudo","");
        new RecupInfo().execute();
    }

    /**
     * Permet de récuperer les informations liées
     * au joueur.
     */
    class RecupInfo extends AsyncTask<String,String,String>
    {
        /**
         * La progressDialog qui va s'afficher à l'écran
         * le temps de la récupération des données
         */
        private ProgressDialog pDialog;
        /**
         * Json parser
         */
        JSONParser jParser = new JSONParser();
        /**
         * Json object
         */
        JSONObject json;
        /**
         * Le pseudo du joueur
         */
        String pseudo;
        /**
         * Le score du joueur.
         */
        int score;
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfilActivity.this);
            pDialog.setMessage("Chargement du profil. Veuillez patienter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userPseudo", memoire));
            // getting JSON string from URL
            json = jParser.makeHttpRequest(url_load_info_user, "GET", params);
            try {
                pseudo = json.getString("userPseudo");
                score = json.getInt("userScore");

            } catch (JSONException e) {
                e.printStackTrace();
                Intent intent = new Intent(ProfilActivity.this, ConnexionActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
                ProfilActivity.this.finish();
            }
            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            view_pseudo.setText(pseudo);
            view_score.setText(String.valueOf(score));

        }
    }
}
