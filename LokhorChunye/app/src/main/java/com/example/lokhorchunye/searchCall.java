package com.example.lokhorchunye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class searchCall extends AppCompatActivity {
    String title;
    String id;
    int ID;
    TextView t,a,b,c,d,e,f,g,h;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Mewa");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_call);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        t = findViewById(R.id.t);
        a = findViewById(R.id.a);
        b = findViewById(R.id.b);
        c = findViewById(R.id.c);
        d = findViewById(R.id.d);
        e = findViewById(R.id.e);
        f = findViewById(R.id.f);
        g = findViewById(R.id.g);
        h = findViewById(R.id.h);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    //MewaModel mewaModel = snapshot1.getValue(MewaModel.class);
                    Log.d("yes",snapshot1.getKey());
                    //Log.d("yes",snapshot1.child(snapshot1.getKey()).child(id).getValue(String.class));
                    //if(snapshot1.child(snapshot1.getKey()).child(id).getValue(String.class).equals(id)){
                        /*Log.d("yes",String.valueOf(ID));
                        Log.d("yes",snapshot1.child("About").getValue(String.class));

                        t.setText(snapshot1.child("Title").getValue(String.class));
                        a.setText(snapshot1.child("About").getValue(String.class));
                        b.setText(snapshot1.child("Love").getValue(String.class));
                        c.setText(snapshot1.child("Health").getValue(String.class));
                        d.setText(snapshot1.child("Fortune").getValue(String.class));
                        e.setText(snapshot1.child("Lives").getValue(String.class));
                        f.setText(snapshot1.child("Occupation").getValue(String.class));
                        g.setText(snapshot1.child("Mantra").getValue(String.class));
                        h.setText(snapshot1.child("Purification").getValue(String.class));*/
                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
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
//            startActivity(new Intent(getApplicationContext(), Home.class));
//        }
        if(item.getItemId() == R.id.about){
            startActivity(new Intent(getApplicationContext(), Home.class));
        }
        return super.onOptionsItemSelected(item);
    }

}