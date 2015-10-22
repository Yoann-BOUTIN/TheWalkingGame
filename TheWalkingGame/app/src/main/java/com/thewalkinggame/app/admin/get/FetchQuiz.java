package com.thewalkinggame.app.admin.get;

import android.os.AsyncTask;
import android.util.Log;

import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.admin.ManageQuizActivity;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kevin on 07/05/2014.
 *
 * Permet de récuperer La liste des quizs
 */
public class FetchQuiz extends AsyncTask<String,String,String> {

    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private String  url_fetch_quiz = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/quiz/all";
    /**
     * Liste qui contient une liste de questions
     * et de réponses.
     */
    private LinkedList<HashMap<String, String>> linkedList;
    /**
     * Contient un couple de questions ou reponses.
     */
    private HashMap<String,String> hashMap ;
    /**
     * Activity correspondant à ManageQuizActivity
     */
    private ManageQuizActivity manageQuizActivity;
    /**
     * Json object
     */
    private JSONObject json;
    /**
     * Json parser
     */
    private JSONParser jsonParser = new JSONParser();
    /**
     * Json array
     */
    private JSONArray jsonArray;
    /**
     * Json object
     */
    private JSONObject jsonObject;

    /**
     * Constructeur de la classe FecthQuiz
     *
     * @param manageQuizActivity Activity correspondant à ManageQuizActivity
     */
    public FetchQuiz(ManageQuizActivity manageQuizActivity) {
        this.manageQuizActivity = manageQuizActivity;
        linkedList              = new LinkedList<HashMap<String, String>>();
    }

    @Override
    protected String doInBackground(String... args) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        // getting JSON string from URL
        json = jsonParser.makeHttpRequest(url_fetch_quiz, "GET", params);
        int nbQuestion = 0;
        try {
            jsonArray = json.getJSONArray("quiz");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("FetchQuiz", "Error : no JSONArray given" + e.toString());
        }
        int jsonLength = jsonArray.length();
        try {
            for(int i=0;i<jsonLength;i++)
            {
                jsonObject = jsonArray.getJSONObject(i);

                JSONObject question = jsonObject.getJSONObject("question");
                JSONArray answers = jsonObject.getJSONArray("answers");

                hashMap = new HashMap<String, String>();

                String question_text = question.getString("questionText");
                String idQuestion = String.valueOf(question.getInt("id"));

                hashMap.put("question",question_text);
                hashMap.put("questionId",idQuestion);

                nbQuestion++;

                linkedList.add(hashMap);
                for(int k = 0;k<answers.length();k++)
                {
                    hashMap = new HashMap<String, String>();
                    JSONObject jsonObjectAnswers = answers.getJSONObject(k);

                    String answer = jsonObjectAnswers.getString("answerText");
                    hashMap.put("answer",answer);

                    linkedList.add(hashMap);
                }
            }
            Log.d("LINKED ",linkedList.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        manageQuizActivity.show_quiz(linkedList);
    }
}
