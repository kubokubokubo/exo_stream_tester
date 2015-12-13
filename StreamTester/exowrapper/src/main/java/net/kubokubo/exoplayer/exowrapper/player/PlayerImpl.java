package net.kubokubo.exoplayer.exowrapper.player;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaDrm;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

import com.google.android.exoplayer.CodecCounters;
import com.google.android.exoplayer.DummyTrackRenderer;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecTrackRenderer;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.TimeRange;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.audio.AudioTrack;
import com.google.android.exoplayer.chunk.ChunkSampleSource;
import com.google.android.exoplayer.chunk.Format;
import com.google.android.exoplayer.dash.DashChunkSource;
import com.google.android.exoplayer.drm.MediaDrmCallback;
import com.google.android.exoplayer.drm.StreamingDrmSessionManager;
import com.google.android.exoplayer.upstream.BandwidthMeter;
import com.google.android.exoplayer.util.PlayerControl;
import com.google.android.exoplayer.util.Util;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by kubo on 12/12/15.
 */
public class PlayerImpl implements Player,
        RendererBuilderCallback,
        BandwidthMeter.EventListener,
        StreamingDrmSessionManager.EventListener,
        DashChunkSource.EventListener,
        ChunkSampleSource.EventListener,
        MediaCodecVideoTrackRenderer.EventListener,
        MediaCodecAudioTrackRenderer.EventListener {

    // Constatnts
    private static final String TAG = "PlayerImpl";
    private static final String USER_AGENT_STRING = "PlayerImpl";
    public static final int RENDERERS_COUNT = 2;
    public static final int TYPE_VIDEO = 0;
    public static final int TYPE_AUDIO = 1;

    //State
    private boolean mPlayerNeedsPrepare;

    // Member variables
    private ExoPlayer mExoPlayer;
    private DashRendererBuilder mDashRendererBuilder;
    private PlayerControl mPlayerControl;
    private Handler mMainHandler;

    private Context mContext;
    private Surface mSurface;
    private SurfaceView mSurfaceView;
    private BandwidthMeter mBandwidthMeter;
    private TrackRenderer mVideoRenderer;
    private CodecCounters mCodecCounters;

    public interface RendererBuilder {
        void buildRenderers(PlayerImpl player);

        void cancel();
    }

    public PlayerImpl(SurfaceView surfaceView, Context context) {
        mMainHandler = new Handler();
        mSurfaceView = surfaceView;
        mContext = context;
        mExoPlayer = ExoPlayer.Factory.newInstance(RENDERERS_COUNT);
    }

    private void pushSurface(boolean blockForSurfacePush) {
        if (mVideoRenderer == null) {
            return;
        }

        if (blockForSurfacePush) {
            mExoPlayer.blockingSendMessage(
                    mVideoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, mSurface);
        } else {
            mExoPlayer.sendMessage(
                    mVideoRenderer, MediaCodecVideoTrackRenderer.MSG_SET_SURFACE, mSurface);
        }
    }

    private void preparePlayer(boolean playWhenReady) {
        if (mPlayerNeedsPrepare) {
           mExoPlayer.prepare();
            mPlayerNeedsPrepare = false;
        }
        setSurface(mSurfaceView.getHolder().getSurface());
        mExoPlayer.setPlayWhenReady(playWhenReady);
        mDashRendererBuilder.buildRenderers(this);
        Log.d(TAG, "prepared player");
    }

    public void setSurface(Surface surface) {
        mSurface = surface;
        pushSurface(false);
    }

    //- Player Interface implementation ------------------------------------------------------------

    @Override
    public void init(String streamUrl, String licenseUrl) {
        Log.d(TAG, "init: url " + streamUrl + (TextUtils.isEmpty(licenseUrl) ? " no license url" : ", license: " + licenseUrl));
        mDashRendererBuilder = new DashRendererBuilder(mContext, USER_AGENT_STRING, streamUrl, new WidevineMediaDrmCallback(licenseUrl));
        preparePlayer(true);
        //TODO
    }

    @Override
    public void init(String streamUrl) {
        init(streamUrl, null);
    }

    @Override
    public void setBitrate(int bitrate) {
        //TODO
    }

    @Override
    public void play() {
        //TODO
    }

    @Override
    public void pause() {
        //TODO
    }

    @Override
    public List<Integer> getBitrates() {
        //TODO
        return null;
    }


    //-  RendererBuilderCallback implementation ----------------------------------------------------

    @Override
    public Handler getMainHandler() {
        return mMainHandler;
    }

    @Override
    public void onRenderers(TrackRenderer[] renderers, BandwidthMeter bandwidthMeter) {
        Log.d(TAG, "onRenderers: " + renderers.length + " renderers");
        for (int i = 0; i < RENDERERS_COUNT; i++) {
            if (renderers[i] == null) {
                // Convert a null renderer to a dummy renderer.
                renderers[i] = new DummyTrackRenderer();
            }
        }
        // Complete preparation.
        mVideoRenderer = renderers[TYPE_VIDEO];
        mCodecCounters = mVideoRenderer instanceof MediaCodecTrackRenderer
                ? ((MediaCodecTrackRenderer) mVideoRenderer).codecCounters
                : renderers[TYPE_AUDIO] instanceof MediaCodecTrackRenderer
                ? ((MediaCodecTrackRenderer) renderers[TYPE_AUDIO]).codecCounters : null;
        mBandwidthMeter = bandwidthMeter;
        pushSurface(false);
        mExoPlayer.prepare(renderers);
//        rendererBuildingState = RENDERER_BUILDING_STATE_BUILT;
    }

    @Override
    public void onRenderersError(Exception e) {
        Log.e(TAG, "Renderers Error: " + e.getMessage());
    }

    @Override
    public Looper getPlaybackLooper() {
        return mExoPlayer.getPlaybackLooper();
    }

    //-  BandwidthMeter.EventListener implementation -----------------------------------------------

    @Override
    public void onBandwidthSample(int i, long l, long l1) {
        //TODO
    }

    //-  StreamingDrmSessionManager.EventListener implementation -----------------------------------

    @Override
    public void onDrmKeysLoaded() {
        //TODO
    }

    @Override
    public void onDrmSessionManagerError(Exception e) {
        //TODO
    }



    //- MediaCodecTrackRenderer.EventListener implementation ---------------------------------------

    @Override
    public void onDecoderInitializationError(MediaCodecTrackRenderer.DecoderInitializationException e) {

    }

    @Override
    public void onCryptoError(MediaCodec.CryptoException e) {

    }

    @Override
    public void onDecoderInitialized(String s, long l, long l1) {

    }

    //- MediaCodecVideoTrackRenderer.EventListener implementation ----------------------------------

    @Override
    public void onDroppedFrames(int i, long l) {

    }

    @Override
    public void onVideoSizeChanged(int i, int i1, int i2, float v) {

    }

    @Override
    public void onDrawnToSurface(Surface surface) {

    }

    //- MediaCodecAudioTrackRenderer.EventListener implementation ----------------------------------

    @Override
    public void onAudioTrackUnderrun(int i, long l, long l1) {

    }

    @Override
    public void onAudioTrackWriteError(AudioTrack.WriteException e) {

    }

    @Override
    public void onAudioTrackInitializationError(AudioTrack.InitializationException e) {

    }

    //- DashChunkSource.EventListener implementation -----------------------------------------------

    @Override
    public void onAvailableRangeChanged(TimeRange timeRange) {

    }

    //- ChunkSampleSource.EventListener implementation ---------------------------------------------

    @Override
    public void onLoadStarted(int i, long l, int i1, int i2, Format format, long l1, long l2) {

    }

    @Override
    public void onLoadCompleted(int i, long l, int i1, int i2, Format format, long l1, long l2, long l3, long l4) {

    }

    @Override
    public void onLoadCanceled(int i, long l) {

    }

    @Override
    public void onLoadError(int i, IOException e) {

    }

    @Override
    public void onUpstreamDiscarded(int i, long l, long l1) {

    }

    @Override
    public void onDownstreamFormatChanged(int i, Format format, int i1, long l) {

    }


    //- Internal classes ---------------------------------------------------------------------------

    @TargetApi(18)
    private class WidevineMediaDrmCallback implements MediaDrmCallback {
        private String mWidevineLicenseUri;

        public WidevineMediaDrmCallback(String licenseUri) {
            mWidevineLicenseUri = licenseUri;
        }

        @Override
        public byte[] executeProvisionRequest(UUID uuid, MediaDrm.ProvisionRequest request)
                throws IOException {
            String url = request.getDefaultUrl() + "&signedRequest=" + new String(request.getData());
            return Util.executePost(url, null, null);
        }

        @Override
        public byte[] executeKeyRequest(UUID uuid, MediaDrm.KeyRequest request) throws IOException {
            String url = request.getDefaultUrl();
            if (TextUtils.isEmpty(url)) {
                url = mWidevineLicenseUri;
            }
            return Util.executePost(url, request.getData(), null);
        }
    }
}
