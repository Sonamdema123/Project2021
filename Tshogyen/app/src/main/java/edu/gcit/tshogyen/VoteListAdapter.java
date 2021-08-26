package edu.gcit.tshogyen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class    VoteListAdapter extends RecyclerView.Adapter<VoteListAdapter.VoteViewHolder> {
    List<Candidates> voteList;
    LayoutInflater inflater;
    FirebaseFirestore fStore;
    DocumentReference documentReference;
    FieldValue increment = FieldValue.increment(1);
    int currentPos = -1;
    public boolean clicked = false;

    public VoteListAdapter(Context context, List<Candidates> votelist) {
        this.voteList = votelist;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vote = inflater.inflate(R.layout.vote_list, parent, false);
        VoteViewHolder viewHolder = new VoteViewHolder(vote);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder holder, int position) {

        //Get the current vote
        Candidates currentVote = voteList.get(position);
        holder.vote.setEnabled(position == currentPos || currentPos == -1);

        if (clicked) holder.vote.setEnabled(false);
        else holder.vote.setEnabled(true);

        fStore = FirebaseFirestore.getInstance();


        holder.vote.setOnClickListener(new View.OnClickListener() {
            @Nullable
            @Override
            public void onClick(View v) {
                clicked = true;
                notifyDataSetChanged();
                Log.d("TAG", "onClick: Voted for Candidate " + currentVote.FullName);
                documentReference = fStore.collection(currentVote.CandidateRole).document(currentVote.getId());
                documentReference.update("Votes", increment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("TAG", "onComplete: Vote Success");
                        Toast.makeText(inflater.getContext(), "Vote Successfully Counted.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: Error!" + e.toString());
                        Toast.makeText(inflater.getContext(), "Error !" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //Bind the data to the views
        holder.bindTo(currentVote);
        Picasso.get().load(currentVote.getImage())
                .fit()
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return voteList.size();
    }

    class VoteViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView t1;
        TextView t4;
        Button vote;
        Candidates CurrentVote;
        DocumentReference documentReference;
        public VoteViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t4 = itemView.findViewById(R.id.t4);
            image = itemView.findViewById(R.id.image_resultView);
            vote = itemView.findViewById(R.id.voteButton);

        }

        void bindTo(Candidates currentVote){
            //Populate the textviews with data
            t1.setText(currentVote.getFullName());
            t4.setText(currentVote.getCandidateID());

            //Get the current vote
            CurrentVote = currentVote;

        }
    }
}