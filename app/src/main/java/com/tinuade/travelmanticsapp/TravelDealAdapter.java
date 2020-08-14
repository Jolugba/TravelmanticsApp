package com.tinuade.travelmanticsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class TravelDealAdapter extends RecyclerView.Adapter<TravelDealAdapter.TravelDealsViewHolder> {

    private DatabaseReference databaseReference;
    private ArrayList<TravelDeal> deals;
    private ChildEventListener childEventListener;

    public TravelDealAdapter(){

        FirebaseUtils.openDatabaseReference("Traveldeals");
        databaseReference = FirebaseUtils.databaseReference;
        deals=FirebaseUtils.travelDeals;

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                TravelDeal travelDeal = snapshot.getValue(TravelDeal.class);
                assert travelDeal != null;
                Log.d("TravelDealAdapter",travelDeal.getTitle());
                travelDeal.setId(snapshot.getKey());
                deals.add(travelDeal);
                notifyItemInserted(deals.size()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }


    public class TravelDealsViewHolder extends RecyclerView.ViewHolder {
        TextView travelDealTitle;
        public TravelDealsViewHolder(@NonNull View itemView) {
            super(itemView);
            travelDealTitle=itemView.findViewById(R.id.travelDealsTitle);
        }
        public void bind(TravelDeal deal){
            travelDealTitle.setText(deal.getTitle());
        }
    }

    @NonNull
    @Override
    public TravelDealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View itemView= LayoutInflater.from(context).inflate(R.layout.row_list,parent,false);
        return new TravelDealsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelDealsViewHolder holder, int position) {
            TravelDeal deal=deals.get(position);
            holder.bind(deal);

    }

    @Override
    public int getItemCount() {
        return deals.size();
    }


}
