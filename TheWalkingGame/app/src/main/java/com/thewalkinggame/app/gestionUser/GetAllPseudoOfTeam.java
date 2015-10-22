package com.thewalkinggame.app.gestionUser;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.MapFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 25/05/2014.
 *
 * Permet d'obtenir tous les pseudo des joueurs
 * d'une équipe.
 */
public class GetAllPseudoOfTeam extends AsyncTask<String, String, String> {

    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static final String url_get_all_pseudo_of_team = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/byPseudo";
    /**
     * Json parser
     */
    JSONParser jsonParser = new JSONParser();
    /**
     * Liste de parametres permettant d'obtenir
     * des données voulue en BDD.
     */
    List<NameValuePair> params = null;
    /**
     * Le MapFragment
     */
    private MapFragment mapFragment;

    /***** Les pseudo des joueurs *****/
    private String userPseudo;
    private String pseudoJ1;
    private String pseudoJ2;
    private String pseudoJ3;
    private String pseudoJ4;
    /**
     * La progressDialog qui va s'afficher à l'écran
     * le temps de la récuperation des données
     */
    private ProgressDialog pDialog;

    /**
     * Constructeur de la class GetAllPseudoOfTeam
     *
     * @param memoire Le pseudo de l'utilisateur courant
     * @param mapFragment La MapFragment
     */
    public GetAllPseudoOfTeam(String memoire, MapFragment mapFragment){
        this.mapFragment = mapFragment;
        this.userPseudo = memoire;
    }

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        pDialog = new ProgressDialog(mapFragment.getActivity());
        pDialog.setMessage("Localisation des survivants en cours");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /**
     * Get product
     */
    protected String doInBackground(String... args) {
        // Building Parameters
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("pseudo", userPseudo+""));

        // getting JSON Object
        JSONObject json = jsonParser.makeHttpRequest(url_get_all_pseudo_of_team, "GET", params);
        try {
            pseudoJ1 = json.getString("joueur1");
            pseudoJ2 = json.getString("joueur2");
            pseudoJ3 = json.getString("joueur3");
            pseudoJ4 = json.getString("joueur4");
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
        ArrayList<String> pseudo = new ArrayList<String>();
        if(!pseudoJ1.toLowerCase().equals(userPseudo.toLowerCase())){
            pseudo.add(pseudoJ1);
        }
        if(!pseudoJ2.toLowerCase().equals(userPseudo.toLowerCase())){
            pseudo.add(pseudoJ2);
        }
        if(!pseudoJ3.toLowerCase().equals(userPseudo.toLowerCase())){
            pseudo.add(pseudoJ3);
        }
        if(!pseudoJ4.toLowerCase().equals(userPseudo.toLowerCase())){
            pseudo.add(pseudoJ4);
        }
        mapFragment.setPseudoJ2(pseudo.get(0));
        mapFragment.setPseudoJ3(pseudo.get(1));
        mapFragment.setPseudoJ4(pseudo.get(2));
        mapFragment.setUserLocationJ2(new UserLocation(pseudo.get(0)));
        mapFragment.setUserLocationJ3(new UserLocation(pseudo.get(1)));
        mapFragment.setUserLocationJ4(new UserLocation(pseudo.get(2)));
        pDialog.dismiss();
    }
}
