package applicationTests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.admin.ManageFindKeysActivity;
import com.thewalkinggame.app.admin.ManageGamesActivity;
import com.thewalkinggame.app.admin.ManageHeadUpsideDownActivity;
import com.thewalkinggame.app.admin.ManageQuizActivity;
import com.thewalkinggame.app.admin.ManageReanimerActivity;
import com.thewalkinggame.app.admin.ManageSimonActivity;

/**
 * Created by Yoann on 01/06/2014.
 */
public class RedirectionManageGamesTest extends ActivityInstrumentationTestCase2<ManageGamesActivity> {
    /**
     * Robotium
     */
    private Solo solo = null;

    /**
     * Contructeur de la classe de test
     */
    public RedirectionManageGamesTest() {
        super(ManageGamesActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * Ferme chaque activity apres le test
     *
     * @throws Exception
     */
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    /**
     * On vérifie qu'on est bien sur l'activity correspondant au menu des jeux.
     *
     * @throws Exception
     */
    public void testInGoodActivity() throws Exception {
        solo.assertCurrentActivity("Nous devrions être dans ManageGamesActivity et nous somme dans : "
                + solo.getCurrentActivity(),ManageGamesActivity.class);
    }

    /**
     * On vérifie qu'on va bien dans l'activity ManageQuizActivity
     *
     * @throws Exception
     */
    public void testGoManageQuizActivity() throws Exception {
        // On click sur le bouton qui emmene sur la page ManageGamesActivity
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.button_manage_quiz));

        // On test si on est bien dans l'activity ManageGamesActivity
        solo.assertCurrentActivity("Nous devrions être dans ManageQuizActivity et nous sommes dans : "
                + solo.getCurrentActivity(), ManageQuizActivity.class);
    }

    /**
     * On vérifie qu'on va bien dans l'activity ManageReanimerActivity
     *
     * @throws Exception
     */
    public void testGoManageReanimerActivity() throws Exception {
        // On click sur le bouton qui emmene sur la page ManageGamesActivity
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.button_manage_reanimer));

        // On test si on est bien dans l'activity ManageGamesActivity
        solo.assertCurrentActivity("Nous devrions être dans ManageReanimerActivity et nous sommes dans : "
                + solo.getCurrentActivity(), ManageReanimerActivity.class);
    }

    /**
     * On vérifie qu'on va bien dans l'activity ManageHeadUpsideDownActivity
     *
     * @throws Exception
     */
    public void testGoManageHeadUpsideDownActivity() throws Exception {
        // On click sur le bouton qui emmene sur la page ManageGamesActivity
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.button_manage_head_upside_down));

        // On test si on est bien dans l'activity ManageGamesActivity
        solo.assertCurrentActivity("Nous devrions être dans ManageHeadUpsideDownActivity et nous sommes dans : "
                + solo.getCurrentActivity(), ManageHeadUpsideDownActivity.class);
    }

    /**
     * On vérifie qu'on va bien dans l'activity ManageSimonActivity
     *
     * @throws Exception
     */
    public void testGoManageSimonActivity() throws Exception {
        // On click sur le bouton qui emmene sur la page ManageGamesActivity
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.button_manage_simon));

        // On test si on est bien dans l'activity ManageGamesActivity
        solo.assertCurrentActivity("Nous devrions être dans ManageSimonActivity et nous sommes dans : "
                + solo.getCurrentActivity(), ManageSimonActivity.class);
    }

    /**
     * On vérifie qu'on va bien dans l'activity ManageFindKeysActivity
     *
     * @throws Exception
     */
    public void testGoManageFindKeysActivity() throws Exception {
        // On click sur le bouton qui emmene sur la page ManageGamesActivity
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.button_manage_find_key));

        // On test si on est bien dans l'activity ManageGamesActivity
        solo.assertCurrentActivity("Nous devrions être dans ManageFindKeysActivity et nous sommes dans : "
                + solo.getCurrentActivity(), ManageFindKeysActivity.class);
    }
}
