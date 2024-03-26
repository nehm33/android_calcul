package com.platydev.calculmental.ui.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platydev.calculmental.R;
import com.platydev.calculmental.data.utils.Utils;
import com.platydev.calculmental.databinding.FragmentEndGameBinding;
import com.platydev.calculmental.ui.welcome.WelcomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EndGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EndGameFragment extends Fragment {

    private static final String SCORE_PARAM = "score";
    private static final String TIME_PARAM = "time";

    private FragmentEndGameBinding binding;
    private int score;
    private long time;

    public EndGameFragment() {
        // Required empty public constructor
    }

    public static EndGameFragment newInstance(int score, long time) {
        EndGameFragment endGameFragment = new EndGameFragment();
        Bundle args = new Bundle();
        args.putInt(SCORE_PARAM, score);
        args.putLong(TIME_PARAM, time);
        endGameFragment.setArguments(args);
        return endGameFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getInt(SCORE_PARAM);
            time = getArguments().getLong(TIME_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEndGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.finalScoreTextView.setText(""+score);
        if (time > 0) {
            binding.finalTimeTextView.setText(Utils.formatTime(time));
        } else {
            binding.yourTimeTextView.setVisibility(View.INVISIBLE);
        }
        binding.welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                for (int i=0; i<fragmentManager.getBackStackEntryCount(); i++) {
                    fragmentManager.popBackStack();
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                WelcomeFragment welcomeFragment = WelcomeFragment.newInstance();
                fragmentTransaction.replace(R.id.container, welcomeFragment);
                fragmentTransaction.commit();
            }
        });
    }
}