package net.kubokubo.exoplayer.exowrapper.player;

import android.net.Uri;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by kubo on 12/12/15.
 */
public interface Player {

    public void init(SurfaceView surfaceView, Uri streamUrl);

    public void setBitrate(int bitrate);

    public void play();

    public void pause();

    public List<Integer> getBitrates();

}
