package net.kubokubo.exoplayer.exowrapper.player;

import android.os.Handler;
import android.os.Looper;

import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.upstream.BandwidthMeter;

/**
 * Created by kubo on 12/12/15.
 */
public interface RendererBuilderCallback {
    Handler getMainHandler();

    void onRenderers(TrackRenderer[] renderers, BandwidthMeter bandwidthMeter);

    void onRenderersError(Exception e);

    Looper getPlaybackLooper();
}
