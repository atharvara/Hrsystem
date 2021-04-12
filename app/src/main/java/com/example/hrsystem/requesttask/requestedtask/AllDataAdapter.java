package com.example.hrsystem.requesttask.requestedtask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hrsystem.R;
import com.example.hrsystem.requesttask.taskModel;

import java.util.List;

public class AllDataAdapter  extends RecyclerView.Adapter<AllDataAdapter.ViewHolder> {
    private Context context;
    private List<AllDataModel> list;
    public AllDataAdapter(Context context, List<AllDataModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.all_data_request, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AllDataModel Task=list.get(i);
        viewHolder.tv1.setText("Task:-"+Task.getTask());
        viewHolder.tv2.setText("Due Date:-"+Task.getDueDate());
        viewHolder.tv3.setText(Task.getSts());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv1, tv2,tv3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.taskE);
            tv2=itemView.findViewById(R.id.dueDate);
            tv3=itemView.findViewById(R.id.status);
        }
    }
}
