package com.thewalkinggame.app.gestionJeu.QuizHandler;

/**
 * Created by kevin on 08/04/2014.
 * Class that represent a Quiz
 */
public class Quiz {

    /**
     * Object that represents the quizActivity
     */
    private final QuizActivity quizActivity;
    /**
     * Represent the Question
     */
    private Question question;
    /**
     * Represent Answers for a Question
     */
    private Answers answers;

    /**
     * Constructor for Quiz object
     * @param quizActivity
     */
    public Quiz(QuizActivity quizActivity)
    {
        this.quizActivity = quizActivity;
        question =  new Question(Quiz.this);
    }

    /**
     * Function that set the question text in TextView of Quiz activity
     * and fetch answers for this Question
     */
    public void findAnswers() {
        quizActivity.setTextView(question.getQuestionText());
        if(question.isChoiceMultiple()){quizActivity.activeAdvertisementMultipleChoice();}
        // recuperer les reponses
        answers = new Answers(Quiz.this);
    }

    /**
     * Getters and Setters
     */
    public Answers getAnswers(){return answers;}

    public void setAnswersText()
    {
        quizActivity.setButtonAnswersText(answers.getAnswers().get(0).getAnswerText(),answers.getAnswers().get(1).getAnswerText(),
                answers.getAnswers().get(2).getAnswerText(),answers.getAnswers().get(3).getAnswerText());
    }
    public boolean isSucceed(Answer ans)
    {
        return ans.isGoodAnswer();
    }
    public Question getQuestion(){return question;}

}
