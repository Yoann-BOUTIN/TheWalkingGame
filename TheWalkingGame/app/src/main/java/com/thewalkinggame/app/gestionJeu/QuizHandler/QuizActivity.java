package com.thewalkinggame.app.gestionJeu.QuizHandler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thewalkinggame.app.MapFragment;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionUser.Etat;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kevin on 27/03/2014.
 * Activity for Quiz
 */
public class QuizActivity extends Activity
{
    /**
     * buttons for answers and validation
     */
    Button button_answer0, button_answer1, button_answer2, button_answer3, button_validation = null;
    /**
     * TextViews for answer_text and andvertisment of multiple answers
     */
    TextView question, advertise_multiple_choice;
    /**
     * Context for the activity
     */
    final Context context = this;
    /**
     * Etat in EN_DEFI during the game
     */
    private Etat etat = Etat.EN_DEFI;
    /**
     * the mapFragment object
     */
    private MapFragment mapFragment = null;
    /**
     * boolean that allows to know
     * if a first press was perfomed
     */
    private boolean firstPress = true;
    /**
     * A quiz object
     */
    private Quiz quiz;
    /**
     * ArrayList of ids for ids answers
     */
    private ArrayList<Integer> ids ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizfragment);

        button_answer0 = (Button)findViewById(R.id.button_ans1);
        button_answer1 = (Button)findViewById(R.id.button_ans2);
        button_answer2 = (Button)findViewById(R.id.button_ans3);
        button_answer3 = (Button) findViewById(R.id.button_ans4);
        button_validation = (Button)findViewById(R.id.button_validation);

        button_answer0.setOnClickListener(button_answer0_listener);
        button_answer1.setOnClickListener(button_answer1_listener);
        button_answer2.setOnClickListener(button_answer2_listener);
        button_answer3.setOnClickListener(button_answer3_listener);
        button_validation.setOnClickListener(validate);

        question = (TextView)findViewById(R.id.texview_question);


        ids = new ArrayList<Integer>();
        quiz = new Quiz(this);

    }

    public void onBackPressed(){
        Toast.makeText(QuizActivity.this, "La partie n'est pas terminée", Toast.LENGTH_LONG).show();
    }
    /**
     * Listener for Button Answer 1
     */
    View.OnClickListener button_answer0_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(firstPress)
            {
                button_answer0.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_a));
                ids.add(0);
                firstPress = false;
            }
            else
            {
                if(quiz.getQuestion().isChoiceMultiple())
                {
                    if(ids.contains(0))
                    {
                        ids.remove(ids.indexOf(0));
                        button_answer0.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_a));
                    }
                    else{
                        ids.add(0);
                        button_answer0.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_a));
                    }
                    Collections.sort(ids);
                }
                else
                {
                    switch (ids.get(0))
                    {
                        case 1:
                            button_answer1.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_b));
                            break;
                        case 2:
                            button_answer2.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_c));
                            break;
                        case 3:
                            button_answer3.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_d));
                            break;
                    }
                    ids.remove(0);
                    button_answer0.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_a));
                    ids.add(0);
                }
            }
        }
    };
    /**
     * Listener for Button Answer 2
     */
    View.OnClickListener button_answer1_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if(firstPress)
            {
                ids.add(1);
                button_answer1.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_b));
                firstPress = false;
            }
            else
            {
                if(quiz.getQuestion().isChoiceMultiple())
                {
                    if(ids.contains(1))
                    {
                        ids.remove(ids.indexOf(1));
                        button_answer1.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_b));
                    }
                    else{
                        ids.add(1);
                        button_answer1.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_b));
                    }
                    Collections.sort(ids);
                }
                else
                {
                    switch (ids.get(0))
                    {
                        case 0:
                            button_answer0.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_a));
                            break;
                        case 2:
                            button_answer2.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_c));
                            break;
                        case 3:
                            button_answer3.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_d));
                            break;
                    }
                    ids.remove(0);
                    button_answer1.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_b));
                    ids.add(1);
                }
            }
        }
    };
    /**
     * Listener for Button Answer 3
     */
    View.OnClickListener button_answer2_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if(firstPress)
            {
                ids.add(2);
                button_answer2.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_c));
                firstPress = false;
            }
            else
            {
                if(quiz.getQuestion().isChoiceMultiple())
                {
                    if(ids.contains(2))
                    {
                        ids.remove(ids.indexOf(2));
                        button_answer2.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_c));
                    }
                    else{
                        ids.add(2);
                        button_answer2.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_c));
                    }
                    Collections.sort(ids);
                }
                else
                {
                    switch (ids.get(0))
                    {
                        case 0:
                            button_answer0.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_a));
                            break;
                        case 1:
                            button_answer1.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_b));
                            break;
                        case 3:
                            button_answer3.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_d));
                            break;
                    }
                    ids.remove(0);
                    button_answer2.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_c));
                    ids.add(2);
                }
            }
        }
    };

    /**
     * Listener for Button Answer 4
     */
    View.OnClickListener button_answer3_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if(firstPress)
            {
                ids.add(3);
                button_answer3.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_d));
                firstPress = false;
            }
            else
            {
                if(quiz.getQuestion().isChoiceMultiple())
                {
                    if(ids.contains(3))
                    {
                        ids.remove(ids.indexOf(3));
                        button_answer3.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_d));
                    }
                    else{
                        ids.add(3);
                        button_answer3.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_d));
                    }
                    Collections.sort(ids);
                }
                else
                {
                    switch (ids.get(0))
                    {
                        case 0:
                            button_answer0.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_a));
                            break;
                        case 1:
                            button_answer1.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_b));
                            break;
                        case 2:
                            button_answer2.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_c));
                            break;
                    }
                    ids.remove(0);
                    button_answer3.setBackground(getResources().getDrawable(R.drawable.panneau_quizz_select_d));
                    ids.add(3);
                }
            }
        }
    };

    /**
     * Listener of validate button
     */
    View.OnClickListener validate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(ids.isEmpty())
            {
                etat.defiTermine(false);
                QuizActivity.this.finish();
                Toast.makeText(QuizActivity.this, "Mauvaise Réponse", Toast.LENGTH_LONG).show();
            }
            else {
                int erreur = 0;
                int reussi = 0;
                for (int id : ids) {
                    if (!quiz.isSucceed(quiz.getAnswers().getAnswers().get(id)))
                        erreur++;
                    else
                        reussi++;
                }
                if (erreur == 0 && reussi == quiz.getAnswers().countGoodAnswers()) {
                    Toast.makeText(QuizActivity.this, "Bonne Réponse", Toast.LENGTH_LONG).show();
                    etat.defiTermine(true);
                    QuizActivity.this.finish();
                } else {
                    etat.defiTermine(false);
                    QuizActivity.this.finish();
                    Toast.makeText(QuizActivity.this, "Mauvaise Réponse", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    /**
     * Allows to show advertisment that the question
     * can has multiple answers
     */
    public void activeAdvertisementMultipleChoice(){
        advertise_multiple_choice = (TextView)findViewById(R.id.multiple_choice_textview);
        advertise_multiple_choice.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.icon_caution),null,null,null);
        advertise_multiple_choice.setText("Il peut y avoir plusieurs réponses");
    }

    /**
     * Getters and Setters
     */
    public void setTextView(String text)
    {
        question.setText(text);
    }
    public void setButtonAnswersText(String ans1,String ans2,String ans3, String ans4)
    {
        button_answer0.setText(ans1);
        button_answer1.setText(ans2);
        button_answer2.setText(ans3);
        button_answer3.setText(ans4);
    }

    public CharSequence getTextQuestion(){
        return question.getText();
    }

    public CharSequence getAnswersText1(){
        return button_answer0.getText();
    }

    public CharSequence getAnswersText2(){
        return button_answer1.getText();
    }

    public CharSequence getAnswersText3(){
        return button_answer2.getText();
    }

    public CharSequence getAnswersText4(){
        return button_answer3.getText();
    }

    public Quiz getQuiz(){
        return quiz;
    }
}
