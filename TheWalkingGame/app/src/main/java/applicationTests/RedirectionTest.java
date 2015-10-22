package applicationTests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.gestionTeam.InvitationActivity;
import com.thewalkinggame.app.MainActivity;
import com.thewalkinggame.app.gestionUser.ProfilActivity;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionTeam.RecrutementActivity;
import com.thewalkinggame.app.TabActionBar;

/**
* On test le bon redirectionnement des boutons vers l'activity
* qui leur correspond.
*
* Created by Marc on 06/04/2014.
*/
public class RedirectionTest extends ActivityInstrumentationTestCase2<MainActivity> {

    /**
     * Permet d'utiliser Robotium pour les test
     */
    private Solo solo = null;

    /**
     * Constructeur de la class de test
     */
    public RedirectionTest() {
        super(MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    /**
     * Se lance aprés chaque fin de méthode de test.
     * Permet de fermer les activities.
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
        solo.assertCurrentActivity("Nous devrions être dans MainActivity et nous somme dans : "
                + solo.getCurrentActivity(),MainActivity.class);
    }

    /**
     * On vérifie qu'à la suite du click sur le bouton "Profil"
     * du menu, on arrive bien sur l'activity correspondant à ce bouton,
     * c'est à dire ProfilActivity.
     *
     * @throws Exception
     */
    public void testGoProfilActivity() throws Exception {
        // On click sur le bouton qui emmene sur la page de profil
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.boutonToProfil));

        // On test si on est bien dans l'activity correspondant au profil
        solo.assertCurrentActivity("Nous devrions être dans ProfilActivity et nous sommes dans : "
                + solo.getCurrentActivity(), ProfilActivity.class);
    }

    /**
     * On vérifie qu'à la suite du click sur le bouton "Journal d'invitations"
     * du menu, on arrive bien sur l'activity correspondant à ce bouton,
     * c'est à dire InvitationActivity.
     *
     * @throws Exception
     */
    public void testGoInvitationActivity() throws Exception {
        // On click sur le bouton qui emmene sur la page du journal d'invitation
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.boutonToInvitation));

        // On test si on est bien dans l'activity correspondant au journal d'invitation
        solo.assertCurrentActivity("Nous devrions être dans InvitationActivity et nous sommes dans : "
                + solo.getCurrentActivity(), InvitationActivity.class);
    }

    /**
     * On vérifie qu'à la suite du click sur le bouton "Recruter des survivants"
     * du menu, on arrive bien sur l'activity correspondant à ce bouton,
     * c'est à dire RecrutementActivity.
     *
     * @throws Exception
     */
    public void testGoRecruter() throws Exception {
        // On click sur le bouton qui emmene sur la page de recrutement de joueurs
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.boutonToRecrute));

        // On test si on est bien dans l'activity correspondant au recrutement de joueurs
        solo.assertCurrentActivity("Nous devrions être dans RecrutementActivity et nous sommes dans : "
                + solo.getCurrentActivity(), RecrutementActivity.class);
    }

    /**
     * On vérifie qu'à la suite du click sur le bouton "Lancer le test 1 joueur"
     * du menu, on arrive bien sur l'activity correspondant à ce bouton,
     * c'est à dire MapActivity.
     *
     * @throws Exception
     */
    public void testGoMap() throws Exception {
        // On click sur le bouton qui emmene sur la page du test 1 joueur
        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.boutonToGame));

        // On test si on est bien dans l'activity correspondant au test 1 joueur
        solo.assertCurrentActivity("Nous devrions être dans MapActivity et nous sommes dans : "
                + solo.getCurrentActivity(), TabActionBar.class);
    }
}
