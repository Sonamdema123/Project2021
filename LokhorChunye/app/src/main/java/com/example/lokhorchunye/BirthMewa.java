package com.example.lokhorchunye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SearchView;
//import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BirthMewa extends AppCompatActivity {
    DatabaseReference ref;
    RecyclerView mRecyclerView;
    SearchView searchView;
    ArrayList<String> location;
    int count;
    private List<String> titles;
    private List<Integer> mImages;
    private MyAdapter adapter;
    private Toolbar toolbar;
   // private EditText inputsearch;
   // DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Mewa");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_mewa);
        mRecyclerView = findViewById(R.id.datalist);

        int gridColumnCount =  getResources().getInteger(R.integer.grid_column_count);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref = FirebaseDatabase.getInstance().getReference("Mewa");


        //inputsearch = findViewById(R.id.inputsearch);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Birth Mewa");

       // getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //actionBar.setDisplayUseLogoEnabled(true);


        titles = new ArrayList<>();
        mImages = new ArrayList<>();
        adapter = new MyAdapter(this, titles, mImages);

        mImages.add(R.drawable.m1);
        mImages.add(R.drawable.m2);
        mImages.add(R.drawable.m3);
        mImages.add(R.drawable.m4);
        mImages.add(R.drawable.m5);
        mImages.add(R.drawable.m6);
        mImages.add(R.drawable.m7);
        mImages.add(R.drawable.m8);
        mImages.add(R.drawable.m9);

        titles.add("Chikar");
        titles.add("Ninah");
        titles.add("Sumdhing");
        titles.add("Zhijang");
        titles.add("Ngaser");
        titles.add("Drukar");
        titles.add("Dunmar");
        titles.add("Gaykar");
        titles.add("Gumar");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,gridColumnCount));
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
//        if(item.getItemId() == R.id.help){
//            startActivity(new Intent(getApplicationContext(), Home.class));
//        }
//        if(item.getItemId() == R.id.search){
//            startActivity(new Intent(getApplicationContext(), search.class));
//        }
        if(item.getItemId() == R.id.about){
            startActivity(new Intent(getApplicationContext(), about.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void MewaArrowLeft(View view) {
        Intent arrowLeft = new Intent(this, Home.class);
        startActivity(arrowLeft);
    }

    public void search(View view) {

        SearchView searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processsearch(newText);
                return false;
            }
        });
    }

    private void processsearch(String selection) {
        int year = Integer.parseInt(selection);
        count = 9;
        for(int i=2000;i<=year;i++){
            if(count==0) count=9;
            count-=1;
        }

    }

    public void call(View view) {
        Intent intent = new Intent(BirthMewa.this,searchCall.class);
        intent.putExtra("id",String.valueOf(count+1));
        startActivity(intent);
    }
}