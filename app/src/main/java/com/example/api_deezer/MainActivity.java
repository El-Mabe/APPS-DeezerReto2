package com.example.api_deezer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.AsyncDeezerTask;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;

import java.util.ArrayList;
import java.util.List;

import adapter.AdapterPlaylist;


public class MainActivity extends AppCompatActivity implements AdapterPlaylist.OnItemClickListener{

    // List of playlist that will be shown
    private List<Playlist> playlistsList = new ArrayList<Playlist>();

    private TextView sv_playlist;
    private ImageButton btn_search_playlist;

    private RecyclerView rv_playlist;
    private AdapterPlaylist adapterPlaylist;

    private String playlist_default="";

    private DeezerConnect deezerConnect;

    private  String applicationID = "412262";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_search_playlist =  findViewById(R.id.mainBtnSearch);
        sv_playlist =findViewById(R.id.mainEtSearchPlaylist);
        rv_playlist = findViewById(R.id.mainRecycler);
        rv_playlist.setLayoutManager(new LinearLayoutManager(this));
        adapterPlaylist = new AdapterPlaylist();
        adapterPlaylist.setListener(this);
        rv_playlist.setAdapter(adapterPlaylist);
        rv_playlist.setHasFixedSize(true);

        deezerConnect = new DeezerConnect(this, applicationID);

        searchPlaylist(playlist_default);

        btn_search_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(">>>","entra al onClick");

                searchPlaylist(sv_playlist.getText().toString());
            }
        });

    }

    public void searchPlaylist(String playlisName){
        DeezerRequest request = DeezerRequestFactory.requestSearchPlaylists(playlisName);
        AsyncDeezerTask task = new AsyncDeezerTask(deezerConnect, new JsonRequestListener() {


            @SuppressWarnings("unchecked")
            @Override
            public void onResult(final Object result, final  Object requestId) {
                playlistsList.clear();

                try {
                    playlistsList.addAll((List<Playlist>) result);
                }
                catch (ClassCastException e){
                    Log.e(">>>", e.getMessage()+"");
                }
                if (playlistsList.isEmpty()){
                    Log.e(">>>","La lista esta vacia");
                }

                adapterPlaylist.modifyData((ArrayList<Playlist>) playlistsList);
                adapterPlaylist.notifyDataSetChanged();
                adapterPlaylist.showAllPlaylist((ArrayList<Playlist>) playlistsList);


                Log.e(">>>","");
            }


            @Override
            public void onUnparsedResult(String s, Object o) {
                Log.e(
                        ">>>", "unparse error"
                );
            }

            @Override
            public void onException(Exception e, Object o) {

            }
        });
        task.execute(request);
    }


    @Override
    public void onItemClick(Playlist playlist) {
        Intent i = new Intent(this, activity_playlist.class);
        i.putExtra("playlist", playlist.getId());
        startActivity(i);
    }
}
