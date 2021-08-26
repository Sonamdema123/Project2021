package edu.gcit.tshogyen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.CandidateViewHolder> {
    List<Candidates> candidatesList;
    Context mContext;

    // FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ResultListAdapter(Context context, List<Candidates> candidateslist) {
        this.candidatesList = candidateslist;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ResultListAdapter.CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.result_list, parent, false);
        return new ResultListAdapter.CandidateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultListAdapter.CandidateViewHolder holder, int position) {

        //Get the current sport
        Candidates currentCandidate = candidatesList.get(position);

        //Bind the data to the views
        holder.bindTo(currentCandidate);
        Picasso.get().load(currentCandidate.getImage())
                .fit()
                .centerCrop()
                .into(holder.image);

        ////

    }

    @Override
    public int getItemCount() {
        return candidatesList.size();
    }

    class CandidateViewHolder extends RecyclerView.ViewHolder {
        TextView t1, t3, t4, votecount;
        ImageView image;
        Candidates CurrentCandidate;
        public CandidateViewHolder(View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.t1);
            t3 = itemView.findViewById(R.id.t3);
            t4 = itemView.findViewById(R.id.t4);
            votecount = itemView.findViewById(R.id.voteCount);
            image = itemView.findViewById(R.id.image_resultView);

            //Set the OnClickListener to the whole view
//            itemView.setOnClickListener(this);
        }

        void bindTo(Candidates currentCandidate){
            //Populate the textviews with data
            t1.setText(currentCandidate.getFullName());
            t3.setText(currentCandidate.getCandidateID());
            t4.setText(currentCandidate.getCandidateRole());
            votecount.setText(String.valueOf(currentCandidate.getCandidateVote()));

            //Get the current sport
            CurrentCandidate = currentCandidate;

        }
    }
}
