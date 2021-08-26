package edu.gcit.tshogyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UpdateCandidatesActivity extends AppCompatActivity {
    List<Candidates> candidatesList;
    RecyclerView mRecyclerView;
    //layout manager for recycleview
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton mAddBtn;

    //firestore instance
    FirebaseFirestore db;

    UpdateAdapter adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_candidates);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TSHOGYEN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);//for diff orientation

        //initialize fireStore
        db = FirebaseFirestore.getInstance();

        //initialize progress Dialog
        pd = new ProgressDialog(this);

        //initialize views
        mRecyclerView = findViewById(R.id.updateCandidatesrecyclerView);
        mAddBtn = findViewById(R.id.addBtn);
        //set recycler view properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        candidatesList = new ArrayList<>();

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterCandidatesActivity.class));
            }
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));//for diff orientation

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

        for (Candidates item : candidatesList) {
            if (item.getCandidateRole().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.ffilterList(filteredList);
    }

    private void showBoyPrayerCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        db.collection("Boy Prayer Coordinator")
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
                            candidatesList.add(candidates);
                        }
                        //adapter
                        adapter = new UpdateAdapter(UpdateCandidatesActivity.this, candidatesList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(UpdateCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showGirlPrayerCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        db.collection("Girl Prayer Coordinator")
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
                            candidatesList.add(candidates);
                        }
                        //adapter
                        adapter = new UpdateAdapter(UpdateCandidatesActivity.this, candidatesList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(UpdateCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showBoyCulturalCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        db.collection("Boy Cultural Coordinator")
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
                            candidatesList.add(candidates);
                        }
                        //adapter
                        adapter = new UpdateAdapter(UpdateCandidatesActivity.this, candidatesList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(UpdateCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showGirlCulturalCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        db.collection("Girl Cultural Coordinator")
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
                            candidatesList.add(candidates);
                        }
                        //adapter
                        adapter = new UpdateAdapter(UpdateCandidatesActivity.this, candidatesList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(UpdateCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showBoyGamesandSportsCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        db.collection("Boy Games & Sports Coordinator")
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
                            candidatesList.add(candidates);
                        }
                        //adapter
                        adapter = new UpdateAdapter(UpdateCandidatesActivity.this, candidatesList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(UpdateCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showGirlGamesandSportsCoordinator() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        db.collection("Girl Games & Sports Coordinator")
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
                            candidatesList.add(candidates);
                        }
                        //adapter
                        adapter = new UpdateAdapter(UpdateCandidatesActivity.this, candidatesList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(UpdateCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showGirlResidentCouncillor() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        db.collection("Girl Resident Councillor")
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
                            candidatesList.add(candidates);
                        }
                        //adapter
                        adapter = new UpdateAdapter(UpdateCandidatesActivity.this, candidatesList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(UpdateCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showBoyResidentCouncillor() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        db.collection("Boy Resident Councillor")
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
                            candidatesList.add(candidates);
                        }
                        //adapter
                        adapter = new UpdateAdapter(UpdateCandidatesActivity.this, candidatesList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(UpdateCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showChiefCouncillor() {
        //set title of progress dialog
        pd.setTitle("Loading Candidates...");
        //show progress dialog
        pd.show();

        db.collection("Chief Councillor")
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
                            candidatesList.add(candidates);
                        }
                        //adapter
                        adapter = new UpdateAdapter(UpdateCandidatesActivity.this, candidatesList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error while retrieving
                        pd.dismiss();
                        Toast.makeText(UpdateCandidatesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteChiefCouncillor(int position) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Delete Candidate");
        alertdialog.setMessage("Are You Sure You Want To Delete ?");

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setTitle("Deleting Candidate...");
                pd.show();

                Candidates candidates = candidatesList.get(position);

                db.collection("Chief Councillor").document(candidates.getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Candidate Deleted !!", Toast.LENGTH_SHORT).show();
                                    //update candidate
                                    candidatesList.remove(position);
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.show();
    }

    public void deleteGirlResidentCouncillor(int position){
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Delete Candidate");
        alertdialog.setMessage("Are You Sure You Want To Delete ?");

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setTitle("Deleting Candidate...");
                pd.show();

                Candidates candidates = candidatesList.get(position);

                db.collection("Girl Resident Councillor").document(candidates.getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Candidate Deleted !!", Toast.LENGTH_SHORT).show();
                                    //update candidate
                                    candidatesList.remove(position);
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.show();
    }

    public void deleteBoyResidentCouncillor(int position) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Delete Candidate");
        alertdialog.setMessage("Are You Sure You Want To Delete ?");

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setTitle("Deleting Candidate...");
                pd.show();

                Candidates candidates = candidatesList.get(position);

                db.collection("Boy Resident Councillor").document(candidates.getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Candidate Deleted !!", Toast.LENGTH_SHORT).show();
                                    //update candidate
                                    candidatesList.remove(position);
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.show();
    }

    public void deleteBoyCulturalCoordinator(int position) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Delete Candidate");
        alertdialog.setMessage("Are You Sure You Want To Delete ?");

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setTitle("Deleting Candidate...");
                pd.show();

                Candidates candidates = candidatesList.get(position);

                db.collection("Boy Cultural Coordinator").document(candidates.getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Candidate Deleted !!", Toast.LENGTH_SHORT).show();
                                    //update candidate
                                    candidatesList.remove(position);
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.show();
    }

    public void deleteGirlCulturalCoordinator(int position) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Delete Candidate");
        alertdialog.setMessage("Are You Sure You Want To Delete ?");

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setTitle("Deleting Candidate...");
                pd.show();

                Candidates candidates = candidatesList.get(position);

                db.collection("Girl Cultural Coordinator").document(candidates.getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Candidate Deleted !!", Toast.LENGTH_SHORT).show();
                                    //update candidate
                                    candidatesList.remove(position);
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.show();
    }

    public void deleteBoyGamesandSportsCoordinator(int position) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Delete Candidate");
        alertdialog.setMessage("Are You Sure You Want To Delete ?");

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setTitle("Deleting Candidate...");
                pd.show();

                Candidates candidates = candidatesList.get(position);

                db.collection("Boy Games & Sports Coordinator").document(candidates.getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Candidate Deleted !!", Toast.LENGTH_SHORT).show();
                                    //update candidate
                                    candidatesList.remove(position);
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.show();

    }

    public void deleteGirlGamesandSportsCoordinator(int position) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Delete Candidate");
        alertdialog.setMessage("Are You Sure You Want To Delete ?");

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setTitle("Deleting Candidate...");
                pd.show();

                Candidates candidates = candidatesList.get(position);

                db.collection("Girl Games & Sports Coordinator").document(candidates.getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Candidate Deleted !!", Toast.LENGTH_SHORT).show();
                                    //update candidate
                                    candidatesList.remove(position);
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.show();

    }

    public void deleteBoyPrayerCoordinator(int position) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Delete Candidate");
        alertdialog.setMessage("Are You Sure You Want To Delete ?");

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setTitle("Deleting Candidate...");
                pd.show();

                Candidates candidates = candidatesList.get(position);

                db.collection("Boy Prayer Coordinator").document(candidates.getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Candidate Deleted !!", Toast.LENGTH_SHORT).show();
                                    //update candidate
                                    candidatesList.remove(position);
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.show();
    }

    public void deleteGirlPrayerCoordinator(int position) {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Delete Candidate");
        alertdialog.setMessage("Are You Sure You Want To Delete ?");

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setTitle("Deleting Candidate...");
                pd.show();

                Candidates candidates = candidatesList.get(position);

                db.collection("Girl Prayer Coordinator").document(candidates.getId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Candidate Deleted !!", Toast.LENGTH_SHORT).show();
                                    //update candidate
                                    candidatesList.remove(position);
                                    finish();
                                    startActivity(getIntent());
                                }else{
                                    pd.dismiss();
                                    Toast.makeText(adapter.updateCandidatesActivity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.show();
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