package applicationTests.game;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionJeu.JeuSimon.SimonGameActivity;

/**
 * Created by Marc on 14/05/2014.
 *
 * On va tester la réussite de ce défi,
 * ainsi que la défaite.
 * On va aussi faire un test de layout
 * et vérifier qu'on a bien à l'écran
 * les 4 boutons du jeu sur lequel
 * le joueur va devoir appuyer.
 */
public class SimonGameTest extends ActivityInstrumentationTestCase2<SimonGameActivity> {

    /**
     * Main class for testing with Robotium
     */
    Solo solo = null;
    /**
     * Actvity SimonGameActivity que l'on test
     */
    private SimonGameActivity activity;


    /**
     * Constructeur de la classe de test
     */
    public SimonGameTest(){
        super(SimonGameActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        activity = getActivity();
    }

    /**
     * S'execute apres chaque méthode de test.
     * Permet de fermer l'activité courante.
     *
     * @throws Exception
     */
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    /**
     * Vérifie que le joueur ne peut pas
     * utiliser le bouton back pour revenir sur la map.
     */
    public void testCanNotGoBackActivity() throws Exception {

        // On vérifie qu'on est bien dans SimonGameActivity
        solo.assertCurrentActivity("Nous devrions être dans SimonGameActivity et nous sommes dans : "
                + solo.getCurrentActivity(), SimonGameActivity.class);

        // On appuie sur le bouton BACK de la tablette
        solo.getCurrentActivity().onBackPressed();

        // On vérifie qu'on est toujours dans SimonGameActivity
        solo.assertCurrentActivity("Nous devrions être dans SimonGameActivity et nous sommes dans : "
                + solo.getCurrentActivity(), SimonGameActivity.class);
    }

    /**
     * On vérifie que les boutons du jeu simon existent
     */
    public void testLayout() {
        // On vérifie que la vue pour chacun des boutons existe
        assertNotNull(activity.findViewById(R.id.layout_green));
        assertNotNull(activity.findViewById(R.id.layout_red));
        assertNotNull(activity.findViewById(R.id.layout_yellow));
        assertNotNull(activity.findViewById(R.id.layout_blue));
    }

    /**
     * On vérifie que la longueur de la sequence
     * est bien celle voulue.
     */
    public void testSeqLength() throws Throwable {
        while(!activity.getRecupDataTerminated()){
            //nothing
        }

        activity.setSeqLength(4);

        final Button readSeq = (Button) activity.findViewById(R.id.readySimon);
        // On click sur le bouton pour lire la sequence
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                readSeq.performClick();
            }
        });

        while(!activity.getSeqConstructed()){
            //nothing
        }

        // On récupere les données du jeu
        int[] seq = activity.getSeq();
        int seqLengthWanted = activity.getSeqLength();

        // On vérifie que la sequence n'est pas nulle
        assertNotNull(seq);

        // On calcule la longueur de la sequence
        int seqLength = 0;
        for(int i = 0; i < seq.length; i++){
            if (seq[i] != 0) seqLength++;
        }

        // On vérifie que la longueur de la sequence est correcte
        assertEquals("Longueur de la sequence incorrecte : " + seqLength, seqLengthWanted, seqLength);
    }

    /**
     * On test de réussir le défi, et donc que la variable
     * isSucceed vaut true.
     *
     * @throws Throwable
     */
    public void testGameSucceed() throws Throwable {
        activity.setGoChangeEtat(false);
        activity.setRecupDataTerminated(true);

        while(!activity.getRecupDataTerminated()){
            // nothing
        }

        activity.setSeqLength(4);

        final Button readSeq = (Button) activity.findViewById(R.id.readySimon);
        // On click sur le bouton pour lire la sequence
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                readSeq.performClick();
            }
        });

        while(!activity.getSeqConstructed()){
            //nothing
        }

        // On initialise les données du jeu
        final int[] seq = activity.getSeq();
        final int seqLength = activity.getSeqLength();

        // On récupere les vues
        final Button buttonGreen = (Button) activity.findViewById(R.id.layout_green);
        final Button buttonRed = (Button) activity.findViewById(R.id.layout_red);
        final Button buttonYellow = (Button) activity.findViewById(R.id.layout_yellow);
        final Button buttonBlue = (Button) activity.findViewById(R.id.layout_blue);
        final TextView txt = (TextView) activity.findViewById(R.id.textCAVSimon);


        while(!buttonGreen.isEnabled()
                && !buttonRed.isEnabled()
                && !buttonYellow.isEnabled()
                && !buttonBlue.isEnabled()){
            // nothing
            // On attend que la démo soit terminée
        }

        // On click sur les boutons voulues
        for(int i = 0; i < seqLength; i++){
            switch (seq[i]){
                case 1 :
                    if(i < seqLength - 1) {
                        solo.assertCurrentActivity("Nous devrions être dans SimonGameActivity et nous sommes dans : "
                                + solo.getCurrentActivity(), SimonGameActivity.class);
                    }
                    assertEquals("seq["+i+"] != 1", seq[i], 1);
                    solo.clickOnView(buttonGreen);
                    break;
                case 2 :
                    if(i < seqLength - 1) {
                        solo.assertCurrentActivity("Nous devrions être dans SimonGameActivity et nous sommes dans : "
                                + solo.getCurrentActivity(), SimonGameActivity.class);
                    }
                    assertEquals("seq["+i+"] != 2", seq[i], 2);
                    solo.clickOnView(buttonRed);
                    break;
                case 3 :
                    if(i < seqLength - 1) {
                        solo.assertCurrentActivity("Nous devrions être dans SimonGameActivity et nous sommes dans : "
                                + solo.getCurrentActivity(), SimonGameActivity.class);
                    }
                    assertEquals("seq["+i+"] != 3", seq[i], 3);
                    solo.clickOnView(buttonYellow);
                    break;
                case 4 :
                    if(i < seqLength - 1) {
                        solo.assertCurrentActivity("Nous devrions être dans SimonGameActivity et nous sommes dans : "
                                + solo.getCurrentActivity(), SimonGameActivity.class);
                    }
                    assertEquals("seq["+i+"] != 4", seq[i], 4);
                    solo.clickOnView(buttonBlue);
                    break;
            }
        }

        // On vérifie qu'on a réussi le défi
        assertTrue(activity.getIsSucceed());
    }

    /**
     * On test de rater le défi, et donc que la variable
     * isSucceed vaut false.
     *
     * @throws Throwable
     */
    public void testGameFail() throws Throwable {
        activity.setGoChangeEtat(false);
        activity.setRecupDataTerminated(true);

        while(!activity.getRecupDataTerminated()){
            // nothing
        }

        activity.setSeqLength(4);

        final Button readSeq = (Button) activity.findViewById(R.id.readySimon);
        // On click sur le bouton pour lire la sequence
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                readSeq.performClick();
            }
        });

        while(!activity.getSeqConstructed()){
            //nothing
        }

        // On récupere les données du jeu
        final int[] seq = activity.getSeq();
        final int seqLength = activity.getSeqLength();

        // On récupere les vues
        final Button buttonGreen = (Button) activity.findViewById(R.id.layout_green);
        final Button buttonRed = (Button) activity.findViewById(R.id.layout_red);
        final Button buttonYellow = (Button) activity.findViewById(R.id.layout_yellow);
        final Button buttonBlue = (Button) activity.findViewById(R.id.layout_blue);
        final TextView txt = (TextView) activity.findViewById(R.id.textCAVSimon);

        // On click sur le bouton pour lire la sequence
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                readSeq.performClick();
            }
        });

        while(!buttonGreen.isEnabled()
                && !buttonRed.isEnabled()
                && !buttonYellow.isEnabled()
                && !buttonBlue.isEnabled()){
            // nothing
            // On attend que la démo soit terminée
        }

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(seq[0] == 1){
                    buttonYellow.performClick();
                } else {

                    buttonGreen.performClick();
                }
            }
        });

        assertFalse(activity.getIsSucceed());
    }
}
