package com.thewalkinggame.app.gestionTeam;

import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zizou on 01/06/2014.
 *
 * Permet de supprimer une équipe.
 */
public class DeleteTeam extends AsyncTask<Integer,Integer,Integer> {
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private final static String url_delete_equipe = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/delete";
    /**
     * Json parser.
     */
    JSONParser jParser = new JSONParser();


    @Override
    protected Integer doInBackground(Integer... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", String.valueOf(args[0])));
        JSONObject json = jParser.makeHttpRequest(url_delete_equipe, "DELETE", params);
        return null;
    }
}
