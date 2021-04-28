package com.example.hrsystem.support.adminhelp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hrsystem.R;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    private Context context;
    private List<AdminHelpModel> list;
    public AdminAdapter(Context context, List<AdminHelpModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.help_emp_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AdminHelpModel help=list.get(i);
        viewHolder.tv1.setText("Issue:-"+help.getIssue());
        viewHolder.tv2.setText("Employee Id:-"+help.getEmpid());
        viewHolder.tv3.setText(help.getSts());
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
//