package com.platydev.calculmental.ui.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.platydev.calculmental.R;
import com.platydev.calculmental.data.utils.Utils;
import com.platydev.calculmental.databinding.FragmentEndGameBinding;
import com.platydev.calculmental.db.ScoreDatabase;
import com.platydev.calculmental.ui.welcome.WelcomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EndGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EndGameFragment extends Fragment {

    private FragmentEndGameBinding binding;
    private GameViewModel gameViewModel;

    public EndGameFragment() {
        // Required empty public constructor
    }

    public static EndGameFragment newInstance() {
        return new EndGameFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        gameViewModel.reset();
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
        gameViewModel.isScoreHighScore(ScoreDatabase.getInstance(getContext()), getViewLifecycleOwner());
        initComponents();
        setComponentsListener();
        setObservers();
    }

    private void initComponents() {
        binding.finalScoreTextView.setText(String.valueOf(gameViewModel.score.getValue()));
        if (gameViewModel.getTimerTime() > 0) {
            binding.finalTimeTextView.setText(Utils.formatTime(gameViewModel.getTimerTime()));
        } else {
            binding.yourTimeTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void setComponentsListener() {
        binding.welcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pseudo = binding.pseudoEditText.getText().toString().trim();
                if (gameViewModel.saveScore(pseudo, ScoreDatabase.getInstance(getContext()))) {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    for (int i=0; i<fragmentManager.getBackStackEntryCount(); i++) {
                        fragmentManager.popBackStack();
                    }
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    WelcomeFragment welcomeFragment = WelcomeFragment.newInstance();
                    fragmentTransaction.replace(R.id.container, welcomeFragment);
                    fragmentTransaction.commit();
                } else {
                    displayErrorDialog();
                }
            }
        });
    }

    private void setObservers() {
        gameViewModel.isHighScore.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.titleTextView.setTextSize(30);
                    binding.titleTextView.setText(getResources().getString(R.string.nouveau_meilleur_score));
                    binding.congratulationsTextView.setText(getResources().getString(R.string.felicitations));
                    binding.welcomeButton.setText(getResources().getString(R.string.enregistrer));
                    binding.questionTextView.setVisibility(View.VISIBLE);
                    binding.pseudoEditText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void displayErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Pseudo incorrect");
        builder.setMessage("Votre pseudo doit avoir entre 1 et 15 caractères.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}