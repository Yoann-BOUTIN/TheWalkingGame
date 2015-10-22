package applicationTests.game;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.gestionJeu.HeadUpsideDown.HeadUpsideDown;
import com.thewalkinggame.app.gestionJeu.HeadUpsideDown.SplashHeadUpsideDown;

/**
 * Classe de test de la page de chargement pour le jeu HeadUpsideDown,
 * qui permet de tester que le joueur ne peut pas lancer la partie en
 * touchant l'écran si les données du jeu n'ont pas fini d'être récupérées
 * par le serveur.
 * Et que le joueur peut accéder à l'activity correspondante au jeu une
 * fois que les données du jeu sont récupérées de la BDD.
 */
public class SplashHeadUpsideDownTest extends ActivityInstrumentationTestCase2<SplashHeadUpsideDown> {

    /**
     * Permet d'utiliser Robotium pour les test
     */
    Solo solo = null;
    /**
     * Activity SplashHeadUpsideDown que l'on veut tester
     */
    private SplashHeadUpsideDown activity;

    /**
     * Constructeur de notre classe de test
     */
    public SplashHeadUpsideDownTest(){
        super(SplashHeadUpsideDown.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        activity = getActivity();
    }

    /**
     * Vérifie que le joueur ne peut pas utiliser le bouton back pour revenir sur la map.
     */
    public void testCanNotGoBackActivity() throws Exception {
    // On vérifie qu'on est bien dans SplashHeadUpsideDown
    solo.assertCurrentActivity("Nous devrions être dans SplashHeadUpsideDown et nous sommes dans : "
            + solo.getCurrentActivity(), SplashHeadUpsideDown.class);
    // On appuie sur le bouton BACK de la tablette
    solo.getCurrentActivity().onBackPressed();
    // On vérifie qu'on est toujours dans SplashHeadUpsideDown
    solo.assertCurrentActivity("Nous devrions être dans SplashHeadUpsideDown et nous sommes dans : "
            + solo.getCurrentActivity(), SplashHeadUpsideDown.class);
    }

    /**
     * Dans ce test, les données du jeu n'ont pas fini
     * d'être récupérées donc le fait de cliquer sur l'écran
     * n'emmenera pas l'utilisateur sur l'activity du jeu
     * HeadUpsideDown.
     */
    public void testCannotGoToGame(){
        activity.setRecupDataTerminated(false);
        // On click sur le text "appuyez" de
        // "Quand vous êtes prêt, appuyez sur l'écran"
        solo.clickOnText("appuyez");
        // On vérifie qu'on est toujours dans SplashHeadUpsideDown
        solo.assertCurrentActivity("Nous devrions être dans SplashHeadUpsideDown et nous sommes dans : "
                + solo.getCurrentActivity(), SplashHeadUpsideDown.class);
    }

    /**
     * Dans ce test, les données du jeu sont
     * récupérées, donc le fait de cliquer sur l'écran
     * emmène l'utilisateur sur l'activity du jeu HeadUpsideDown.
     */
     public void testGoToGame(){
         activity.setRecupDataTerminated(true);
         // On click sur le text "appuyez" de
         // "Quand vous êtes prêt, appuyez sur l'écran"
         solo.clickOnText("appuyez");
         // On vérifie qu'on est dans l'activity du jeu HeadUpsideDown
         solo.assertCurrentActivity("Nous devrions être dans HeadUpsideDown et nous sommes dans : "
                 + solo.getCurrentActivity(), HeadUpsideDown.class);
     }
}