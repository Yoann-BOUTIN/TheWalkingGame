package applicationTests;

import com.thewalkinggame.app.gestionUser.User;

import junit.framework.TestCase;

/**
 * Created by Yoann on 25/03/2014.
 *
 * Dans cette classe on test la connexion/création d'un
 * utilisateur, on test le bon fonctionnement de la fonction
 * qui permet de créér un utilisateur si son pseudo est
 * valide (dans les normes de l'expression régulière donnée).
 */
public class ConnexionTest extends TestCase {

    /**
     * Test de création d'un utilisateur.
     *
     * @throws Exception
     */
    public void testUser() throws Exception{
        //test la bonne récuperation du pseudo
        User first_user = new User("Yop");
        assertEquals("Yop", first_user.getPseudo());
    }

    /**
     * Test la validité d'un pseudo pour un utilisateur.
     *
     * @throws Exception
     */
    public void testIsValid() throws Exception{
        String s = "";
        // test qu'un pseudo doit avoir une taille comprise entre 4 et 10 caracteres
        for (int i = 0; i < 15; i++){
            User user = new User(s);
            if ((s.length() < 4) || (s.length() > 10)){
                assertFalse(user.isValid());
            }
            else if ((s.length() >= 4) && (s.length() <= 10)){
                assertTrue(user.isValid());
            }
            s += "a";
        }

        // test qu'un utilisateur a le droit d'utiliser des majuscules dans son pseudo
        User userMaj = new User("AAAA");
        assertTrue(userMaj.isValid());

        // test qu'un utilisateur ne puisse pas mettre de symboles dans son pseudo
        User userSymbol = new User("abh.f");
        assertFalse(userSymbol.isValid());

        // test qu'un utilisateur ne puisse pas mettre de chiffres dans son pseudo
        User userChiffre = new User("abf156");
        assertFalse(userChiffre.isValid());
    }
}
