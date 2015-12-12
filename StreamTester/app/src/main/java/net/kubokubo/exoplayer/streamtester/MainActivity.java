package net.kubokubo.exoplayer.streamtester;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import net.kubokubo.exoplayer.exowrapper.player.Player;
import net.kubokubo.exoplayer.exowrapper.player.PlayerImpl;

/**
 * Created by kubo on 11/12/15.
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private Player mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.main_activity);
        mPlayer = new PlayerImpl();
    }

}
