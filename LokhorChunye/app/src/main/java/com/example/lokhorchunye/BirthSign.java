package com.example.lokhorchunye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class BirthSign extends AppCompatActivity {
    RecyclerView recyclerView;
    TableViewAdapter adapter;
//    private Toolbar toolbar;
//    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_sign);
//
//        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);

        setRecyclearView();
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Birth Sign");
//        toolbar.setTitleTextColor(Color.WHITE);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void setRecyclearView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TableViewAdapter(this,getList());
        recyclerView.setAdapter(adapter);
    }

    private List<sign> getList(){
        List <sign> signList = new ArrayList<>();

        signList.add(new sign("Rat(Jewa)", "Thusday", "Monday", "Friday"));
        signList.add(new sign("Ox(Lung)", "Friday", "Tuesday", "Wednesday"));
        signList.add(new sign("Tiger(Tag)", "Wednesday", "Friday", "Thurday"));
        signList.add(new sign("Rabbit(Yeo)", "Wednesday", "Friday", "Thurday"));
        signList.add(new sign("Dragon(Druk)", "Saturday", "Thusday", "Wednesday"));
        signList.add(new sign("Snake(Drue)", "Monday", "Thurday", "Thusday"));
        signList.add(new sign("Horse(Ta)", "Monday", "Thurday", "Thusday"));
        signList.add(new sign("Sheep(Lu)", "Thurday", "Sunday", "Wednesday"));
        signList.add(new sign("Monkey(Tray)", "Thurday", "Wednesday", "Monday"));
        signList.add(new sign("Rooster(Jaa)", "Thurday", "Wednesday", "Wednesday"));
        signList.add(new sign("Dog(Khey)", "sunday", "Thusday", "Wednesday"));
        signList.add(new sign("Pig(Phaa)", "Tuesday", "Monday", "Friday"));
        return signList;
    }
    public void ArrowLeft(View view) {
        Intent arrowLeft = new Intent(this, Home.class);
        startActivity(arrowLeft);
    }
}