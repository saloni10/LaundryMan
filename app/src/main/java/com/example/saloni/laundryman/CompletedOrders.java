package com.example.saloni.laundryman;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompletedOrders extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /*
       initial declarations
   */

    public Completed_Adapter adap;
    ArrayList<OrderItems> completedorderlist = new ArrayList<>();
    private ValueEventListener morderListener;
    private DatabaseReference morder;
    private RecyclerView View_Recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Firebase.setAndroidContext(this);

          /*
             Inflating Recycler View
             */
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isConnected()) {
            Toast.makeText(this,"No Internet Connection Available",Toast.LENGTH_LONG).show();
        }
        View_Recycler = (RecyclerView) findViewById(R.id.rec_list_completed);
        View_Recycler.setLayoutManager(new LinearLayoutManager(this));

         /*
             Setting FireBase references and Listeners on DataChange
         */
        morder = FirebaseDatabase.getInstance().getReference().child("Orders");
        ValueEventListener orderListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                // User user = dataSnapshot.getValue(User.class);
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    OrderItems order = dsp.getValue(OrderItems.class);

                    if (!order.getWhenConfirmed().equals("#1") && !order.getWhenCompleted().equals("#2")) {
                        completedorderlist.add(order);
                    }

                }
                if (completedorderlist.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No Pending Orders To Complete", Toast.LENGTH_SHORT).show();
                } else {
                    func(completedorderlist);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //  Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        morder.addListenerForSingleValueEvent(orderListener);
        morderListener = orderListener;


    }

    /*
       @Params Arraylist of Class OrderItems
       Sets the Adapter for Recycler View
    */
    public void func(ArrayList<OrderItems> orderlist) {

        adap = new Completed_Adapter(orderlist, this);
        View_Recycler.setAdapter(adap);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.completed_orders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.new_orders) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.ongoing_orders) {
            Intent i = new Intent(this, OngoingOrders.class);
            startActivity(i);

        } else if (id == R.id.delieverd_orders) {
            Intent i = new Intent(this, CompletedOrders.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
