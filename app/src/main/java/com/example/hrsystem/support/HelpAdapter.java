package com.example.hrsystem.support;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hrsystem.R;
import com.example.hrsystem.requesttask.requestedtask.RequestAdapter;
import com.example.hrsystem.requesttask.taskAdapter;
import com.example.hrsystem.requesttask.taskModel;
import com.example.hrsystem.support.adminhelp.DetailedPage;

import java.util.List;

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {
    private Context context;
    private List<HelpModel> list;
    public HelpAdapter(Context context, List<HelpModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public HelpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.help_emp_view, viewGroup, false);
        return new HelpAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpAdapter.ViewHolder viewHolder, int i) {
        HelpModel help=list.get(i);
        viewHolder.tv1.setText("Issue:-"+help.getIssue());
        viewHolder.tv2.setText("Employee Id:-"+help.getEmpid());
        viewHolder.tv3.setText(help.getSts());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String issue=viewHolder.tv1.getText().toString();
                String id=viewHolder.tv2.getText().toString();

                issue=issue.substring(7);
                id=id.substring(13);
                Intent intent= new Intent(context, DetailedPageEmp.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("issue",issue);
                intent.putExtra("id",id);

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
            tv1=itemView.findViewById(R.id.issue);
            tv2=itemView.findViewById(R.id.empid);
            tv3=itemView.findViewById(R.id.status);
        }
    }
}
