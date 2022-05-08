package com.example.trainticketapi;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final String LOG_TAG= HomeActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private ArrayList<TrainItem> mTrainList;
    private TrainItemAdapter mAdapter;

    private int gridNumber = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth= FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
                Log.d(LOG_TAG, "Authenticated user!");

        }
        else {
            Log.d(LOG_TAG, "Unauthenticated user!");
           finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,gridNumber));
        mTrainList = new ArrayList<>();

        mAdapter= new TrainItemAdapter(this, mTrainList);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();
    }

    private void initializeData(){
        String[] trainName = getResources().getStringArray(R.array.train_names);
        String[] trainRoute = getResources().getStringArray(R.array.train_routes);
        String[] trainDate = getResources().getStringArray(R.array.departure_dates);
        String[] trainPrice = getResources().getStringArray(R.array.ticket_prizes);
        TypedArray trainsImageResource = getResources().obtainTypedArray(R.array.train_image);


        mTrainList.clear();

        for(int i = 0 ;i < trainName.length; i++){
            mTrainList.add(new TrainItem(trainName[i],trainRoute[i],trainDate[i],trainPrice[i],trainsImageResource.getResourceId(i,0)));

        }

        trainsImageResource.recycle();
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.train_list_menu,menu);
        MenuItem menuItem =  menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG,s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.log_out:
                Log.d(LOG_TAG,"Log out clicked!");
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.profile:
                Log.d(LOG_TAG,"Profile clicked!");
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        return super.onPrepareOptionsMenu(menu);
    }
}