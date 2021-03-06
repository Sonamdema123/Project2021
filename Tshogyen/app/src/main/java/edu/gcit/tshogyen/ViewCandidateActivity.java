package edu.gcit.tshogyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ViewCandidateActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<Candidates> candidateList;
    FirebaseFirestore fStore;
    ProgressDialog pd;
    CandidateListAdapter mAdapter;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ImageView imageView;
    EditText candidateRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_candidate);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        pd = new ProgressDialog(this);

        candidateRole = findViewById(R.id.candidate_role);

        imageView = findViewById(R.id.image_recyclerView_id);

        String id = UUID.randomUUID().toString();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference("Candidate_Images");

        mRecyclerView = findViewById(R.id.candidatesrecyclerView);
        //Set the Layout Manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Initialize the ArrayList that will contain the data
        candidateList = new ArrayList<>();
        //Initialize the adapter and set it ot the RecyclerView
        mAdapter = new CandidateListAdapter(this,candidateList);
        mRecyclerView.setAdapter(mAdapter);

        //for the different orientation and device sizes
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        fStore = FirebaseFirestore.getInstance();
//        collectionReference = fStore.collection("Candidates");

        //show data in recyclerview
        showChiefCouncillor();
        showBoyResidentCouncillor();
        showGirlResidentCouncillor();
        showBoyGamesandSportsCoordinator();
        showGirlGamesandSportsCoordinator();
        showBoyCulturalCoordinator();
        showGirlCulturalCoordinator();
        showBoyPrayerCoordinator();
        showGirlPrayerCoordinator();

        //autocomplete search
        String[] councillor = getResources().getStringArray(R.array.councilor);

        AutoCompleteTextView editText = findViewById(R.id.edittext);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,councillor );
        editText.setAdapter(adapter);

        //serch
//        editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }
    //search
    private void filter(String text) {
        ArrayList<Candidates> filteredList = new ArrayList<>();

        for (Candidates item : candidateList) {
            if (item.getCandidateRole().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);

    }

    private void showGirlCulturalCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        fStore.collection("Girl Cultural Coordinator")
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
                        mAdapter = new CandidateListAdapter(getApplicationContext(), candidateList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(ViewCandidateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showGirlGamesandSportsCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        fStore.collection("Girl Games & Sports Coordinator")
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
                        mAdapter = new CandidateListAdapter(getApplicationContext(), candidateList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(ViewCandidateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showGirlPrayerCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        fStore.collection("Girl Prayer Coordinator")
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
                        mAdapter = new CandidateListAdapter(getApplicationContext(), candidateList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(ViewCandidateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showBoyPrayerCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        fStore.collection("Boy Prayer Coordinator")
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
                        mAdapter = new CandidateListAdapter(getApplicationContext(), candidateList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(ViewCandidateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
                        mAdapter = new CandidateListAdapter(getApplicationContext(), candidateList);
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

    private void showBoyGamesandSportsCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        fStore.collection("Boy Games & Sports Coordinator")
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
                        mAdapter = new CandidateListAdapter(getApplicationContext(), candidateList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(mAdapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(ViewCandidateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showGirlResidentCouncillor() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        fStore.collection("Girl Resident Councillor")
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
                        mAdapter = new CandidateListAdapter(getApplicationContext(), candidateList);
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

    private void showBoyResidentCouncillor() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        fStore.collection("Boy Resident Councillor")
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
                        mAdapter = new CandidateListAdapter(getApplicationContext(), candidateList);
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

    private void showChiefCouncillor() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        fStore.collection("Chief Councillor")
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
                        mAdapter = new CandidateListAdapter(getApplicationContext(), candidateList);
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
        if(item.getItemId() == R.id.resetPassword){
            startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
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