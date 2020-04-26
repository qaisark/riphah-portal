package com.goprogs.riphahportal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.MyViewHolder> {
    private List<Query> querylist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, created_at, qname;

        public MyViewHolder(View view) {
            super(view);
                
            title = (TextView) view.findViewById(R.id.qtitle);
            created_at = (TextView) view.findViewById(R.id.qcreated_at);
            qname = (TextView) view.findViewById(R.id.quname);
        }
    }


    public QueryAdapter(List<Query> querylist) {
        this.querylist = querylist;
    }

    @Override
    public QueryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.query_list_row, parent, false);

        return new QueryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QueryAdapter.MyViewHolder holder, int position) {
        Query query = querylist.get(position);

        holder.title.setText(query.getTitle());
        holder.created_at.setText(query.getCreated_at());
        holder.qname.setText(query.getUname());
    }

    @Override
    public int getItemCount() {
        return querylist.size();
    }
}
