package applicationTests.game;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;

import com.robotium.solo.Solo;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionJeu.FindTheKey.FindTheKey;

/**
 * Created by Marc on 01/06/2014.
 *
 * On test les méthodes du jeu FindTheKey.
 * La bonne incrémentation du nombre de clés trouvées lorsque
 * le joueur en trouve.
 * Que le joueur réussi bien le jeu lorsqu'il trouve toutes
 * les clés.
 * Que le joueur ne réussi pas le jeu s'il ne trouve pas toutes
 * les clés dans le temps imparti.
 */
public class FindTheKeyTest extends ActivityInstrumentationTestCase2<FindTheKey> {

    /**
     * Permet d'utiliser Robotium pour les test
     */
    Solo solo = null;
    /**
     * Activity SplashHeadUpsideDown que l'on veut tester
     */
    private FindTheKey activity;
    /**
     * ImageButton de la premiere cle a trouver
     */
    private ImageButton key1;
    /**
     * ImageButton de la deuxieme cle a trouver
     */
    private ImageButton key2;
    /**
     * ImageButton de la troisieme cle a trouver
     */
    private ImageButton key3;
    /**
     * ImageButton de la quatrieme cle a trouver
     */
    private ImageButton key4;
    /**
     * ImageButton de la cinquieme cle a trouver
     */
    private ImageButton key5;

    /**
     * Constructeur de notre classe de test
     */
    public FindTheKeyTest() {
        super(FindTheKey.class);
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
        // On vérifie qu'on est bien dans FindTheKey
        solo.assertCurrentActivity("Nous devrions être dans FindTheKey et nous sommes dans : "
                + solo.getCurrentActivity(), FindTheKey.class);
        // On appuie sur le bouton BACK de la tablette
        solo.getCurrentActivity().onBackPressed();
        // On vérifie qu'on est toujours dans FindTheKey
        solo.assertCurrentActivity("Nous devrions être dans FindTheKey et nous sommes dans : "
                + solo.getCurrentActivity(), FindTheKey.class);
    }

    /**
     * Vérifie que la variable qui compte le nombre de clés trouvées
     * s'incrémente bien de 1 lorsque le joueur trouve une clé.
     */
    public void testCountKeysFound(){
        // On récupere les vues
        key1 = (ImageButton) activity.findViewById(R.id.first_key_to_find);
        key2 = (ImageButton) activity.findViewById(R.id.second_key_to_find);
        key3 = (ImageButton) activity.findViewById(R.id.third_key_to_find);
        key4 = (ImageButton) activity.findViewById(R.id.fourth_key_to_find);
        key5 = (ImageButton) activity.findViewById(R.id.fifth_key_to_find);

        // On ne veut pas changer l'etat du joueur
        // et lancer d'autres méthodes de la machine à
        // états.
        activity.setGoChangeEtat(false);

        // On fait les tests/vérification que countKeyFound a la bonne valeur
        // durant le cours de la partie.
        assertEquals("Nombre de clés doit etre égal à 0", activity.getCountKeyFound(), 0);
        solo.clickOnView(key1);
        assertEquals("Nombre de clés doit être égal à 1", activity.getCountKeyFound(), 1);
        solo.clickOnView(key2);
        assertEquals("Nombre de clés doit être égal à 2", activity.getCountKeyFound(), 2);
        solo.clickOnView(key3);
        assertEquals("Nombre de clés doit être égal à 3", activity.getCountKeyFound(), 3);
        solo.clickOnView(key4);
        assertEquals("Nombre de clés doit être égal à 4", activity.getCountKeyFound(), 4);
        solo.clickOnView(key5);
        assertEquals("Nombre de clés doit être égal à 5", activity.getCountKeyFound(), 5);
    }

    /**
     * On test l'echec du défi, c'est à dire lorsque
     * le joueur n'a trouvé aucunes clés dans le temps
     * qui lui était imparti.
     */
    public void testLooseTheGame(){
        // On ne veut pas changer l'etat du joueur
        // et lancer d'autres méthodes de la machine à
        // états.
        activity.setGoChangeEtat(false);

        // Il ne reste plus de temps pour le joueur
        // pour esperer réussir le défi, et il n'a
        // trouvé aucunes clés.
        activity.setTotalTime(0);

        // On vérifie que le jeu n'est pas réussi
        assertFalse(activity.getIsSucceed());
    }
}