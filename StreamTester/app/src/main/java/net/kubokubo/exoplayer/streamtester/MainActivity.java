package net.kubokubo.exoplayer.streamtester;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by kubo on 11/12/15.
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.main_activity);
    }

}
