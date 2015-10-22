package com.thewalkinggame.app.gestionJeu.QuizHandler;

import android.os.AsyncTask;
import android.util.Log;

import com.thewalkinggame.app.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* Created by kevin on 08/04/2014.
 * class that build a Question object
*/
public class Question {

    /**
     * String
     * URL of our server in order to count number of quiz
     */
    private final String url_count_question = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/quiz/countQuiz";
    /**
     * String
     * URL of our server in order to fetch a Quesion
     */
    private final String url_load_question = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/quiz/id";
    /**
     * int
     * A question id
     */
    private int questionId;
    /**
     * String
     * A question texr
     */
    private String questionText;
    /**
     * boolean
     * Allorw to know if it's a question with multiple choice
     */
    private boolean multipleChoice;
    /**
     * JSONParser object
     */
    private JSONParser jParser = new JSONParser();
    /**
     * A quiz object
     */
    private Quiz quiz;
    /**
     * int
     * This number allows to choose a random quiz in database
     */
    private int numberQuiz;

    /**
     * Constructor of Question objects
     * @param quiz
     */
    public Question(Quiz quiz)
    {
        this.quiz = quiz;
        new CountQuestion().execute();
    }

    /**
     * Getters and Setters
     */
    public int getQuestionId() {return questionId;}
    public void setQuestionId(int id){questionId = id;}
    public String getQuestionText(){return questionText;}
    public void setQuestionText(String text){questionText = text;}
    public boolean isChoiceMultiple() {return multipleChoice;}
    public void setMultipleChoice(boolean multiple_choice){this.multipleChoice = multiple_choice;}

    /**
     * Class that allow to make a httpRequest in database to
     * count the number of Questions. This number allows us,
     * to fetch a random question in database
     */
    class CountQuestion extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... params) {

            // parameters to send to php script
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_count_question, "GET", parameters);

            // Check your log cat for JSON reponse
            try {
                numberQuiz = json.getInt("numberQuiz");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After we call a new asynchTask to fetch a Question
         */
        @Override
        protected void onPostExecute(String s) {
            new FetchQuestion().execute();
        }
    }

    /**
     * Class that fetch a random Question in database
     * thanks to the number of question calculated before
     */
    class FetchQuestion extends AsyncTask<String,String,String>
    {
        List<NameValuePair> parameters;
        int id = 0;
        @Override
        protected String doInBackground(String... params) {

            Random random = new Random();
            id = random.nextInt(numberQuiz)+1;

            // parameters to send to php script
            parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("id",String.valueOf(id)));
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_load_question, "GET", parameters);
            try {
                JSONObject jsonObject = json.getJSONObject("question");
                setQuestionId(Integer.parseInt(jsonObject.getString("id")));
                setQuestionText(jsonObject.getString("questionText"));
                setMultipleChoice(Integer.parseInt(jsonObject.getString("questionMultipleChoice")) == 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After fetching a question, we call an other
         * function to fetch answers corresponding to question id
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            quiz.findAnswers();
        }
    }
}
