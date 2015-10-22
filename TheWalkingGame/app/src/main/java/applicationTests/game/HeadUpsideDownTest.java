package applicationTests.game;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.gestionJeu.HeadUpsideDown.HeadUpsideDown;

/**
 * Created by kevin on 19/05/2014.
 *
 * On va tester la réussite du défi,
 * ainsi que l'echec.
 * Le fait que l'énigme apparaisse bien
 * à l'écran, et que le joueur ne peut
 * revenir sur la map en appuyant sur le bouton
 * BACK de la tablette lorsqu'il est dans le défi
 */
public class HeadUpsideDownTest extends ActivityInstrumentationTestCase2<HeadUpsideDown> {

    /**
     * Permet d'utiliser Robotium pour les test
     */
    private Solo solo;
    /**
     * Activity of HeadUpsideDown
     */
    private HeadUpsideDown headUpsideDownTest;

    /**
     * Constructor of the class test
     */
    public HeadUpsideDownTest() {
        super(HeadUpsideDown.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        headUpsideDownTest = getActivity();
    }

    /**
     * Vérifie que le joueur ne peut pas utiliser
     *  le bouton back pour revenir sur la map.
     *  */
    public void testCanNotGoBackActivity() throws Exception {
        // On vérifie qu'on est bien dans HeadUpsideDown
        solo.assertCurrentActivity("Nous devrions être dans HeadUpsideDown et nous sommes dans : "
                + solo.getCurrentActivity(), HeadUpsideDown.class);
        // On appuie sur le bouton BACK de la tablette
        solo.getCurrentActivity().onBackPressed();
        // On vérifie qu'on est toujours dans HeadUpsideDown
        solo.assertCurrentActivity("Nous devrions être dans HeadUpsideDown et nous sommes dans : "
                + solo.getCurrentActivity(), HeadUpsideDown.class);
    }

    /**
     * Check if the textView is in the activity, and if it
     * contains the text expected
     */
    public void testTextEnigme()
    {
        // On verifie que le texte existe
        assertNotNull(headUpsideDownTest.getTextOfTextView());
        // On vérifie qu'on a bien le texte de l'énigme sur la page
        assertEquals(headUpsideDownTest.getTextOfTextView(),"Je me sens mieux la tête à l'envers");
    }

    /**
     * Simulate the loose of the game
     */
    public void testLoosingGame()
    {
        headUpsideDownTest.setGoChangeEtat(false);
        // Check that the game is not succeed at the start
        assertFalse(headUpsideDownTest.gameSucceed());
        // Assert that the game is not succeed
        assertFalse(headUpsideDownTest.gameSucceed());
    }

    /**
     * Simulate the win of the game
     */
    public void testWinningGame()
    {
        headUpsideDownTest.setGoChangeEtat(false);
        // Check that the game is not succeed at the start
        assertFalse(headUpsideDownTest.gameSucceed());
        // New value for yAccelerometer
        headUpsideDownTest.setyAccelerometer(-9.f);
        //Function that check if it's succeed or not
        assertTrue(headUpsideDownTest.gameSucceed());
    }
}
