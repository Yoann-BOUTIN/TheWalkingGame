package applicationTests.game;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.gestionJeu.FindTheKey.FindTheKey;
import com.thewalkinggame.app.gestionJeu.FindTheKey.SplashFindTheKey;

/**
 * Created by Marc on 01/06/2014.
 *
 * Classe de test de la page de chargement pour le jeu FindTheKey,
 * qui permet de tester que le joueur ne peut pas lancer la partie en
 * touchant l'écran si les données du jeu n'ont pas fini d'être récupérées
 * par le serveur.
 * Et que le joueur peut accéder à l'activity correspondante au jeu une
 * fois que les données du jeu sont récupérées de la BDD.
 */
public class SplashFindTheKeyTest extends ActivityInstrumentationTestCase2<SplashFindTheKey> {
    Solo solo = null;
    private SplashFindTheKey activity;

    public SplashFindTheKeyTest(){
        super(SplashFindTheKey.class);
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity()); activity = getActivity();
    }

    /**
     * Vérifie que le joueur ne peut pas utiliser
     * le bouton back pour revenir sur la map.
     */
    public void testCanNotGoBackActivity() throws Exception {
        // On vérifie qu'on est bien dans SplashFindTheKey
        solo.assertCurrentActivity("Nous devrions être dans SplashFindTheKey et nous sommes dans : "
                + solo.getCurrentActivity(), SplashFindTheKey.class);
        // On appuie sur le bouton BACK de la tablette
        solo.getCurrentActivity().onBackPressed();
        // On vérifie qu'on est toujours dans SplashFindTheKey
        solo.assertCurrentActivity("Nous devrions être dans SplashFindTheKey et nous sommes dans : "
                + solo.getCurrentActivity(), SplashFindTheKey.class);
    }

    /** * Dans ce test, les données du jeu n'ont pas fini
     *  d'être récupérées donc le fait de cliquer sur l'écran
     *  n'emmenera pas l'utilisateur sur l'activity du jeu
     *  FindTheKey.
     */
    public void testCannotGoToGame() throws Throwable {
        activity.setFetchDataTerminated(false);
        // On click sur le text continuer
        solo.clickOnText("continuer");
        // On vérifie qu'on est toujours dans SplashFindTheKey
        solo.assertCurrentActivity("Nous devrions être dans SplashFindTheKey et nous sommes dans : "
                + solo.getCurrentActivity(), SplashFindTheKey.class);
    }

    /**
     * Dans ce test, les données du jeu sont
     * récupérées, donc le fait de cliquer sur l'écran
     * emmène l'utilisateur sur l'activity du jeu FindTheKey.
     */
    public void testGoToGame() throws Throwable {
        activity.setFetchDataTerminated(true);
        // On click sur le text continuer
        // on pourrait aussi click n'importe ou
        // sur l'écran.
        solo.clickOnText("continuer");
        // On vérifie qu'on est dans l'activity du jeu FindTheKey
        solo.assertCurrentActivity("Nous devrions être dans FindTheKey et nous sommes dans : "
                + solo.getCurrentActivity(), FindTheKey.class);
    }
}