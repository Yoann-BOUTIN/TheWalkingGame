package com.thewalkinggame.app.admin.get;

import android.os.AsyncTask;
import android.util.Log;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.admin.ManagePlayersActivity;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kevin on 08/05/2014.
 *
 * Permet la récupération des joueurs dans la BDD
 */
public class FetchUsers extends AsyncTask<String,String,String> {

    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static String url_fetch_users = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/user/allUsers";
    /**
     * Activity correspondant à ManagePlayersActivity
     */
    ManagePlayersActivity managePlayersActivity;
    /**
     * Liste de couple <pseudo, id>
     */
    private LinkedList<HashMap<String, String>> linkedList;
    /**
     * Couple <pseudo, id>
     */
    private HashMap<String, String> hashMap;
    /**
     * Json parser
     */
    private JSONParser jsonParser = new JSONParser();
    /**
     * Json object
     */
    private JSONObject json;
    /**
     * Liste de json
     */
    private JSONArray jsonArray;
    /**
     * Json object
     */
    private JSONObject jsonObject;

    /**
     * Constructeur de la class FetchUsers
     *
     * @param managePlayersActivity Activity correspondant à ManagePlayersActivity
     */
    public FetchUsers(ManagePlayersActivity managePlayersActivity)
    {
        this.managePlayersActivity  = managePlayersActivity;
        linkedList                  = new LinkedList<HashMap<String, String>>();
    }

    @Override
    protected String doInBackground(String... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // getting JSON string from URL
        json = jsonParser.makeHttpRequest(url_fetch_users, "GET", params);

        try {
            jsonArray = json.getJSONArray("users");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("FetchUser", "Error : no JSONArray given" + e.toString());
        }
        int jsonLength = jsonArray.length();
        try {
            for(int i=0;i<jsonLength;i++)
            {
                hashMap = new HashMap<String, String>();
                jsonObject = jsonArray.getJSONObject(i);

                String pseudo = jsonObject.getString("userPseudo");
                int idUser = jsonObject.getInt("id");

                hashMap.put("pseudo",pseudo);
                hashMap.put("delete",String.valueOf(idUser));

                linkedList.addFirst(hashMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        managePlayersActivity.showPlayers(linkedList);
    }
}
