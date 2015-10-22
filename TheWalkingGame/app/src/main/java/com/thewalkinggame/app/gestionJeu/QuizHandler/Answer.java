package com.thewalkinggame.app.gestionJeu.QuizHandler;

/**
 * Created by kevin on 17/04/2014.
 *
 * Class allows to build an object Answer
 */
public class Answer {

    /**
     * id of one answer in db
     */
    private int id;
    /**
     * Text corresponding to an Answer
     */
    private String answer_text;
    /**
     * boolean that inform if it's a good answer or not
     */
    private boolean correct_answer;

    /**
     * function that return an id of answer in db
     * @return int: id of answer
     */
    public int getId(){return id;}

    /**
     * function that return if it's a good answer
     * @return boolean: is it a correct answer?
     */
    public boolean isGoodAnswer(){return correct_answer;}

    /**
     * function that return the text of an answer
     * @return string: text of answer
     */
    public String getAnswerText(){return answer_text;}

    /**
     * Constructor
     * @param id : int
     * @param answer_text : String
     * @param correct_answer : boolean
     */
    public Answer(int id, String answer_text, boolean correct_answer)
    {
        this.id = id;
        this.answer_text = answer_text;
        this.correct_answer = correct_answer;
    }
}
