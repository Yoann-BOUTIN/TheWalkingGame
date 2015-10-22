package applicationTests.game;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionJeu.QuizHandler.Quiz;
import com.thewalkinggame.app.gestionJeu.QuizHandler.QuizActivity;

/**
 * Created by Marc on 18/04/2014.
 *
 * On va tester que le quiz apparait bien,
 * sous la forme d'une question et de ses
 * 4 réponses.
 * Qu'il peut y avoir des questions avec
 * plusieurs réponses juste comme des questions
 * avec une seule réponse juste.
 */
public class QuizTest extends ActivityInstrumentationTestCase2<QuizActivity>{

    /**
     * Permet d'utiliser Robotium pour les test
     */
    Solo solo = null;
    /**
     * Id du textview correspondant à la question
     */
    private int textViewQuestion;
    /**
     * Id du bouton correspondant à la réponse 1
     */
    private int buttonAns1;
    /**
     * Id du bouton correspondant à la réponse 2
     */
    private int buttonAns2;
    /**
     * Id du bouton correspondant à la réponse 3
     */
    private int buttonAns3;
    /**
     * Id du bouton correspondant à la réponse 4
     */
    private int buttonAns4;
    /**
     * Id du bouton valider
     */
    private int buttonValider;
    /**
     * L'activity du quiz
     */
    private QuizActivity activity;

    /**
     * Constructeur de la class de test
     */
    public QuizTest(){
        super(QuizActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        activity = getActivity();
    }

    /**
     * Vérifie qu'une fois le joueur en train de répondre au quiz,
     * il ne peut utiliser le bouton back pour revenir sur la map.
     */
    public void testCanNotGoBackActivity() throws Exception {

        // On vérifie qu'on est bien dans QuizActivity
        solo.assertCurrentActivity("Nous devrions être dans QuizActivity et nous sommes dans : "
                + solo.getCurrentActivity(), QuizActivity.class);

        // On appuie sur le bouton BACK de la tablette
        solo.getCurrentActivity().onBackPressed();

        // On vérifie qu'on est toujours dans QuizActivity
        solo.assertCurrentActivity("Nous devrions être dans QuizActivity et nous sommes dans : "
                + solo.getCurrentActivity(), QuizActivity.class);
    }

    /**
     * Vérifie que le layout de QuizActivity contient bien :
     *  - 1 TextView question
     *  - 4 Button reponses
     *  - 1 Button valider
     *
     * Et que le text compris dans le TextView correspondant à la question,
     * et le text compris dans les boutons correspondent bien à la
     * question du quiz et à ses réponses.
     */
    public void testLayout() throws Exception{

        // Récupere le text de la question et des réponses du quiz
        CharSequence textQuestion = this.getActivity().getTextQuestion();
        CharSequence textAnswer1 = this.getActivity().getAnswersText1();
        CharSequence textAnswer2 = this.getActivity().getAnswersText2();
        CharSequence textAnswer3 = this.getActivity().getAnswersText3();
        CharSequence textAnswer4 = this.getActivity().getAnswersText4();

        // Récupere les id des boutons et du text
        textViewQuestion = R.id.texview_question;
        buttonAns1 = R.id.button_ans1;
        buttonAns2 = R.id.button_ans2;
        buttonAns3 = R.id.button_ans3;
        buttonAns4 = R.id.button_ans4;
        buttonValider = R.id.button_validation;

        // On vérifie que la vue pour chacun des boutons/text existe
        assertNotNull(activity.findViewById(textViewQuestion));
        assertNotNull(activity.findViewById(buttonAns1));
        assertNotNull(activity.findViewById(buttonAns2));
        assertNotNull(activity.findViewById(buttonAns3));
        assertNotNull(activity.findViewById(buttonAns4));
        assertNotNull(activity.findViewById(buttonValider));

        // On récupère la vue de chaque boutons de réponse et
        // du text correspondant à la question
        TextView viewTextViewQuestion = (TextView) activity.findViewById(textViewQuestion);
        Button viewButtonAns1 = (Button) activity.findViewById(buttonAns1);
        Button viewButtonAns2 = (Button) activity.findViewById(buttonAns2);
        Button viewButtonAns3 = (Button) activity.findViewById(buttonAns3);
        Button viewButtonAns4 = (Button) activity.findViewById(buttonAns4);
        Button viewButtonValider = (Button) activity.findViewById(buttonValider);

        // On vérifie que le text des vues sont les mêmes que ceux fourni
        // par le Quiz, pour la question, les réponses, et le bouton valider.
        assertEquals("Incorrect label of the button", textQuestion, viewTextViewQuestion.getText());
        assertEquals("Incorrect label of the button", textAnswer1, viewButtonAns1.getText());
        assertEquals("Incorrect label of the button", textAnswer2, viewButtonAns2.getText());
        assertEquals("Incorrect label of the button", textAnswer3, viewButtonAns3.getText());
        assertEquals("Incorrect label of the button", textAnswer4, viewButtonAns4.getText());
        assertEquals("Incorrect label of the button", "Valider", viewButtonValider.getText());
    }

    /**
     * Vérifie que si c'est un quiz avec une réponse juste
     * alors il ne possede qu'une seule reponse juste.
     * Si c'est un quiz à plusieurs réponses juste,
     * il en possede alors plusieurs.
     */
    public void testNbGoodAnswer(){

        /** Si l'identifiant d'une réponse (chiffre allant de 0 à 3)
         *  correspont à une réponse juste, on incrémente la variable
         *  nombreReponseJuste.
         *  On vérifie ensuite que nombreReponseJuste = 1 si le quiz n'a
         *  qu'une seule reponse juste.
         *  Si c'est un quiz avec plusieurs reponses juste, on vérifie
         *  alors que nombreReponseJuste = le nombre de reponses juste
         *  du quiz (c'est à dire countGoodAnswers()).
         */
        int nombreReponsesJuste = 0;
        Quiz quiz = activity.getQuiz();
        if(!quiz.getQuestion().isChoiceMultiple()) {
            // Le quiz n'a qu'une seule réponse juste
            if (quiz.isSucceed(quiz.getAnswers().getAnswers().get(0))) nombreReponsesJuste++;
            if (quiz.isSucceed(quiz.getAnswers().getAnswers().get(1))) nombreReponsesJuste++;
            if (quiz.isSucceed(quiz.getAnswers().getAnswers().get(2))) nombreReponsesJuste++;
            if (quiz.isSucceed(quiz.getAnswers().getAnswers().get(3))) nombreReponsesJuste++;
            assertEquals("Doit avoir 1 reponse juste", 1, nombreReponsesJuste);
        } else {
            // Le quiz a nbRepJusteAttendue reponses juste
            int nbRepJusteAttendue = quiz.getAnswers().countGoodAnswers();
            if (quiz.isSucceed(quiz.getAnswers().getAnswers().get(0))) nombreReponsesJuste++;
            if (quiz.isSucceed(quiz.getAnswers().getAnswers().get(1))) nombreReponsesJuste++;
            if (quiz.isSucceed(quiz.getAnswers().getAnswers().get(2))) nombreReponsesJuste++;
            if (quiz.isSucceed(quiz.getAnswers().getAnswers().get(3))) nombreReponsesJuste++;
            assertEquals("Doit avoir " + nbRepJusteAttendue + " reponses juste",
                    nbRepJusteAttendue, nombreReponsesJuste);
        }
    }
}