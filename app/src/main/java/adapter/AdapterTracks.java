package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.model.Track;
import com.example.api_deezer.R;

import java.util.ArrayList;



public class AdapterTracks extends RecyclerView.Adapter<AdapterTracks.CustomViewHolder> {

    ArrayList<Track> data;

    public void showAllTracks(ArrayList<Track> allTracks) {
        for(int i = 0 ; i<allTracks.size() ; i++){
            if(!data.contains(allTracks.get(i))) data.add(allTracks.get(i));
        }
        //que ya el adapter tiene valores
        notifyDataSetChanged();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTracks(){ data = new ArrayList<>();    }

    @Override
    public AdapterTracks.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.track_list, parent, false);
        AdapterTracks.CustomViewHolder vh = new AdapterTracks.CustomViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        ImageView img = holder.root.findViewById(R.id.trackImage);
        Glide.with(holder.root.getContext()).load(data.get(position).getAlbum().getMediumImageUrl()).into(img);

        ((TextView) holder.root.findViewById(R.id.trackName)).setText(data.get(position).getShortTitle()+"");
        ((TextView) holder.root.findViewById(R.id.trackArtist)).setText(data.get(position).getArtist().getName()+"");
        Long idTrack=data.get(position).getId();
        //getReleaseyear sale error al utilizarlo y al buscar veo que el json de respuesta no lo tiene esta deprecated por eso el metodo
        ((TextView) holder.root.findViewById(R.id.trackYear)).setText(data.get(position).getDiscNumber()+"");

        holder.root.findViewById(R.id.track_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(data.get(position));
            }
        });

    }
    public void modifyData(ArrayList<Track> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //OBSERVER
    public interface OnItemClickListener{
        void onItemClick(Track track);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }


}
