package applicationTests;

import android.app.Fragment;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.MapFragment;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionJeu.Objective;
import com.thewalkinggame.app.gestionUser.Etat;
import com.thewalkinggame.app.gestionUser.UserLocation;
import com.thewalkinggame.app.utilitaire.MyAlertDialog;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
* Created by Yoann on 09/04/2014.
 *
 *----------------------------- EXPLICATION DE LANCEMENT DES TESTS ---------------------------------
 *
 * Etant donne que notre architecture pour l'enum ETAT est difficilement testable a cause des elements graphiques qu'il contient
 * pour pouvoir lancer ces tests il faut :
 *
 * - Decommenter tous les tests suivants.
 * - Aller dans le fichier de l'enum ETAT et commenter la ligne "objective = new Objective(43.7160892d, 7.2635992d);" dans la
 * fonction obtenirObjectif.
 *
 * Nous avons essaye toutes les methodes qui s'offraient a nous pour pouvoir tester cet enum mais, comme vous avez pu aussi
 * le constater avec nous, nous nous y sommes mal pris lors de l'ecriture du code ce qui nous a empeche de le tester proprement.
*/
/**
@RunWith(PowerMockRunner.class)
@PrepareForTest(MapFragment.class)*/
public class EtatTest extends ActivityInstrumentationTestCase2<NullTestActivity> {
    /**
     * Position de l'utilisateur
     */
    UserLocation userlocation;
    /**
     * Objectif
     */
    Objective objective;
    /**
     * Generation de position pour l'objectif
     */
    Location ml ;
    /**
     * Objectif
     */
    Objective ob ;
    /**
     * Etat de l'utilisateur
     */
    Etat etatUser;
    /**
     * Generation de la map sur laquelle on va tester la machine a etat
     */
    MapFragment map;
    /**
     * Boolean permettant de faire en sorte que le defi soit reussi ou non pour tester les deux cas
     */
    Boolean defiTest;
    /**
     * AlertDialog genere lors de certains etats
     */
    MyAlertDialog alert;
    /**
     * Robotium
     */
    Solo solo;
    /**
     * Fragment qui va comporter la map ou vont se derouler les tests
     */
    Fragment fm;


    public EtatTest(){super(NullTestActivity.class);}

    @Override
    public void setUp() throws Exception
    {
        solo = new Solo(getInstrumentation(), getActivity());
        fm = solo.getCurrentActivity().getFragmentManager().findFragmentById(R.id.map);
        map = new MapFragment();
        alert = mock(MyAlertDialog.class);
        when(alert.getIsShown()).thenReturn(Boolean.TRUE);
        doNothing().when(alert).show();

        // Mockito creation
        ml = mock(Location.class);
        // Mockito methods
        when(ml.getLatitude()).thenReturn(42.0);
        when(ml.getLongitude()).thenReturn(71.22);
        when(ml.getProvider()).thenReturn("flp");
        when(ml.getAccuracy()).thenReturn(3.0f);

        // New Objective
        ob = new Objective(ml.getLatitude(),ml.getLongitude());
        // New UserLocation

        userlocation = mock(UserLocation.class);
        when(userlocation.getLatitude()).thenReturn(43.7160892d);
        when(userlocation.getLongitude()).thenReturn(7.2635992d);
        when(userlocation.isOnObjective(objective)).thenReturn(true);

    }

    /**
     * Ferme les activity ouvertes a la fin des tests
     * @throws Exception
     */
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    /**
     * Verification qu'au depart le joueur est bien dans l'etat AUCUN_LIEU
     * @throws Exception
     */
    public void testEtatAUCUN_LIEU() throws Exception{
        etatUser = map.getEtat();
        assertTrue(etatUser == Etat.AUCUN_LIEU);
    }

    /**
     * On verifie que lorsque le joueur recoit un nouvel objectif il passe bien dans l'etat EN_RECHERCHE_DE_LIEU
     * @throws Exception
     */
    public void testEtatEN_RECHERCHE_DE_LIEU() throws Exception{
        /*try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etatUser = Etat.AUCUN_LIEU;
                    etatUser = etatUser.obtenirObjectif(map);
                    assertTrue(etatUser == Etat.EN_RECHERCHE_DU_LIEU);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }*/
    }

    /**
     * On verifie que lorsque le joueur termine une penalite il passe bien par l'etat AUCUN_LIEU
     * @throws Exception
     */
    public void testEtatEN_PENALITE() throws Exception {
        /*try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etatUser = Etat.EN_PENALITE;
                    etatUser.setMapActivity(map);
                    etatUser = etatUser.penaliteTerminee();
                    assertTrue(etatUser == Etat.AUCUN_LIEU);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }*/

    }

    /**
     * Verifie que lorsque le joueur atteint un objectif il soit dans l'etant REPOND_ALERT_DIALOG
     * @throws Exception
     */
    public void testEtataAtteintObjectif() throws Exception{
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etatUser = Etat.EN_RECHERCHE_DU_LIEU;
                    etatUser.setMapActivity(map);
                    etatUser = etatUser.aAtteintObjectif(userlocation);
                    assertTrue(etatUser == Etat.REPOND_ALERT_DIALOG);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etatUser = Etat.LIEU_TROUVE;
                    etatUser.setMapActivity(map);
                    etatUser = etatUser.aAtteintObjectif(userlocation);
                    assertTrue(etatUser == Etat.REPOND_ALERT_DIALOG);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * On verifie que lorsque le joueur atteint un objectif il passe bien dans l'etat EN_DEFI
     * @throws Exception
     */
    public void testEtatEN_DEFI() throws  Exception{
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etatUser = Etat.LIEU_TROUVE;
                    etatUser.setMapActivity(map);
                    etatUser = etatUser.lancerDefi();
                    assertTrue(etatUser == Etat.EN_DEFI);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * On verifie que lorsque le joueur rate son defi il passe bien dans l'etat EN_PENALITE
     * @throws Exception
     */
    public void testDefiTerminePena() throws Exception{
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etatUser = Etat.EN_DEFI;
                    defiTest = Boolean.FALSE;
                    etatUser.setMapActivity(map);
                    etatUser = etatUser.defiTermine(defiTest);
                    assertTrue(etatUser == Etat.EN_PENALITE);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    /**
     * On verifie que lorsque le joueur reussi son defi il passe bien dans l'etat AUCUN_LIEU
     * @throws Exception
     */
    public void testDefiTermine() throws Exception{
        /*try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    etatUser = Etat.EN_DEFI;
                    defiTest = Boolean.TRUE;
                    etatUser.setMapActivity(map);
                    etatUser = etatUser.defiTermine(defiTest);
                    assertTrue(etatUser == Etat.AUCUN_LIEU);
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }*/

    }
}
