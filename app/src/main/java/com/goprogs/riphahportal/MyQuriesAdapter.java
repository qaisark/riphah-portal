package com.goprogs.riphahportal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyQuriesAdapter extends RecyclerView.Adapter<MyQuriesAdapter.MyViewHolder> {
    private List<Query> querylist;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, created_at, qname;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.myqtitle);
            created_at = (TextView) view.findViewById(R.id.myqcreated_at);

        }
    }


    public MyQuriesAdapter(List<Query> querylist) {
        this.querylist = querylist;
    }

    @Override
    public MyQuriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_query_list, parent, false);

        return new MyQuriesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyQuriesAdapter.MyViewHolder holder, int position) {
        Query query = querylist.get(position);

        holder.title.setText(query.getTitle());
        holder.created_at.setText(query.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return querylist.size();
    }
}
