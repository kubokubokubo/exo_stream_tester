package net.kubokubo.exoplayer.streamtester;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import net.kubokubo.exoplayer.exowrapper.player.Player;
import net.kubokubo.exoplayer.exowrapper.player.PlayerImpl;

/**
 * Created by kubo on 11/12/15.
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private Player mPlayer;
    private SurfaceView mSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.main_activity);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_video);
        mPlayer = new PlayerImpl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri streamUri = Uri.parse("http://dash.edgesuite.net/envivio/dashpr/clear/Manifest.mpd");
        mPlayer.init(mSurfaceView, streamUri);
        mPlayer.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.pause();
    }
}
