package net.kubokubo.exoplayer.streamtester;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.google.android.exoplayer.AspectRatioFrameLayout;

import net.kubokubo.exoplayer.exowrapper.player.Player;
import net.kubokubo.exoplayer.exowrapper.player.PlayerCallback;
import net.kubokubo.exoplayer.exowrapper.player.PlayerImpl;

/**
 * Created by kubo on 11/12/15.
 */
public class MainActivity extends Activity implements PlayerCallback {

    private static final String TAG = "MainActivity";

    private Player mPlayer;
    private SurfaceView mSurfaceView;
    private AspectRatioFrameLayout mVideoFrame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.main_activity);
        mVideoFrame = (AspectRatioFrameLayout) findViewById(R.id.video_frame);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_video);
        mPlayer = new PlayerImpl(mSurfaceView, this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String streamUrl = "http://dash.edgesuite.net/envivio/dashpr/clear/Manifest.mpd";
        mPlayer.init(streamUrl);
        mPlayer.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.pause();
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees,
                                   float pixelWidthAspectRatio) {
        mVideoFrame.setAspectRatio(
                height == 0 ? 1 : (width * pixelWidthAspectRatio) / height);
    }
}
