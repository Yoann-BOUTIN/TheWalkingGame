package applicationTests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.MapFragment;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionJeu.Penality;
import com.thewalkinggame.app.gestionUser.Etat;
import com.thewalkinggame.app.gestionUser.UserLocation;

import static org.mockito.Mockito.mock;

/**
 * Created by kevin on 23/05/2014.
 *
 * On test le fait qu'un joueur en pénalité ne
 * peut se déplacer durant sa pénalité, si le joueur
 * n'est plus sur l'objectif alors la pénalité est bloqué
 * jusqu'à ce qu'il retourne à l'objectif.
 */
public class PenalityTest extends ActivityInstrumentationTestCase2<NullTestActivity> {

    public PenalityTest(){super(NullTestActivity.class);}

    /**
     * Permet de récuperer la position du joueur
     * (latitude, longitude)
     */
    UserLocation userlocation;
    /**
     * Objet penality que l'on va tester
     */
    Penality pena;
    /**
     * Map qui va servir a generer la penalite
     */
    MapFragment map;
    /**
     * Progress bar qui apparait avec la penalite
     */
    ProgressBar bar;
    /**
     * TextView qui apparait avec la penalite
     */
    TextView text;
    /**
     * TextView qui apparait avec la penalite
     */
    TextView textPena;
    /**
     * ImageView qui apparait avec la penalite
     */
    ImageView img;
    /**
     * Initialisation de robotium
     */
    Solo solo;
    /**
     * Etat du joueur lors de la penalite
     */
    Etat etat;


    /**
     * initialization before tests
     */
    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
        // Mockito creation
        userlocation = mock(UserLocation.class);
        map = new MapFragment();
    }

    /**
     * On verifie que la progress bar, les deux TextView et l'ImageView deviennent bien visible lors de la penalite
     */
    public void testVisibilityBeforePenality(){
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pena = new Penality(map);
                    bar = (ProgressBar) map.getActivity().findViewById(R.id.progress_bar_circle_penalite);
                    text = (TextView) map.getActivity().findViewById(R.id.textPenalite);
                    textPena = (TextView) map.getActivity().findViewById(R.id.textPenalit);
                    img = (ImageView) map.getActivity().findViewById(R.id.imgPenalite);
                    assertTrue(bar.getVisibility() == View.VISIBLE);
                    assertTrue(text.getVisibility() == View.VISIBLE);
                    assertTrue(textPena.getVisibility() == View.VISIBLE);
                    assertTrue(img.getVisibility() == View.VISIBLE);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * On verifie que l'etat du joueur lors de la penalite est bien EN_PENALITE
     */
    public void testEtatBeforePenality(){
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pena = new Penality(map);
                    etat = pena.getEtat();
                    assertTrue(etat == Etat.EN_PENALITE);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * On verifie qu'apres la penalite l'etat du joueur soit bien AUCUN_LIEU
     */
    public void testEtatAfterPenality(){
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pena = new Penality(map);
                    pena.runThread();
                    etat = pena.getEtat();
                    assertTrue(etat == Etat.AUCUN_LIEU);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * On verifie qu'apres la penalite la progress bar, les deux TextView et l'ImageView sont bien invisible
     */
    public void testVisibilityAfterPenality(){
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pena = new Penality(map);
                    bar = (ProgressBar) map.getActivity().findViewById(R.id.progress_bar_circle_penalite);
                    text = (TextView) map.getActivity().findViewById(R.id.textPenalite);
                    textPena = (TextView) map.getActivity().findViewById(R.id.textPenalit);
                    img = (ImageView) map.getActivity().findViewById(R.id.imgPenalite);
                    pena.runThread();
                    assertTrue(bar.getVisibility() == View.INVISIBLE);
                    assertTrue(text.getVisibility() == View.INVISIBLE);
                    assertTrue(textPena.getVisibility() == View.INVISIBLE);
                    assertTrue(img.getVisibility() == View.INVISIBLE);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
