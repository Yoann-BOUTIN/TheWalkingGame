package applicationTests;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.thewalkinggame.app.R;

/**
 * Class utilisée pour tester une class de type
 * Fragment.
 */
public class NullTestActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_test);
    }

}
