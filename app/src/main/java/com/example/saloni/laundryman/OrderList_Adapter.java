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
 * Created by saloni on 27/11/16.
 */

/**
 * Adapter For OrderItems Activity
 * Holds the view for Order Items
 * Binds the view
 * Displays the new orders Received
 */

public class OrderList_Adapter extends RecyclerView.Adapter<OrderList_Adapter.ListHolder> {


    static public String orderid;
    SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
    Date todayDate = new Date();
    final String thisDate = currentDate.format(todayDate);
    Context c;
    private DatabaseReference msingleorder;
    private DatabaseReference muser;
    private List<OrderItems> listdata;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;

    public OrderList_Adapter(List<OrderItems> listdata, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listdata = listdata;
        Log.d("dekheinadapter", "" + listdata.get(0).getLowers());
        this.c = c;

    }

    public void setItemClickCallback(final ItemClickCallback itemclickcallback) {
        this.itemclickcallback = itemclickcallback;

    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_item, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

        OrderItems item = listdata.get(position);
        String uid = item.getUserId();
        orderid = item.getOrderID();
        holder.topQty.setText(String.valueOf(item.getShirts()));
        holder.lowerQty.setText(String.valueOf(item.getLowers()));
        holder.bedsheetQty.setText(String.valueOf(item.getBedsheets()));
        holder.otherQty.setText(String.valueOf(item.getOthers()));
        holder.totalQ.setText(String.valueOf(item.getTotalQTY()));
        holder.totalP.setText(String.valueOf(item.getTotalPrice()));
        holder.whenPlaced.setText(item.getWhenPlaced());
        holder.pickupdate.setText(item.getPickupDate());
        holder.pickuptime.setText(item.getPickupTime());
        holder.username.setText(item.getUsername());
        holder.room.setText(item.getRoom());
        holder.hostel.setText(item.getHostel());
        holder.typeoforder.setText(item.getTypeOfOrders());
        holder.orderid.setText(item.getOrderID());


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
    class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView topQty;
        private TextView lowerQty;
        private TextView bedsheetQty;
        private TextView otherQty;
        private TextView totalQ;
        private TextView totalP;
        private TextView whenPlaced;
        private TextView pickupdate;
        private TextView pickuptime;
        private TextView username;
        private TextView room;
        private TextView hostel;
        private TextView typeoforder;
        private TextView orderid;
        private Button confirmOrderBtn;
        private View container;

        public ListHolder(View iView) {
            super(iView);
            topQty = (TextView) iView.findViewById(R.id.topQty);
            lowerQty = (TextView) iView.findViewById(R.id.lowerQty);
            bedsheetQty= (TextView) iView.findViewById(R.id.bedsheetQty);
            otherQty = (TextView) iView.findViewById(R.id.otherQty);
            totalQ = (TextView) iView.findViewById(R.id.totalQ);
            totalP = (TextView) iView.findViewById(R.id.totalP);
            whenPlaced = (TextView) iView.findViewById(R.id.whenPlaced);
            pickupdate = (TextView) iView.findViewById(R.id.pickupdate);
            pickuptime = (TextView) iView.findViewById(R.id.pickuptime);
            username = (TextView) iView.findViewById(R.id.username);
            room = (TextView) iView.findViewById(R.id.room);
            hostel = (TextView) iView.findViewById(R.id.hostel);
            typeoforder = (TextView) iView.findViewById(R.id.typeoforder);
            orderid = (TextView) iView.findViewById(R.id.orderid);
            confirmOrderBtn = (Button) iView.findViewById(R.id.confirmOrder);
            confirmOrderBtn.setOnClickListener(this);
            container = iView.findViewById(R.id.container_item);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {


            if (view.getId() == confirmOrderBtn.getId()) {
                int pos = getAdapterPosition();
                String sorderid = listdata.get(pos).getOrderID();
                msingleorder = FirebaseDatabase.getInstance().getReference().child("Orders");
                msingleorder.child(sorderid).child("WhenConfirmed").setValue(thisDate);
                Toast.makeText(c, "Order Confirmed", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(c, MainActivity.class);
                c.startActivity(i);


            }


        }
    }


}
