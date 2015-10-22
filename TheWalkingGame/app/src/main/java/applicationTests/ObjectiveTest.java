package applicationTests;

import android.location.Location;

import com.thewalkinggame.app.gestionJeu.Objective;

import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Yoann on 01/06/2014.
 */
public class ObjectiveTest extends TestCase {
    /**
     * Objet  objectif que l'on va tester
     */
    Objective objectif;
    /**
     * Position objectif genere
     */
    Location ml ;
    /**
     * Position objectif genere
     */
    Location m2;
    /**
     * Position objectif genere
     */
    Location m3;
    /**
     * Tableau comportant tous les objectifs
     */
    double[][] tabOjectives;
    /**
     * Tableau ayant le meme nombre de case que tabOjectives genere avec des booleans a true qui passeront false quand l'objectif
     * aura ete utilise
     */
    boolean[] objectiveAvailable;
    /**
     * Nombre d'objectif
     */
    int nbObjectives;

    public void setUp()
    {

        // Mockito creation
        ml = mock(Location.class);
        // Mockito methods
        when(ml.getLatitude()).thenReturn(42.0);
        when(ml.getLongitude()).thenReturn(71.22);

        // Mockito creation
        m2 = mock(Location.class);
        // Mockito methods
        when(m2.getLatitude()).thenReturn(40.0);
        when(m2.getLongitude()).thenReturn(70.0);

        // Mockito creation
        m3 = mock(Location.class);
        // Mockito methods
        when(m3.getLatitude()).thenReturn(30.0);
        when(m3.getLongitude()).thenReturn(60.0);

        // New Objective
        objectif = new Objective(ml.getLatitude(),ml.getLongitude());
        nbObjectives = objectif.getNbObjectives();
        tabOjectives = new double[nbObjectives][2];
        tabOjectives = objectif.getTabOjectives();
        objectiveAvailable = new boolean[nbObjectives];
        objectiveAvailable = objectif.getObjectiveAvailable();

        //Beginning with Lattitude && Longitude == 0
        assertTrue(objectif.getLatitude() == 42.0);
        assertTrue(objectif.getLongitude() == 71.22);
        /**
         * Verification que le tableau objectiveAvailable soit bien cree avec des valeurs true pour chacun des objectifs
         */
        for(int i = 0 ; i < nbObjectives ; i++){
            assertTrue(objectiveAvailable[i] == true);
        }
    }

    /**
     * On test de changer la latitude et de la recuperer pour verifier qu'elle a bien change
     */
    public void testSetLatitude(){
        objectif.setLatitude(m2.getLatitude());
        assertTrue(objectif.getLatitude() == m2.getLatitude());
    }

    /**
     * On test de changer la longitude et de la recuperer pour verifier qu'elle a bien change
     */
    public void testSetLongitude(){
        objectif.setLongitude(m2.getLongitude());
        assertTrue(objectif.getLongitude() == m2.getLongitude());
    }

    /**
     * On change d'objectif autant de fois qu'il y en a pour verifier qu'une fois fait toutes les cases du tableau
     * objectiveAvailable contiennent la valeur false
     */
    public void testNewObjectiveLocation(){
        for (int i = 0; i<nbObjectives;i++){
            objectif.newObjectiveLocation();
        }
        for (int i = 0; i<nbObjectives;i++){
            assertTrue(objectiveAvailable[i] == false);
        }
    }
}
