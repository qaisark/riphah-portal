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


public class AnswerListAdapter extends BaseAdapter {
    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Answer> AnswerList = null;
    private ArrayList<Answer> arraylist;

    public AnswerListAdapter(Context context, List<Answer> AnswerList) {
        mContext = context;
        this.AnswerList = AnswerList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Answer>();
        this.arraylist.addAll(AnswerList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return AnswerList.size();
    }

    @Override
    public Answer getItem(int position) {
        return AnswerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.answer_listview, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.answerlabel);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(AnswerList.get(position).getAnswer());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        AnswerList.clear();
        if (charText.length() == 0) {
            AnswerList.addAll(arraylist);
        } else {
            for (Answer wp : arraylist) {
                if (wp.getAnswer().toLowerCase(Locale.getDefault()).contains(charText)) {
                    AnswerList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
