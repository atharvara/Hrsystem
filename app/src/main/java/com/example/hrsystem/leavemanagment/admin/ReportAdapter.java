package com.example.hrsystem.leavemanagment.admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hrsystem.R;
import com.example.hrsystem.requesttask.AllDataTaskAdapter;
import com.example.hrsystem.requesttask.AllDataTaskModel;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private Context context;
    private List<ReportData> list;
    public ReportAdapter(Context context, List<ReportData> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.report_card, viewGroup, false);
        return new ReportAdapter.ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder viewHolder, int i) {
        ReportData Task=list.get(i);
        viewHolder.tv1.setText("Employee Id:-"+Task.getEmpid());
        viewHolder.tv2.setText("Employee Name:-"+Task.getEmpname());
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
            tv1=itemView.findViewById(R.id.empid);
            tv2=itemView.findViewById(R.id.empname);
            tv3=itemView.findViewById(R.id.status);
        }
    }
}
