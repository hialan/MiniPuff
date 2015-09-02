package com.bigpuffs.minipuff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bigpuffs.minipuff.models.Candidate;
import com.bigpuffs.minipuff.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CandidatesAdapter extends ArrayAdapter<Candidate> {

    public CandidatesAdapter(Context context, List<Candidate> candidates) {
        super(context, android.R.layout.simple_list_item_1, candidates);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Candidate candidate = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_candidate, parent, false);
        }

        ImageView ivCandidate = (ImageView) convertView.findViewById(R.id.ivCandidate);
        ivCandidate.setImageResource(0);
        Picasso.with(getContext()).load(candidate.imageUrl).into(ivCandidate);

        return convertView;
    }
}
