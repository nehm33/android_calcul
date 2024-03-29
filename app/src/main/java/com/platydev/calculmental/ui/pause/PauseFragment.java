package com.platydev.calculmental.ui.pause;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platydev.calculmental.R;
import com.platydev.calculmental.databinding.FragmentPauseBinding;
import com.platydev.calculmental.ui.game.GameFragment;
import com.platydev.calculmental.ui.game.GameViewModel;
import com.platydev.calculmental.ui.welcome.WelcomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PauseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PauseFragment extends Fragment {

    private FragmentPauseBinding binding;
    private GameViewModel gameViewModel;

    public PauseFragment() {
        // Required empty public constructor
    }


    public static PauseFragment newInstance() {
        return new PauseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPauseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                GameFragment gameFragment = GameFragment.newInstance();
                fragmentTransaction.replace(R.id.container, gameFragment);
                fragmentTransaction.commit();
            }
        });
        binding.restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameViewModel.setPaused(false);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                GameFragment gameFragment = GameFragment.newInstance();
                fragmentTransaction.replace(R.id.container, gameFragment);
                fragmentTransaction.commit();
            }
        });
        binding.quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameViewModel.setPaused(false);
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