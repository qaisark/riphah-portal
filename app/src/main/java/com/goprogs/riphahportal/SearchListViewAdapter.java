package com.goprogs.riphahportal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchListViewAdapter extends BaseAdapter {
    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Query> QueryList = null;
    private ArrayList<Query> arraylist;

    public SearchListViewAdapter(Context context, List<Query> QueryList) {
        mContext = context;
        this.QueryList = QueryList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Query>();
        this.arraylist.addAll(QueryList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return QueryList.size();
    }

    @Override
    public Query getItem(int position) {
        return QueryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(QueryList.get(position).getTitle());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        QueryList.clear();
        if (charText.length() == 0) {
            QueryList.addAll(arraylist);
        } else {
            for (Query wp : arraylist) {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    QueryList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
