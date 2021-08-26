package com.example.lokhorchunye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TableViewAdapter extends RecyclerView.Adapter<TableViewAdapter.ViewHolder> {
    Context context;
    List<sign> signList;

    public TableViewAdapter(Context context, List<sign> signList) {
        this.context = context;
        this.signList = signList;
    }

    @NonNull
    @Override
    public TableViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewAdapter.ViewHolder holder, int position) {
        if(signList != null && signList.size() > 0){
            sign model = signList.get(position);
            holder.sign.setText(model.getSign());
            holder.laza.setText(model.getLaza());
            holder.sogza.setText(model.getSogza());
            holder.sheza.setText(model.getSheza());
        }else{
            return;
        }

    }

    @Override
    public int getItemCount() {
        return signList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sign, laza,sogza,sheza;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sign = itemView.findViewById(R.id.msign);
            laza = itemView.findViewById(R.id.mlaza);
            sogza = itemView.findViewById(R.id.msogza);
            sheza = itemView.findViewById(R.id.msheza);
        }
    }
}
