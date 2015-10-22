package com.thewalkinggame.app.gestionUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.MainActivity;
import com.thewalkinggame.app.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zizou on 26/03/2014.
 *
 * Gere la connexion/création d'un utilisateur.
 */
public class ConnexionActivity extends Activity {
    /**
     * EditText dans lequel l'utilisateur
     * entre son pseudo.
     */
    EditText enterPseudo = null;
    /**
     * Bouton de confirmation
     */
    Button confirmer = null;
    /**
     * Preferences
     */
    SharedPreferences settings;
    /**
     * Editer les preferences
     */
    SharedPreferences.Editor editor;
    /**
     * object user
     */
    User user = null;
    /**
     * pseudo de l'utilisateur
     */
    String pseudo = null;
    /**
     *
     */
    String memoire;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
      private static String url_create_user = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/user/newUser";
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
      private static String url_user_exist = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/user/exist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        enterPseudo = (EditText)findViewById(R.id.choixPseudo);
        confirmer = (Button)findViewById(R.id.confirmPseudo);

        confirmer.setOnClickListener(confirmerListener);
        settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        editor = settings.edit();
        memoire = settings.getString("pseudo","");

        new GetUserIsOnBdd().execute();
    }

    /**
     * listener qui permet la confirmation
     * du pseudo de l'utilisateur.
     */
    View.OnClickListener confirmerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pseudo = enterPseudo.getText().toString();
            user = new User(pseudo);
            if(user.isValid()){
                new UserExist().execute();
            }
            else{
                Toast.makeText(ConnexionActivity.this, "Veuillez entrer un pseudo valide !", Toast.LENGTH_SHORT).show();
            }



        }
    };

    class UserExist extends AsyncTask<String, String, String> {
        /**
         * La progressDialog qui va s'afficher à l'écran
         * le temps d'obtenir les données.
         */
        private ProgressDialog pDialog;
        /**
         * Json parser.
         */
        JSONParser jsonParser = new JSONParser();
        /**
         * Les parametres qui permettent d'obtenir
         * les données voulue.
         */
        List<NameValuePair> params = null;
        /**
         * True le pseudo existe
         */
        boolean exist;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(ConnexionActivity.this);
            pDialog.setMessage("Verification de la disponibilité du pseudo...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            // Building Parameters
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userPseudo", pseudo));

            JSONObject jsonExist = jsonParser.makeHttpRequest(url_user_exist, "GET", params);
            // getting JSON Object
            // Note that create product url accepts POST method
            try {
                exist = jsonExist.getBoolean("exist");
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
            // dismiss the dialog once done
            pDialog.dismiss();
            if(!exist){
                new CreateUser().execute();
            }
            else{
                Toast.makeText(ConnexionActivity.this, "Pseudo déjà utilisé. Veuillez en choisir un autre.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Permet la création d'un utilisateur
     * en BDD.
     */
    class CreateUser extends AsyncTask<String, String, String> {
        /**
         * La progressDialog qui va s'afficher à l'écran
         * le temps de la création de l'utilisateur
         */
        private ProgressDialog pDialog;
        /**
         * Json parser
         */
        JSONParser jsonParser = new JSONParser();
        /**
         * Les parametres qui permettent d'envoyer
         * en BDD les données voulue.
         */
        JSONObject params = new JSONObject();

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(ConnexionActivity.this);
            pDialog.setMessage("Création de l'utilisateur...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {

            // Building Parameters
            try {
                params.put("userPseudo", pseudo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonParser.makeHttpPostRequest(url_create_user, "POST", params);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            editor.putString("pseudo",pseudo);
            editor.commit();
            enterPseudo.setText("");
            Toast.makeText(ConnexionActivity.this, "Bonjour " + pseudo, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
            ConnexionActivity.this.finish();
        }
    }

    /**
     * Va voir si le pseudo du joueur
     * existe en BDD ou non.
     * S'il existe on ne fait rien,
     * s'il n'existe pas on l'emmene dans
     * ConnexionActivity afin qu'on puisse
     * l'ajouter en BDD.
     */
    class GetUserIsOnBdd extends AsyncTask<String,Integer,String> {

        JSONParser jParser = new JSONParser();
        boolean userExist;
        private final static String url_pseudo = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/user/exist";

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userPseudo", memoire));
            JSONObject json = jParser.makeHttpRequest(url_pseudo, "GET", params);
            try {
                userExist = json.getBoolean("exist");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            if (userExist){
                Toast.makeText(ConnexionActivity.this, "Re-bonjour " + memoire, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
                ConnexionActivity.this.finish();
            } else {
                // nothing
            }
        }
    }
}
