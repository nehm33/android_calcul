package com.platydev.calculmental.ui.pause;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platydev.calculmental.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PauseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PauseFragment extends Fragment {

    public PauseFragment() {
        // Required empty public constructor
    }


    public static PauseFragment newInstance(String param1, String param2) {
        return new PauseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pause, container, false);
    }
}