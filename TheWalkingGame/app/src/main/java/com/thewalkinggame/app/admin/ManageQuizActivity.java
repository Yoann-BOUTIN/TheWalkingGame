package com.thewalkinggame.app.admin;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.thewalkinggame.app.R;
import com.thewalkinggame.app.admin.delete.DeleteQuiz;
import com.thewalkinggame.app.admin.get.FetchQuiz;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Activity dans laquelle l'admin peut modifier
 * les données des quizs.
 */
public class ManageQuizActivity extends Activity {
    /**
     * LinearLayout
     */
    LinearLayout linearLayoutInScrollView, linearLayoutDisplayHorizontal = null;
    /**
     * Liste des quizs
     */
    LinkedList<HashMap<String,String>> linkedList;
    /**
     * La scrollView pour afficher tous les
     * quizs à l'écran
     */
    ScrollView scrollView = null;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_quiz);
        scrollView = (ScrollView)findViewById(R.id.scrollViewQuiz);
        linearLayoutInScrollView = new LinearLayout(this);
        new FetchQuiz(ManageQuizActivity.this).execute();
    }

    /**
     * Permet l'affichage de tous les quizs
     *
     * @param linkedList Liste de tous les quizs
     */
    public void show_quiz(LinkedList<HashMap<String, String>> linkedList) {
        scrollView.removeAllViews();
        linearLayoutInScrollView.removeAllViews();
        this.linkedList = linkedList;

        linearLayoutInScrollView.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams layoutQuestion = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutQuestion.setMargins(0,20,0,0);

        for(int i = 0; i < linkedList.size(); i += 5)
        {
//            // The inner LinearLayout for the horizontal display
            linearLayoutDisplayHorizontal = new LinearLayout(this);
            linearLayoutDisplayHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutDisplayHorizontal.setLayoutParams(layoutQuestion);
//            // The textView to say it's a question
            TextView textToSayQuestion = new TextView(this);
            textToSayQuestion.setText("Question "+linkedList.get(i).get("questionId")+": ");
            textToSayQuestion.setTextSize(20);
            textToSayQuestion.setGravity(View.TEXT_ALIGNMENT_CENTER);
            textToSayQuestion.setTextColor(Color.WHITE);
//            // The EditText for the Question and its params
            EditText editTextQuestion = new EditText(this);
            editTextQuestion.setTextSize(15);
            editTextQuestion.setMaxLines(3);
            editTextQuestion.setText(linkedList.get(i).get("question"));
            editTextQuestion.setTextColor(Color.WHITE);
            editTextParams.setMargins(0, 0, 0, 0);
            editTextParams.weight=2;
            editTextQuestion.setLayoutParams(editTextParams);
             // Button to delete the question
            Button deleteButton = new Button(this);
            deleteButton.setBackgroundResource(android.R.drawable.ic_menu_close_clear_cancel);
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setText(linkedList.get(i).get("questionId"));
            deleteButton.setTextSize(0);
            deleteButton.setOnClickListener(deleteQuiz);
//            // Addition in the LinearLayout Horizontal
            linearLayoutDisplayHorizontal.addView(textToSayQuestion);
            linearLayoutDisplayHorizontal.addView(editTextQuestion);
            linearLayoutDisplayHorizontal.addView(deleteButton);
            // addition in linearScrollView
            linearLayoutInScrollView.addView(linearLayoutDisplayHorizontal);
//            // textViews for answer 1
            linearLayoutDisplayHorizontal = new LinearLayout(this);
            linearLayoutDisplayHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            TextView textViewanswer1 = new TextView(this);
            textViewanswer1.setText("Réponse 1 : ");
            textViewanswer1.setTextSize(16);
            textViewanswer1.setTextColor(Color.WHITE);
//            // editText for answer 1 and it's params
            EditText answer1 = new EditText(this);
            answer1.setText(linkedList.get(i+1).get("answer"));
            answer1.setTextColor(Color.WHITE);
            linearLayoutDisplayHorizontal.addView(textViewanswer1);
            linearLayoutDisplayHorizontal.addView(answer1);
//            // addition in linearScrollView
            linearLayoutInScrollView.addView(linearLayoutDisplayHorizontal);
//            // textViews for answer 2 and params
            linearLayoutDisplayHorizontal = new LinearLayout(this);
            linearLayoutDisplayHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            TextView textViewanswer2 = new TextView(this);
            textViewanswer2.setText("Réponse 2 : ");
            textViewanswer2.setTextSize(16);
            textViewanswer2.setTextColor(Color.WHITE);
//            // editText for answer 2 and it's params
            EditText answer2 = new EditText(this);
            answer2.setText(linkedList.get(i+2).get("answer"));
            answer2.setTextColor(Color.WHITE);
            linearLayoutDisplayHorizontal.addView(textViewanswer2);
            linearLayoutDisplayHorizontal.addView(answer2);
//            // addition in linearScrollView
            linearLayoutInScrollView.addView(linearLayoutDisplayHorizontal);
//            // textViews for answer 3 and params
            linearLayoutDisplayHorizontal = new LinearLayout(this);
            linearLayoutDisplayHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            TextView textViewanswer3 = new TextView(this);
            textViewanswer3.setText("Réponse 3 : ");
            textViewanswer3.setTextSize(16);
            textViewanswer3.setTextColor(Color.WHITE);
//            // editText for answer 3 and it's params
            EditText answer3 = new EditText(this);
            answer3.setText(linkedList.get(i+3).get("answer"));
            answer3.setTextColor(Color.WHITE);
            linearLayoutDisplayHorizontal.addView(textViewanswer3);
            linearLayoutDisplayHorizontal.addView(answer3);
//            // addition in linearScrollView
            linearLayoutInScrollView.addView(linearLayoutDisplayHorizontal);
//            // textViews for answer 4 and params
            linearLayoutDisplayHorizontal = new LinearLayout(this);
            linearLayoutDisplayHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            TextView textViewanswer4 = new TextView(this);
            textViewanswer4.setText("Réponse 4 : ");
            textViewanswer4.setTextSize(16);
            textViewanswer4.setTextColor(Color.WHITE);
//            // editText for answer 4 and it's params
            EditText answer4 = new EditText(this);
            answer4.setText(linkedList.get(i+4).get("answer"));
            answer4.setTextColor(Color.WHITE);
            linearLayoutDisplayHorizontal.addView(textViewanswer4);
            linearLayoutDisplayHorizontal.addView(answer4);
//            // addition in linearScrollView
            linearLayoutInScrollView.addView(linearLayoutDisplayHorizontal);
        }
        scrollView.addView(linearLayoutInScrollView);
    }

    /**
     * Listener qui permet de supprimer un quiz.
     */
    View.OnClickListener deleteQuiz = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button deleteButton = (Button) v;
            new DeleteQuiz(ManageQuizActivity.this,(String) deleteButton.getText()).execute();
        }
    };
}