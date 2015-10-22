package applicationTests;

import android.location.Location;

import com.thewalkinggame.app.gestionJeu.Objective;
import com.thewalkinggame.app.gestionUser.UserLocation;

import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
* Created by kevin on 26/03/2014.
 *
 * On test la mise à jour de la position du joueur.
 * Que l'utilisateur n'a pas de nouvel objectif durant
 * son temps de pénalité.
 * Test aussi le fait qu'un nouvel objectif n'a pas
 * la même position que celui que vient de faire le joueur.
 *
*/
public class UserLocationTest extends TestCase
{
    /**
     * Permet de récuperer la position du joueur.
     * (latitude, longitude)
     */
    UserLocation userlocation;
    /**
     * Mock Object correspondant à un Location,
     * qui simule donc la position d'un joueur
     * et donc le return des méthodes getLatitude
     * et getLongitude.
     */
    Location ml ;
    /**
     * L'objectif que le joueur cherche à atteindre
     */
    Objective ob ;

    /**
     * initialization before tests
     */
    public void setUp()
    {
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
        userlocation = new UserLocation("Test1");

        //Beginning with Lattitude && Longitude == 0
        assertTrue(userlocation.getLatitude() == 0);
        assertTrue(userlocation.getLongitude() == 0);
    }

    /**
     * This method tests if the location of the user is really updated
     */
    public void testDrawMyLocationWithMarker()
    {
        //Test de méthode UpdateLocation
        userlocation.updateMyLocation(ml);

        // Vérification de la modification de Lattitude / Longitude
        assertEquals(userlocation.getLatitude(),ml.getLatitude());
        assertEquals(userlocation.getLongitude(),ml.getLongitude());
    }

    /**
     * This methods tests that the user has not a new objective
     * durig his penality time
     */
    public void testPenality()
    {
        //Ensure user is not yet on objective
        assertFalse(userlocation.isOnObjective(ob));

        userlocation.updateMyLocation(ml);

        //EnsureUser is on the Objective
        assertTrue(userlocation.isOnObjective(ob));

        //Assert there is no new objective
        assertTrue(ob.getLattitudePreviousObjective() == 0.0);
        assertTrue(ob.getLongitudePreviousObjective() == 0.0);

        //New objective after time penality
        ob.newObjectiveLocation();

        //Assert there is a new objective
        assertTrue(ob.getLattitudePreviousObjective() != 0.0 && ob.getLongitudePreviousObjective() != 0.0);

    }

    /**
     * This method tests if a new objective is really created
     * when the user arrived at the initial objective
     */
    public void testNewObjective()
    {
        //Ensure user is not yet on objective
        assertFalse(userlocation.isOnObjective(ob));

        userlocation.updateMyLocation(ml);

        //EnsureUser is on the Objective
        assertTrue(userlocation.isOnObjective(ob));

        //New Objectif
        ob.newObjectiveLocation();

        //Assert User is not on te Objective
        //so assert that it's a new objective
        assertTrue(ob.getLatitude() != userlocation.getLatitude() && ob.getLongitude() != userlocation.getLongitude());
    }
}