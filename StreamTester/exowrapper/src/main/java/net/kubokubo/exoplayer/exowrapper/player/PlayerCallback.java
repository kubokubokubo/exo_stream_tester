package net.kubokubo.exoplayer.exowrapper.player;

/**
 * Created by kubo on 13/12/15.
 */
public interface PlayerCallback {

    void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthAspectRatio);

}
