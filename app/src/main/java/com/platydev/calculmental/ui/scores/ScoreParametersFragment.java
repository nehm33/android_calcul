package com.platydev.calculmental.ui.scores;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SeekBar;

import com.platydev.calculmental.R;
import com.platydev.calculmental.data.options.Mode;
import com.platydev.calculmental.data.options.Options;
import com.platydev.calculmental.databinding.FragmentScoreParametersBinding;
import com.platydev.calculmental.ui.welcome.WelcomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreParametersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreParametersFragment extends Fragment {

    private static Options options = new Options(Options.NIVEAU_MIN, Options.TEMPS_MIN, Mode.CLM);

    private FragmentScoreParametersBinding binding;

    public ScoreParametersFragment() {
        // Required empty public constructor
    }

    public static ScoreParametersFragment newInstance() {
        return new ScoreParametersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScoreParametersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setComponents();
    }

    private void setComponents() {
        binding.levelSeekBar.setMin(Options.NIVEAU_MIN);
        binding.levelSeekBar.setMax(Options.NIVEAU_MAX);
        binding.levelSeekBar.setProgress(options.getNiveau());
        binding.levelTextView.setText(String.valueOf(options.getNiveau()));
        binding.levelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                options.setNiveau(seekBar.getProgress());
                binding.levelTextView.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        selectRadioButton().setChecked(true);

        binding.clmButton.setOnClickListener(new ScoreParametersFragment.TimeModeClickListener(Mode.CLM));
        binding.infiniButton.setOnClickListener(new ScoreParametersFragment.TimeModeClickListener(Mode.Infini));
        binding.arcadeButton.setOnClickListener(new ScoreParametersFragment.TimeModeClickListener(Mode.Arcade));

        binding.timeSeekBar.setMin(Options.TEMPS_MIN/Options.TEMPS_PAS);
        binding.timeSeekBar.setMax(Options.TEMPS_MAX/Options.TEMPS_PAS);
        binding.timeSeekBar.setProgress(options.getTemps()/Options.TEMPS_PAS);
        binding.timeTextView.setText(String.valueOf(options.getTemps()));
        binding.timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                options.setTemps(seekBar.getProgress()*Options.TEMPS_PAS);
                binding.timeTextView.setText(String.valueOf(seekBar.getProgress()*Options.TEMPS_PAS));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScoresFragment scoresFragment = ScoresFragment.newInstance(options);
                fragmentTransaction.replace(R.id.container, scoresFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private RadioButton selectRadioButton() {
        Mode mode = options.getMode();
        switch (mode) {
            case CLM:
                return binding.clmButton;
            case Infini:
                setTimeSeekBarEnabled(false);
                return binding.infiniButton;
            default:
                return binding.arcadeButton;
        }
    }

    private void setTimeSeekBarEnabled(boolean enabled) {
        binding.timeSeekBar.setEnabled(enabled);
        binding.timeTextView.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }

    private class TimeModeClickListener implements View.OnClickListener {

        private final boolean isTimeLimitedMode;
        private final Mode mode;

        public TimeModeClickListener(Mode mode) {
            this.isTimeLimitedMode = mode.isTimeLimitedMode();
            this.mode = mode;
        }

        @Override
        public void onClick(View v) {
            setTimeSeekBarEnabled(isTimeLimitedMode);
            options.setMode(mode);
        }
    }
}