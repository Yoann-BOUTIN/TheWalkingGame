package applicationTests.game;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionJeu.Reanimer.Reanimer;
import com.thewalkinggame.app.gestionJeu.Reanimer.SplashReanimer;

/**
 * Created by Zied on 20/05/2014.
 * Classe de test de la page de chargement pour le jeu Reanimer,
 * qui permet de tester que le joueur ne peut pas lancer la partie en
 * touchant l'écran si les données du jeu n'ont pas fini d'être récupérées
 * par le serveur.
 * Et que le joueur peut accéder à l'activity correspondante au jeu une
 * fois que les données du jeu sont récupérées de la BDD.
 */
public class SplashReanimerTest extends ActivityInstrumentationTestCase2<SplashReanimer> {
    Solo solo = null;
    private SplashReanimer activity;

    public SplashReanimerTest(){
        super(SplashReanimer.class);
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
        // On vérifie qu'on est bien dans SplashReanimer
        solo.assertCurrentActivity("Nous devrions être dans SplashReanimer et nous sommes dans : "
            + solo.getCurrentActivity(), SplashReanimer.class);
        // On appuie sur le bouton BACK de la tablette
        solo.getCurrentActivity().onBackPressed();
        // On vérifie qu'on est toujours dans SplashReanimer
        solo.assertCurrentActivity("Nous devrions être dans SplashReanimer et nous sommes dans : "
            + solo.getCurrentActivity(), SplashReanimer.class);
       }

    /** * Dans ce test, les données du jeu n'ont pas fini
    *  d'être récupérées donc le fait de cliquer sur l'écran
    *  n'emmenera pas l'utilisateur sur l'activity du jeu
    *  SplashReanimer.
    */
    public void testCannotGoToGame() throws Throwable {
        assertNotNull(activity.findViewById(R.id.anim_splash_reanimer));
        final ImageView imageView = (ImageView) activity.findViewById(R.id.anim_splash_reanimer);

        activity.setRecupDataTerminated(false);
        // On click sur l'image sur l'écran
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.performClick();
            }
        });
        // On vérifie qu'on est toujours dans SplashReanimer
        solo.assertCurrentActivity("Nous devrions être dans SplashReanimer et nous sommes dans : "
            + solo.getCurrentActivity(), SplashReanimer.class);
    }

    /**
    * Dans ce test, les données du jeu sont
    * récupérées, donc le fait de cliquer sur l'écran
    * emmène l'utilisateur sur l'activity du jeu Reanimer.
    */
    public void testGoToGame() throws Throwable {
        assertNotNull(activity.findViewById(R.id.anim_splash_reanimer));
        final ImageView imageView = (ImageView) activity.findViewById(R.id.anim_splash_reanimer);

        activity.setRecupDataTerminated(true);
        // On click sur l'image sur l'écran
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.performClick();
            }
        });
        // On vérifie qu'on est dans l'activity du jeu Reanimer
        solo.assertCurrentActivity("Nous devrions être dans Reanimer et nous sommes dans : "
            + solo.getCurrentActivity(), Reanimer.class);
    }
}