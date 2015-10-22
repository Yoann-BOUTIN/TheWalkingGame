package applicationTests;

import android.os.Handler;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.thewalkinggame.app.MapFragment;

import junit.framework.TestCase;

/**
 * Created by kevin on 26/03/2014.
 *
 * On test la bonne récupération de la Google Map
 * via notre fragment.
 */

public class MapFragmentTest extends TestCase
{
    /**
     * Le fragment dans lequel est la map
     */
    MapFragment mapFrag = new MapFragment();
    /**
     * La Google Map
     */
    GoogleMap map;
    /**
     * La Google Map
     */
    GoogleMap mapTest;

    /**
    * This is a test to ensure that we retrieve GoogleMap without problems
     *
    * @throws Exception
    */
    public void testToGetMap() throws Exception {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mapTest = SupportMapFragment.newInstance().getMap();
                map = mapFrag.getMap();
                if(map==null){
                    handler.postDelayed(this, 500);
                }else{
                    assertNotNull(map);
                    assertNotNull(mapTest);
                    assertEquals(map, mapTest);
                }
            }
        }, 500);
    }
}

