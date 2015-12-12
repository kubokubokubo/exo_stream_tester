package net.kubokubo.exoplayer.exowrapper.player;

import android.net.Uri;
import android.os.Handler;
import android.view.SurfaceView;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.util.PlayerControl;

import java.util.List;

/**
 * Created by kubo on 12/12/15.
 */
public class PlayerImpl implements Player {

    private static final String TAG = "PlayerImpl";
    private static final int RENDERERS_COUNT = 2;

    private ExoPlayer mExoPlayer;
    private DashRendererBuilder mDashRendererBuilder;
    private PlayerControl mPlayerControl;
    private Handler mMainHandler;

    @Override
    public void init(SurfaceView surfaceView, Uri streamUri) {
        mExoPlayer = ExoPlayer.Factory.newInstance(RENDERERS_COUNT);
    }

    @Override
    public void setBitrate(int bitrate) {

    }

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public List<Integer> getBitrates() {
        return null;
    }
}
