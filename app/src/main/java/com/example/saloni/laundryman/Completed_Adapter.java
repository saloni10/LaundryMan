package com.example.saloni.laundryman;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by saloni on 28/11/16.
 */

/**
 * Adapter For Ongoing Activity
 * Holds the view for Ready/Completed Orders
 * Binds the view
 * Displays the ready orders
 */

public class Completed_Adapter extends RecyclerView.Adapter<Completed_Adapter.ListHolder> {


    SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
    Date todayDate = new Date();
    final String thisDate = currentDate.format(todayDate);
    Context c;
    private DatabaseReference msingleorder;
    private DatabaseReference muser;
    private List<OrderItems> listdata;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;

    public Completed_Adapter(List<OrderItems> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
        this.c = c;

    }

    public void setItemClickCallback(final ItemClickCallback itemclickcallback) {
        this.itemclickcallback = itemclickcallback;

    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.completed_orders, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

        OrderItems item = listdata.get(position);
        String uid = item.getUserId();

        holder.topQty.setText(String.valueOf(item.getShirts()));
        holder.lowerQty.setText(String.valueOf(item.getLowers()));
        holder.bedsheetQty.setText(String.valueOf(item.getBedsheets()));
        holder.otherQty.setText(String.valueOf(item.getOthers()));
        holder.totalQ.setText(String.valueOf(item.getTotalQTY()));
        holder.totalP.setText(String.valueOf(item.getTotalPrice()));
        holder.whenPlaced.setText(item.getWhenPlaced());
        holder.whenDelivered.setText(item.getWhenCompleted());
        // holder.t9.setText(item.getPickupTime());
        holder.username.setText(item.getUsername());
        holder.room.setText(item.getRoom());
        holder.hostel.setText(item.getHostel());
        holder.typeoforder.setText(item.getTypeOfOrders());

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    /*
      * Holder Class to hold itemview of Recycler View
     */
    public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView topQty;
        private TextView lowerQty;
        private TextView bedsheetQty;
        private TextView otherQty;
        private TextView totalQ;
        private TextView totalP;
        private TextView whenPlaced;
        private TextView whenDelivered;
        private TextView t9;
        private TextView username;
        private TextView room;
        private TextView hostel;
        private TextView typeoforder;
        private View container;

        public ListHolder(View iView) {
            super(iView);
            topQty = (TextView) iView.findViewById(R.id.topQty);
            lowerQty = (TextView) iView.findViewById(R.id.lowerQty);
            bedsheetQty = (TextView) iView.findViewById(R.id.bedsheetQty);
            otherQty = (TextView) iView.findViewById(R.id.otherQty);
            totalQ = (TextView) iView.findViewById(R.id.totalQ);
            totalP = (TextView) iView.findViewById(R.id.totalP);
            whenPlaced = (TextView) iView.findViewById(R.id.whenPlaced);
            whenDelivered = (TextView) iView.findViewById(R.id.whenDelivered);
            username = (TextView) iView.findViewById(R.id.username);
            room = (TextView) iView.findViewById(R.id.room);
            hostel = (TextView) iView.findViewById(R.id.hostel);
            typeoforder = (TextView) iView.findViewById(R.id.typeoforder);
            container = iView.findViewById(R.id.container_item_completed);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {


        }
    }
}
