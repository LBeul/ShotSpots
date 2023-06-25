package com.lbeul.shotspots_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.utils.MathUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LocationRecyclerViewAdapter extends RecyclerView.Adapter<LocationRecyclerViewAdapter.LocationViewHolder> {
    private final Context ctx;
    private final InMemoryDatabase db;

    public LocationRecyclerViewAdapter(Context ctx, InMemoryDatabase db) {
        this.ctx = ctx;
        this.db = db;
    }

    @NonNull
    @Override
    public LocationRecyclerViewAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.location_card, parent, false);
        return new LocationRecyclerViewAdapter.LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationRecyclerViewAdapter.LocationViewHolder holder, int position) {
        String lonString = "Longitude: " + MathUtil.round(db.getAllImages().get(position).getGeoPoint().getLongitude(), 5);
        String latString = "Latitude: " + MathUtil.round(db.getAllImages().get(position).getGeoPoint().getLatitude(),5);

        holder.uriLabel.setText(db.getAllImages().get(position).getImageURI());
        holder.lonLabel.setText(lonString);
        holder.latLabel.setText(latString);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm");
        holder.dateLabel.setText(simpleDateFormat.format(db.getAllImages().get(position).getCreationTimeStamp()));
        String camString = "Shot on an " + db.getAllImages().get(position).getCameraManufacturer() + " device";
        holder.camLabel.setText(camString);
        holder.imageView.setImageResource(R.drawable.pin); // Find a static map image api
    }

    @Override
    public int getItemCount() {
        return db.getAllImages().size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView uriLabel, latLabel, lonLabel, dateLabel, camLabel;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            uriLabel = itemView.findViewById(R.id.uri_label);
            latLabel = itemView.findViewById(R.id.lat_label);
            lonLabel = itemView.findViewById(R.id.lon_label);
            dateLabel = itemView.findViewById(R.id.date_label);
            camLabel = itemView.findViewById(R.id.cam_label);
        }
    }
}
