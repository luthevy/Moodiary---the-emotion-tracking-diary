package com.example.moodiary;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EntriesFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.activity_showentries, container, false);
        startActivity(new Intent(getContext(),ShowEntriesActivity.class));
        // Inflate the layout for this fragment
        return null;
    }


}
