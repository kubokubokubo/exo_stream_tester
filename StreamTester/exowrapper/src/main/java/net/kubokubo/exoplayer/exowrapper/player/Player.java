package net.kubokubo.exoplayer.exowrapper.player;

import android.content.Context;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by kubo on 12/12/15.
 */
public interface Player {

    public void init(SurfaceView surfaceView, String streamUrl, String licenseUrl, Context context);

    public void setBitrate(int bitrate);

    public void play();

    public void pause();

    public List<Integer> getBitrates();

}
