package com.bigpuffs.minipuff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import com.bigpuffs.minipuff.R;
import com.bigpuffs.minipuff.models.Option;

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

        RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radioButton);
        radioButton.setText(option.text);

        return convertView;
    }
}
