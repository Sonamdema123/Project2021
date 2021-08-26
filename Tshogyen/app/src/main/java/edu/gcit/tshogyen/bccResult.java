package edu.gcit.tshogyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class bccResult extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<Candidates> candidateList;
    FirebaseFirestore fStore;
    ProgressDialog pd;
    ResultListAdapter mAdapter;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    ImageView imageView;
    EditText candidateRole;
    FloatingActionButton gotonext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcc_result);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        fAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        candidateRole = findViewById(R.id.candidate_role);

        imageView = findViewById(R.id.image_resultView);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("Candidate_Images");

        mRecyclerView = findViewById(R.id.resultrecyclerView);
        //Set the Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Initialize the ArrayList that will contain the data
        candidateList = new ArrayList<>();
        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new ResultListAdapter(this, candidateList);
        mRecyclerView.setAdapter(mAdapter);

        //for the different orientation and device sizes
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        fStore = FirebaseFirestore.getInstance();
//        collectionReference = fStore.collection("Candidates");

        gotonext = findViewById(R.id.gotoResult4);

        gotonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), gccResult.class));
                finish();
            }
        });

        //show data in recyclerview
        showBoyCulturalCoordinator();
    }

    private void showBoyCulturalCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        fStore.collection("Boy Cultural Coordinator")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        candidatesList.clear();
                        //called when data is retrieved
                        pd.dismiss();
                        //show candidate
                        for (DocumentSnapshot can: task.getResult()){
                            Candidates candidates = new Candidates(
                                    can.getString("id"),
                                    can.getString("FullName"),
                                    can.getString("CandidateEmail"),
                                    can.getString("CandidateID"),
                                    can.getString("CandidateRole"),
                                    can.getString("Uri"),
                                    can.getLong("Votes").intValue());
                            candidateList.add(candidates);
                        }
                        //adapter
                        mAdapter = new ResultListAdapter(getApplicationContext(), candidateList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        if(item.getItemId() == R.id.user_profile){
            startActivity(new Intent(getApplicationContext(), UserProfile.class));
        }
        if(item.getItemId() == R.id.about){
            startActivity(new Intent(getApplicationContext(), AboutPage.class));
        }
        return super.onOptionsItemSelected(item);
    }
}