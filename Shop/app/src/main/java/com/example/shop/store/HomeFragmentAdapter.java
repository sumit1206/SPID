package com.example.shop.store;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;

import java.util.List;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.MyViewHolder> {

private Context hContext;
private List<HomeFragmentdata>homeFragmentdataArray;

public HomeFragmentAdapter(Context hContext,List<HomeFragmentdata>homeFragmentdataArray)
{
    this.hContext = hContext;
    this.homeFragmentdataArray = homeFragmentdataArray;
}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragent_home_card_custom, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        final HomeFragmentdata dataWheeler = homeFragmentdataArray.get(position);
        holder.from_station_wheeler.setText(dataWheeler.getFrom_station());
        holder.to_station_wheeler.setText(dataWheeler.getTo_station());
        holder.departure_date_wheeler.setText(dataWheeler.getDeparture_date().substring(0,10));
        holder.departure_time_wheeler.setText(dataWheeler.getDeparture_time());
        holder.arrival_date_wheeler.setText(dataWheeler.getArrival_date().substring(0,10));
        holder.arrival_time_wheeler.setText(dataWheeler.getArrival_time());
//        holder.train_no_wheeler.setText(dataWheeler.getTrain_no());
        holder.parcel_id_wheeler.setText(dataWheeler.getParcel_id());
        switch (Integer.parseInt(dataWheeler.getStatus())){
            case 0:
                holder.status_wheeler.setText("With sender");
                break;
            case 1:
                if(new StoreUser(hContext).getStnName().equalsIgnoreCase(dataWheeler.getFrom_station()))
                    holder.status_wheeler.setText("With You");
                else
                    holder.status_wheeler.setText("In "+dataWheeler.getFrom_station()+" station");
                break;
            case 2:
                holder.status_wheeler.setText("In Train");
                break;
            case 3:
                if(new StoreUser(hContext).getStnName().equalsIgnoreCase(dataWheeler.getTo_station()))
                    holder.status_wheeler.setText("With You");
                else
                    holder.status_wheeler.setText("In "+dataWheeler.getTo_station()+" station");
                break;
            case 4:
                holder.status_wheeler.setText("Delivered");
                break;
                default:
                    holder.status_wheeler.setText("Cancelled");
        }


    }

    @Override
    public int getItemCount() {
        return homeFragmentdataArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
{
    TextView from_station_wheeler, to_station_wheeler, departure_date_wheeler, departure_time_wheeler, arrival_date_wheeler, arrival_time_wheeler, train_no_wheeler, parcel_id_wheeler,status_wheeler;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        from_station_wheeler = itemView.findViewById(R.id.from_station_wheeler);
        to_station_wheeler = itemView.findViewById(R.id.to_station_carrier);
        departure_date_wheeler = itemView.findViewById(R.id.travel_date_wheeler);
        departure_time_wheeler = itemView.findViewById(R.id.departure_time_wheeler);
        arrival_date_wheeler = itemView.findViewById(R.id.arrival_date_wheeler);
        arrival_time_wheeler = itemView.findViewById(R.id.arrival_time_wheeler);
//        train_no_wheeler = itemView.findViewById(R.id.train_no_wheeler);
        parcel_id_wheeler = itemView.findViewById(R.id.parcel_id);
        status_wheeler = itemView.findViewById(R.id.status);
    }
}
}
