package net.kubokubo.exoplayer.streamtester;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.exoplayer.demo.PlayerActivity;

import java.util.Locale;

/**
 * Created by kubo on 11/12/15.
 */
public class DemoActivity extends Activity {

    private static final String TAG = "DemoActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.demo_activity);

        ListView sampleList = (ListView) findViewById(R.id.custom_sample_list);
        final SampleAdapter sampleAdapter = new SampleAdapter(this);

        Sample[] samples = new Sample[]{
                new Sample("Google Glass",
                        "http://www.youtube.com/api/manifest/dash/id/bf5bb2419360daf1/source/youtube?"
                                + "as=fmp4_audio_clear,fmp4_sd_hd_clear&sparams=ip,ipbits,expire,source,id,as&ip=0.0.0.0&"
                                + "ipbits=0&expire=19000000000&signature=51AF5F39AB0CEC3E5497CD9C900EBFEAECCCB5C7."
                                + "8506521BFC350652163895D4C26DEE124209AA9E&key=ik0", PlayerActivity.TYPE_DASH),
                new Sample("Google Play",
                        "http://www.youtube.com/api/manifest/dash/id/3aa39fa2cc27967f/source/youtube?"
                                + "as=fmp4_audio_clear,fmp4_sd_hd_clear&sparams=ip,ipbits,expire,source,id,as&ip=0.0.0.0&"
                                + "ipbits=0&expire=19000000000&signature=A2716F75795F5D2AF0E88962FFCD10DB79384F29."
                                + "84308FF04844498CE6FBCE4731507882B8307798&key=ik0", PlayerActivity.TYPE_DASH),
        };
        sampleAdapter.addAll(samples);

        sampleList.setAdapter(sampleAdapter);
        sampleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = sampleAdapter.getItem(position);
                if (item instanceof Sample) {
                    onSampleSelected((Sample) item);
                }
            }
        });
    }

    private void onSampleSelected(Sample sample) {
        Intent mpdIntent = new Intent(this, PlayerActivity.class)
                .setData(Uri.parse(sample.uri))
                .putExtra(PlayerActivity.CONTENT_ID_EXTRA, sample.contentId)
                .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, sample.type);
        startActivity(mpdIntent);
    }

    //private classes

    private static class SampleAdapter extends ArrayAdapter<Object> {

        public SampleAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                int layoutId = getItemViewType(position) == 1 ? android.R.layout.simple_list_item_1
                        : R.layout.sample_chooser_inline_header;
                view = LayoutInflater.from(getContext()).inflate(layoutId, null, false);
            }
            Object item = getItem(position);
            String name = null;
            if (item instanceof Sample) {
                name = ((Sample) item).contentId;
            }
            ((TextView) view).setText(name);
            return view;
        }

        @Override
        public int getItemViewType(int position) {
            return (getItem(position) instanceof Sample) ? 1 : 0;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

    }

    public static class Sample {

        public final String name;
        public final String contentId;
        public final String uri;
        public final int type;

        public Sample(String name, String uri, int type) {
            this(name, name.toLowerCase(Locale.US).replaceAll("\\s", ""), uri, type);
        }

        public Sample(String name, String contentId, String uri, int type) {
            this.name = name;
            this.contentId = contentId;
            this.uri = uri;
            this.type = type;
        }
    }
}
