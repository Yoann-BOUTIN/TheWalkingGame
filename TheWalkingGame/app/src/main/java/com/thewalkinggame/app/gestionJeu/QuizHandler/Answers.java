package com.thewalkinggame.app.gestionJeu.QuizHandler;

import android.os.AsyncTask;
import android.util.Log;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kevin on 08/04/2014.
 * class that build an ArrayList of Answers
 * This class allows also to setText for Answers in QuizActivity
 */
public class Answers {
    /**
     * URL where requests are send.
     */
    private final String url_load_answers = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/quiz/id";
    /**
     * A quiz object
     */
    private final Quiz quiz;
    /**
     *A JsonParson object : to Build a httprequest
     */
    private JSONParser jParser = new JSONParser();
    /**
     * ArrayList of answers, to build a Quiz
     */
    private ArrayList<Answer> answers = new ArrayList<Answer>();

    /**
     * Constructor
     * @param quiz
     */
    public Answers(Quiz quiz)
    {
        this.quiz = quiz;
        new LoadAnswers().execute();
    }

    /**
     * Function that return an ArraysList of Answer objects
     * @return ArraysList<Answer>
     */
    public ArrayList<Answer> getAnswers(){return this.answers;}

    /**
     * Function to count the number of good answers.
     * This function is use for Quiz that has
     * multiple good answers
     *
     * @return int: number of good answers
     */
    public int countGoodAnswers()
    {
        int i = 0;
        for(Answer ans : answers)
        {
            if(ans.isGoodAnswer())
            {
                i++;
            }
        }
        return i;
    }

    /**
     * Inner class that build a request for the server
     * This class allows to load answers from the database
     * with a question id
     */
    class LoadAnswers extends AsyncTask<String,String,String>
    {
        List<NameValuePair> params = null;

        /**
         * Build and execute the httpRequest
         * @param args
         * @return String
         */
        protected String doInBackground(String... args) {

            // Building Parameters
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", quiz.getQuestion().getQuestionId()+""));

            // getting JSON Object
            // Note that create product url accepts GET method
            JSONObject json = jParser.makeHttpRequest(url_load_answers, "GET", params);
            try {
                JSONArray answersArray = json.getJSONArray("answers");
                for(int i = 0; i < answersArray.length(); i++)
                {
                    JSONObject jsonObject = answersArray.getJSONObject(i);
                    answers.add(new Answer(jsonObject.getInt("id"),jsonObject.getString("answerText"),jsonObject.getInt("correctAnswer")==1));
                }
                Collections.shuffle(answers);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Function executed after the httpRequest
         * @param s: String
         *
         * This function call an other function in Quiz that allows to
         * show answers text in QuizActivity
         * We  call setAnswersText here to ensure tat data are already retrieved
         */
        @Override
        protected void onPostExecute(String s) {
            quiz.setAnswersText();
        }
    }
}