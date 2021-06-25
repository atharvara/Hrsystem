package com.example.hrsystem.requesttask;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hrsystem.R;

import java.util.List;

public class assignedAdapter extends RecyclerView.Adapter<assignedAdapter.ViewHolder>{
private Context context;
private List<assignedModel> list;
public assignedAdapter(Context context, List<assignedModel> list) {
        this.context = context;
        this.list = list;
        }
    @NonNull
    @Override
    public assignedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.assigned_task, viewGroup, false);
        return new assignedAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull assignedAdapter.ViewHolder viewHolder, int i) {
        assignedModel Task=list.get(i);
        viewHolder.tv1.setText("Task:-"+Task.getTask());
        viewHolder.tv2.setText("Employees:-"+Task.getEmpid());
        viewHolder.tv3.setText(Task.getDuedate());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task=viewHolder.tv1.getText().toString();
                String due=viewHolder.tv3.getText().toString();
                task=task.substring(6);
                Intent intent= new Intent(context, assignedTaskReport.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("task",task);
                intent.putExtra("due",due);

                context.startActivity(intent);
            }
        });
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
            tv2=itemView.findViewById(R.id.empid);
            tv3=itemView.findViewById(R.id.dueDate);
        }
    }
}
