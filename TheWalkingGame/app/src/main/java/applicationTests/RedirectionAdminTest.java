package applicationTests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.admin.MainManageActivity;
import com.thewalkinggame.app.admin.ManageCurrentsParties;
import com.thewalkinggame.app.admin.ManageGamesActivity;
import com.thewalkinggame.app.admin.ManagePlayersActivity;

/**
 * Created by Marc on 30/04/2014.
 *
 * On test le bon redirectionnement des boutons vers
 * les activités appropriées.
 */
public class RedirectionAdminTest extends ActivityInstrumentationTestCase2<MainManageActivity> {

    /**
     * Permet d'utiliser Robotium pour les test
     */
    private Solo solo = null;

    /**
     * Contructeur de la classe de test
     */
    public RedirectionAdminTest() {
        super(MainManageActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
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
     * On vérifie qu'on est bien sur l'activity correspondant au menu.
     *
     * @throws Exception
     */
    public void testInGoodActivity() throws Exception {
        solo.assertCurrentActivity("Nous devrions être dans MainManageActivity et nous somme dans : "
                + solo.getCurrentActivity(),MainManageActivity.class);
    }

    /**
     * On vérifie qu'à la suite du click sur le bouton "Gestion des jeux"
     * de MainManageActivity, on arrive bien sur l'activity correspondant à ce bouton,
     * c'est à dire ManageGamesActivity.
     *
     * @throws Exception
     */
    public void testGoManageGamesActivity() throws Exception {
        // On click sur le bouton qui emmene sur la page ManageGamesActivity
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.button_manage_games));

        // On test si on est bien dans l'activity ManageGamesActivity
        solo.assertCurrentActivity("Nous devrions être dans ManageGamesActivity et nous sommes dans : "
                + solo.getCurrentActivity(), ManageGamesActivity.class);
    }

    /**
     * On vérifie qu'à la suite du click sur le bouton "Gestion des joueurs"
     * de MainManageActivity, on arrive bien sur l'activity correspondant à ce bouton,
     * c'est à dire ManagePlayersActivity.
     *
     * @throws Exception
     */
    public void testGoManagePlayersActivity() throws Exception {
        // On click sur le bouton qui emmene sur la pa ge ManageGamesActivity
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.button_manage_players));

        // On test si on est bien dans l'activity ManageGamesActivity
        solo.assertCurrentActivity("Nous devrions être dans ManagePlayersActivity et nous sommes dans : "
                + solo.getCurrentActivity(), ManagePlayersActivity.class);
    }

//    /**
//     * On vérifie qu'à la suite du click sur le bouton "Gestion des parties en cours"
//     * de MainManageActivity, on arrive bien sur l'activity correspondant à ce bouton,
//     * c'est à dire ManageCurrentsParties.
//     *
//     * @throws Exception
//     */
//    public void testGoManageCurrentsParties() throws Exception {
//        // On click sur le bouton qui emmene sur la page ManageCurrentsParties
//        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.button_manage_partie));
//
//        // On test si on est bien dans l'activity ManageCurrentsParties
//        solo.assertCurrentActivity("Nous devrions être dans ManageCurrentsParties et nous sommes dans : "
//                + solo.getCurrentActivity(), ManageCurrentsParties.class);
//    }
}