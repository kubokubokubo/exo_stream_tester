package net.kubokubo.exoplayer.exowrapper.player;

import android.util.Log;
import android.view.Surface;

import java.net.URI;
import java.util.List;

/**
 * Created by kubo on 12/12/15.
 */
public class PlayerImpl implements Player {

    private static final String TAG = "PlayerImpl";

    @Override
    public void playUrl(Surface surface, URI streamUrl) {
        Log.d(TAG, "WILL PLAY VIDEO");
    }

    @Override
    public void setBitrate(int bitrate) {

    }

    @Override
    public void stop() {

    }

    @Override
    public List<Integer> getBitrates() {
        return null;
    }
}
