package com.example.api_deezer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.AsyncDeezerTask;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;

public class activity_track extends AppCompatActivity {

    private ImageView img_track;
    private TextView tv_name_track;
    private TextView tv_artist_track;
    private TextView tv_album_track;
    private TextView tv_duration_track;
    private Button btn_listen_track;


    private DeezerConnect deezerConnect;
    private  Long track_id_send;
    private  String applicationID = "412262";

    private Track track_actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancion);

        track_id_send=getIntent().getExtras().getLong("track");



        img_track =findViewById(R.id.songImageCancion);
        tv_name_track=findViewById(R.id.songTvNameCancion);
        tv_artist_track=findViewById(R.id.songTvAlbumCancion);
        tv_album_track=findViewById(R.id.songTvAlbumCancion);
        tv_duration_track=findViewById(R.id.songTvDuraCancion);
        btn_listen_track=findViewById(R.id.songBtnPlay);
        btn_listen_track.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity_track.this, activity_web_view.class);
                i.putExtra("url_song", track_actual.getLink());
                startActivity(i);
                Log.e(">>>",track_actual.getLink()+"");

            }
        });


        deezerConnect = new DeezerConnect(this, applicationID);
        //DeezerConnect deezerConnect = DeezerConnect.forApp(applicationID). withContext(this). build();

        DeezerRequest request= DeezerRequestFactory.requestTrack(track_id_send);
        AsyncDeezerTask task = new AsyncDeezerTask(deezerConnect,
                new JsonRequestListener() {

                    @SuppressWarnings("unchecked")
                    @Override
                    public void onResult(Object result, Object requestId) {

                        track_actual=(Track) result;

                        tv_name_track.setText(((Track) result).getShortTitle());
                        tv_artist_track.setText(((Track) result).getArtist().getName());
                        tv_album_track.setText(((Track) result).getAlbum().getTitle());

                        int duracionSec=((Track) result).getDuration();
                        int hours =duracionSec / 3600;
                        int minutes = (duracionSec % 3600) / 60;
                        int seconds = duracionSec % 60;

                        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                        tv_duration_track.setText(timeString+"");

                        Glide.with(getApplicationContext()).load(((Track) result).getAlbum().getMediumImageUrl()).into(img_track);


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

    public void onClick(View view){

    }
}
