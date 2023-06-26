package com.lbeul.shotspots_v2.views.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lbeul.shotspots_v2.R;
import com.lbeul.shotspots_v2.models.imageData.ImageData;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.DatabaseException;
import com.lbeul.shotspots_v2.models.inMemoryDatabase.InMemoryDatabase;
import com.lbeul.shotspots_v2.utils.MathUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

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

    @SuppressLint({"SimpleDateFormat", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull LocationRecyclerViewAdapter.LocationViewHolder holder, int position) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<ImageData> images = db.getAllImages();
        double longitude = images.get(position).getGeoPoint().getLongitude();
        double latitude = images.get(position).getGeoPoint().getLatitude();
        String roundedLongitude = "Longitude: " + MathUtil.round(longitude, 5);
        String roundedLatitude = "Latitude: " + MathUtil.round(latitude, 5);

        holder.uriLabel.setText(images.get(position).getImageURI());
        holder.lonLabel.setText(roundedLongitude);
        holder.latLabel.setText(roundedLatitude);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm");
        holder.dateLabel.setText(simpleDateFormat.format(images.get(position).getCreationTimeStamp()));
        String camString = "Shot on an " + images.get(position).getCameraManufacturer() + " device";
        holder.camLabel.setText(camString);
        holder.deleteButton.setOnClickListener(v -> {
            try {
                db.removeImageById(images.get(position).getId());
                notifyDataSetChanged();
            } catch (DatabaseException e) {
                Toast.makeText(ctx,"Cannot delete Image with id " + images.get(position).getId().toString(), Toast.LENGTH_LONG).show();
            }
        });
        try {
            holder.imageView.setImageBitmap(BitmapFactory.decodeStream(createMapBoxUrl(latitude, longitude).openConnection().getInputStream()));
        } catch (IOException e) {
            holder.imageView.setImageResource(R.drawable.pin);
            Toast.makeText(ctx, "Could not get map image for location", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return db.getAllImages().size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView uriLabel, latLabel, lonLabel, dateLabel, camLabel;
        ImageButton deleteButton;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            uriLabel = itemView.findViewById(R.id.uri_label);
            latLabel = itemView.findViewById(R.id.lat_label);
            lonLabel = itemView.findViewById(R.id.lon_label);
            dateLabel = itemView.findViewById(R.id.date_label);
            camLabel = itemView.findViewById(R.id.cam_label);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }

    private URL createMapBoxUrl(double lat, double lon) throws MalformedURLException {
        String apiToken = ctx.getString(R.string.MAPBOX_API_TOKEN);
        String url = "https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/pin-l+40681C(" + lon + "," + lat + ")/" + lon + "," + lat + ",17/400x400?access_token=" + apiToken;
        return new URL(url);
    }
}
