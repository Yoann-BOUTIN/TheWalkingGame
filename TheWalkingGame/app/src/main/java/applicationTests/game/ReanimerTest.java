package applicationTests.game;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.gestionJeu.Reanimer.Reanimer;

/**
 * Created by kevin
 *
 * On va tester la réussite du défi, ainsi que l'echec.
 * Et que le joueur ne peut revenir sur la map en
 * appuyant sur le bouton BACK de la tablette lorsqu'il
 * est dans le défi.
 */
public class ReanimerTest extends ActivityInstrumentationTestCase2<Reanimer> {

    /**
     * Permet d'utiliser Robotium pour les test
     */
    private Solo solo;
    /**
     * Activity Reanimer que l'on test
     */
    private Reanimer reanimerTest;

    /**
     * Constructeur de la class de test
     */
    public ReanimerTest() {
        super(Reanimer.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        reanimerTest = getActivity();
    }

    public void tearDown() throws Exception { solo.finishOpenedActivities(); }
    /** * Vérifie que le joueur ne peut pas utiliser *
     *  le bouton back pour revenir sur la map. */

    public void testCanNotGoBackActivity() throws Exception {
    // On vérifie qu'on est bien dans Reanimer
    solo.assertCurrentActivity("Nous devrions être dans Reanimer et nous sommes dans : "
            + solo.getCurrentActivity(), Reanimer.class);
    // On appuie sur le bouton BACK de la tablette
    solo.getCurrentActivity().onBackPressed();
    // On vérifie qu'on est toujours dans Reanimer
    solo.assertCurrentActivity("Nous devrions être dans Reanimer et nous sommes dans : "
            + solo.getCurrentActivity(), Reanimer.class);
    }

    /**
     * Simulate the loose of the game
     */
    public void testLoosingGame()
    {
        reanimerTest.setGoChangeEtat(false);
        //set a start score
        reanimerTest.setScore(0);
        //set a maximal score
        reanimerTest.setScoreTarget(1500);
        // Check that the game is not succeed at the start
        assertFalse(reanimerTest.gameSucceed());
        // score < scoreTarget
        reanimerTest.setScore(700);
        // Assert that the game is not succeed
        assertFalse(reanimerTest.gameSucceed());
    }

    /**
     * Simulate the win of the game
     */
    public void testWinningGame()
    {
        reanimerTest.setGoChangeEtat(false);
        //set a start score
        reanimerTest.setScore(0);
        //set a maximal score to targer
        reanimerTest.setScoreTarget(1500);
        // Check that the game is not succeed at the start
        assertFalse(reanimerTest.gameSucceed());
        // New value for yAccelerometer
        reanimerTest.setScore(1500);
        //Function that check if it's succeed or not
        assertTrue(reanimerTest.gameSucceed());
    }
}
