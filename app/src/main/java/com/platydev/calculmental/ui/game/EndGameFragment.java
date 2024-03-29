package com.platydev.calculmental.ui.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.platydev.calculmental.R;
import com.platydev.calculmental.data.options.Options;
import com.platydev.calculmental.data.score.Score;
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
        binding.finalScoreTextView.setText(String.valueOf(gameViewModel.score.getValue()));
        if (gameViewModel.getTimerTime() > 0) {
            binding.finalTimeTextView.setText(Utils.formatTime(gameViewModel.getTimerTime()));
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
        //displayResultDialog();
    }

    private void displayResultDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Bravo! Vous êtes dans le top 10.");
        builder.setMessage("Votre score est "+ gameViewModel.score.getValue()+".\nEntrez votre pseudo (15 caractères max).");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String pseudo = input.getText().toString().trim();
                if (!pseudo.isEmpty() && pseudo.length() <= 15) {
                    Score scoreObj = new Score(pseudo, gameViewModel.score.getValue(),
                            gameViewModel.getOptions().getNiveau(),
                            gameViewModel.getTimerTime() != 0 ? gameViewModel.getTimerTime() : gameViewModel.getOptions().getTemps(),
                            gameViewModel.getOptions().getMode().getNom());
                    ScoreDatabase.getInstance(getContext()).scoreDAO().insertScore(scoreObj);
                    dialog.cancel();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}