package net.kubokubo.exoplayer.streamtester;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.exoplayer.demo.PlayerActivity;

import java.util.ArrayList;

/**
 * Created by kubo on 11/12/15.
 */
public class DemoActivity extends Activity {

    private static final String TAG = "DemoActivity";

    ArrayList<Sample> mSamples;
    ArrayAdapter mSampleAdapter;
    ListView mSampleList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.demo_activity);

        mSampleList = (ListView) findViewById(R.id.custom_sample_list);
        final EditText inputStreamUlr = (EditText) findViewById(R.id.edit_text_stream_url);
        final EditText inputLicenseUrl = (EditText) findViewById(R.id.edit_text_license_url);
        Button submitButton = (Button) findViewById(R.id.play_button);

        mSamples = new ArrayList<>();
        mSamples.add(new Sample("http://demo.unified-streaming.com/video/smurfs/smurfs.ism/smurfs.mpd", null));
        mSamples.add(new Sample("http://dash.edgesuite.net/envivio/dashpr/clear/Manifest.mpd", null));
        mSampleAdapter = new ArrayAdapter<Sample>(this, android.R.layout.simple_list_item_1, mSamples) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    int layoutId = android.R.layout.simple_list_item_1;
                    view = LayoutInflater.from(getContext()).inflate(layoutId, null, false);
                }
                Object item = getItem(position);
                String name = null;
                if (item instanceof Sample) {
                    name = ((Sample) item).uri + (((Sample) item).licenseUri == null ? "" : " WITH LICENSE " + ((Sample) item).licenseUri);
                }
                ((TextView) view).setText(name);
                return view;
            }
        };

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSamples.add(new Sample(inputStreamUlr.getText().toString(), inputLicenseUrl.getText().toString()));
                mSampleAdapter.notifyDataSetChanged();
                onSampleSelected(new Sample(inputStreamUlr.getText().toString(), inputLicenseUrl.getText().toString()));
            }
        });

        mSampleList.setAdapter(mSampleAdapter);
        mSampleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "item was clicked");
                Object item = mSampleAdapter.getItem(position);
                if (item instanceof Sample) {
                    Log.d(TAG, "will play sample");
                    onSampleSelected((Sample) item);
                }
            }
        });
    }

    private void onSampleSelected(Sample sample) {
        if (sample.licenseUri == null) {
            Log.d(TAG, "ExoPlayer Demo PlayerActivity will play video");
            Intent mpdIntent = new Intent(this, PlayerActivity.class)
                    .setData(Uri.parse(sample.uri))
                    .putExtra(PlayerActivity.CONTENT_ID_EXTRA, "net.kubokubo.sampleid." + Math.random())
                    .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, PlayerActivity.TYPE_DASH);
            startActivity(mpdIntent);
        } else {
            Log.d(TAG, "WidevinePlayerActivity will play video");
            Intent mpdIntent = new Intent(this, WidevinePlayerActivity.class)
                    .setData(Uri.parse(sample.uri))
                    .putExtra(PlayerActivity.CONTENT_ID_EXTRA, "net.kubokubo.sampleid." + Math.random())
                    .putExtra(WidevinePlayerActivity.CONTENT_LICENSE_URI, sample.licenseUri);
            startActivity(mpdIntent);
        }
    }

    public static class Sample {

        public final String uri;
        public final String licenseUri;


        public Sample(String uri, String licenseUri) {
            this.licenseUri = licenseUri;
            this.uri = uri;
        }

    }

}
