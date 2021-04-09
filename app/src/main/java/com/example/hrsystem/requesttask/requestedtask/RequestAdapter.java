package com.example.hrsystem.requesttask.requestedtask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrsystem.R;
import com.example.hrsystem.requesttask.taskAdapter;
import com.example.hrsystem.requesttask.taskModel;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private Context context;
    private List<RequestModel> list;
    public RequestAdapter(Context context, List<RequestModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_request, viewGroup, false);
        return new RequestAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RequestModel requestModel=list.get(i);
        viewHolder.tv1.setText("Task:-"+requestModel.getTask());
        viewHolder.tv2.setText("Due Date:-"+requestModel.getDueDate());
        viewHolder.tv3.setText(requestModel.getSts());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task=viewHolder.tv1.getText().toString();
                String due=viewHolder.tv2.getText().toString();
                due=due.substring(10);
                task=task.substring(6);
                Intent intent= new Intent(context,RequestPage.class);
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
            tv2=itemView.findViewById(R.id.dueDate);
            tv3=itemView.findViewById(R.id.status);
        }
    }
}
