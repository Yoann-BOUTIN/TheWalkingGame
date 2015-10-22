package applicationTests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.MainActivity;
import com.thewalkinggame.app.endGame.EndOfGame;

/**
 * Created by kevin on 01/06/2014.
 */
public class EndOfGameTest extends ActivityInstrumentationTestCase2<EndOfGame> {

    /**
     * Permet d'utiliser Robotium pour les test
     */
    private Solo solo;
    /**
     * Activity EndOfGame que l'on test
     */
    private EndOfGame endOfGame;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        endOfGame = getActivity();
    }

    /**
     * Constructeur de EndOfGameTest
     */
    public EndOfGameTest() {
        super(EndOfGame.class);
    }

    /**
     * On test qu'on ne peut pas appuyer sur le bouton back de la tablette
     * @throws Exception
     */
    public void testCanNotGoBackActivity() throws Exception {
        // On vérifie qu'on est bien dans EndOfGame
        solo.assertCurrentActivity("Nous devrions être dans EndOfGame et nous sommes dans : "
                + solo.getCurrentActivity(), EndOfGame.class);
        // On appuie sur le bouton BACK de la tablette
        solo.getCurrentActivity().onBackPressed();
        // On vérifie qu'on est toujours dans EndOfGame
        solo.assertCurrentActivity("Nous devrions être dans EndOfGame et nous sommes dans : "
                + solo.getCurrentActivity(), EndOfGame.class);
    }

    /**
     * On test que le clique sur le Bouton Retour au menu
     * nous amène bien sur le menu principal
     */
    public void testClickToGoMenu()
    {
        // On est dans EndOfGame
        solo.assertCurrentActivity("Nous devrions être dans EndOfGame et nous sommes dans : "
                + solo.getCurrentActivity(), EndOfGame.class);

        // On clique sur le bouton Retour au menu
        solo.clickOnButton("Retour au menu principal");

        //On verifie qu'on est dans le menu principal
        solo.assertCurrentActivity("Nous devrions être dans MainActivity et nous sommes dans: "
                +solo.getCurrentActivity(), MainActivity.class);
    }
}
