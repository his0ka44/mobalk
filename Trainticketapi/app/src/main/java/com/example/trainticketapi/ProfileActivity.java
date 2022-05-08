package com.example.trainticketapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private static final String LOG_TAG= ProfileActivity.class.getName();
    private FirebaseUser user;
    private TrainItemAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"Welcome to your profile!"+user,Toast.LENGTH_LONG).show();
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