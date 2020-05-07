package com.example.api_deezer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.AsyncDeezerTask;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterTracks;

public class activity_playlist extends AppCompatActivity implements AdapterTracks.OnItemClickListener {

    /** The list of tracks of displayed by this activity. */
    private List<Track> mTrackList = new ArrayList<Track>();

    private RecyclerView rv_tracks;
    private AdapterTracks adapterTracks;

    private DeezerConnect deezerConnect;
    private  Long prueba_id_playlist=3110421322L;
    private  String applicationID = "412262";

    private ImageView img_playlist_big;

    private TextView tv_playlist_name;
    private TextView tv_playlist_description;
    private TextView tv_playlist_num_tracks;
    private TextView tv_playlist_num_fans;

    private Long  playlist_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        playlist_send = getIntent().getExtras().getLong("playlist");


        img_playlist_big =findViewById(R.id.playlistIvBanner);
        tv_playlist_description=findViewById(R.id.playlisTvDescrip);
        tv_playlist_num_tracks=findViewById(R.id.playlistTvNumCanciones);
        tv_playlist_num_fans=findViewById(R.id.playlistTvNumFans);
        tv_playlist_name=findViewById(R.id.playlistTvNamePlaylist);

        rv_tracks = findViewById(R.id.playlistRecycler);
        rv_tracks.setLayoutManager(new LinearLayoutManager(this));
        adapterTracks = new AdapterTracks();
        adapterTracks.setListener(this);
        rv_tracks.setAdapter(adapterTracks);
        rv_tracks.setHasFixedSize(true);

        deezerConnect = new DeezerConnect(this, applicationID);


        showPlaylistElements(playlist_send);
    }

    public void  showPlaylistElements(Long playlistName){
        DeezerRequest request= DeezerRequestFactory.requestPlaylist(playlistName);
        AsyncDeezerTask task = new AsyncDeezerTask(deezerConnect,
                new JsonRequestListener() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void onResult(Object result, Object requestId) {

                        mTrackList.clear();

                        try {
                            mTrackList.addAll((List<Track>) ((Playlist) result).getTracks());
                        }
                        catch (ClassCastException e) {
                            Log.e(">>>",""+e.getMessage());
                        }

                        if (mTrackList.isEmpty()) {
                            Log.e(">>>","la lista esta vacia");

                        }
                        adapterTracks.showAllTracks((ArrayList<Track>) mTrackList);
                        //cambio la imagen grande

                        tv_playlist_name.setText(((Playlist) result).getTitle());
                        tv_playlist_description.setText(((Playlist) result).getDescription());
                        tv_playlist_num_tracks.setText(((Playlist) result).getTracks().size()+" songs");
                        tv_playlist_num_fans.setText(((Playlist) result).getFans()+" fans");

                        Glide.with(getApplicationContext()).load(((Playlist) result).getBigImageUrl()).into(img_playlist_big);


                    }

                    @Override
                    public void onUnparsedResult(final String response, final Object requestId) {
                        Log.e(">>>","unparse error");

                    }

                    @Override
                    public void onException(final Exception exception,
                                            final Object requestId) {
                        Log.e(">>>","exception "+exception.getMessage());
                    }

                });
        task.execute(request);
    }


    @Override
    public void onItemClick(Track track) {
        Intent i = new Intent(this, activity_track.class);
        i.putExtra("track", track.getId());
        startActivity(i);
    }

    public void onClick(View view){
        Intent miIntent = new Intent(activity_playlist.this, MainActivity.class );
        startActivity(miIntent);
    }
}
