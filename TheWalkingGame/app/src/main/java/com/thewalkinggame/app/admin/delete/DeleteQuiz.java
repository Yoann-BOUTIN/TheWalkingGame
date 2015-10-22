package com.thewalkinggame.app.admin.delete;

import android.os.AsyncTask;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.admin.ManageQuizActivity;
import com.thewalkinggame.app.admin.get.FetchQuiz;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by law on 24/05/2014.
 *
 * Permet de supprimer un Quiz.
 */
public class DeleteQuiz extends AsyncTask<String,String,String> {

    /**
     * Les parametres qui vont nous permettre d'identifier
     * ce qui doit être supprimé dans la BDD.
     */
    List<NameValuePair> parameters;
    /**
     * Json object
     */
    JSONObject json = null;
    /**
     * Json parser
     */
    private JSONParser jParser;
    /**
     * Id du quiz à supprimer
     */
    private String idQuiz;
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private String url_deleteQuiz = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/quiz/delete";
    /**
     * Activity correspond à ManageQuizActivity
     */
    ManageQuizActivity manageQuizActivity;

    /**
     *
     * @param manageQuizActivity Activity correspond à ManageQuizActivity
     * @param id Id du quiz à supprimer
     */
    public DeleteQuiz(ManageQuizActivity manageQuizActivity, String id){
        this.manageQuizActivity = manageQuizActivity;
        idQuiz = id;
        jParser = new JSONParser();
    }

    @Override
    protected String doInBackground(String... params) {

        // parameters to send to php script
        parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("id",idQuiz));

        json = jParser.makeHttpRequest(url_deleteQuiz,"DELETE",parameters);
        return null;
    }

    protected void onPostExecute(String file_url) {
        new FetchQuiz(manageQuizActivity).execute();
    }
}
