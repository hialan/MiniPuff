package com.bigpuffs.minipuff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bigpuffs.minipuff.models.Candidate;
import com.bigpuffs.minipuff.R;
import com.bigpuffs.minipuff.models.Option;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OptionsAdapter extends ArrayAdapter<Option> {

    public OptionsAdapter(Context context, List<Option> candidates) {
        super(context, android.R.layout.simple_list_item_1, candidates);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Option option = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_option, parent, false);
        }

        return convertView;
    }
}
