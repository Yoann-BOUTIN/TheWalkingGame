package applicationTests;

import com.thewalkinggame.app.gestionUser.User;

import junit.framework.TestCase;

/**
 * Created by Yoann on 01/06/2014.
 */
public class UserTest extends TestCase {
    /**
     * String qui va etre manipule pour simuler le pseudo
     */
    String pseudo;
    /**
     * Objet user sur lequel on va effectuer les tests
     */
    User testUser;

    /**
     * On verifie que la fonction getPseudo rend bien le pseudo
     */
    public void testGetPseudo(){
        pseudo = "testPseudo";
        testUser = new User(pseudo);
        assertTrue(testUser.getPseudo() == "testPseudo");
    }

    /**
     * On verifie qu'un pseudo vide n'est pas valide
     */
    public void testEmptyPseudo(){
        pseudo = "";
        testUser = new User(pseudo);
        assertFalse(testUser.isValid());
    }

    /**
     * On verifie qu'un pseudo de moins de 4 caracteres n'est pas valide
     */
    public void testTooShortPseudo(){
        pseudo = "";
        for(int i = 0; i < 3; i++){
            pseudo += "a";
            testUser = new User(pseudo);
            assertFalse(testUser.isValid());
        }
    }

    /**
     * On verifie qu'un pseudo de plus de 10 caracteres n'est pas valide
     */
    public void testTooLongPseudo(){
        pseudo = "aaaaaaaaaa";
        testUser = new User(pseudo);
        assertTrue(testUser.isValid());
        for (int i = 0; i < 5; i++){
            pseudo += "a";
            testUser = new User(pseudo);
            assertFalse(testUser.isValid());
        }
    }

    /**
     * On verifie qu'un pseudo contenant des symboles autre que des lettres n'est pas valide
     */
    public void testWrongCharPseudo(){
        pseudo = "*1/-";
        testUser = new User(pseudo);
        assertFalse(testUser.isValid());
        pseudo = "aaaa12aa";
        testUser = new User(pseudo);
        assertFalse(testUser.isValid());
    }

    /**
     * On verifie qu'un pseudo ayant une longueur comprise entre 4 et 10 caracteres est valide
     */
    public void testGoodLengthPseudo(){
        pseudo = "aaaa";
        for(int i = 0; i < 6; i++){
            testUser = new User(pseudo);
            assertTrue(testUser.isValid());
            pseudo += "a";
        }
    }

    /**
     * On verifie qu'un pseudo ayant des lettres minuscules et majuscules est valide
     */
    public void testGoodCharPseudo(){
        pseudo = "aaaa";
        for(int i = 0; i < 6; i++){
            testUser = new User(pseudo);
            assertTrue(testUser.isValid());
            pseudo+="A";
        }
    }
}
