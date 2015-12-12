package net.kubokubo.exoplayer.exowrapper.player;

import android.view.Surface;

import java.net.URI;
import java.util.List;

/**
 * Created by kubo on 12/12/15.
 */
public interface Player {

    public void playUrl(Surface surface, URI streamUrl);

    public void setBitrate(int bitrate);

    public void stop();

    public List<Integer> getBitrates();

}
