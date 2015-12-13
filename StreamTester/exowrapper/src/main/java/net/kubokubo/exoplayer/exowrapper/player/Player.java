package net.kubokubo.exoplayer.exowrapper.player;

import java.util.List;

/**
 * Created by kubo on 12/12/15.
 */
public interface Player {

    //TODO release the ExoPlayer

    public void init(String streamUrl);

    public void init(String streamUrl, String licenseUrl);

    public void setBitrate(int bitrate);

    public void play();

    public void pause();

    public List<Integer> getBitrates();

}
