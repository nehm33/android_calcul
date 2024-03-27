package com.platydev.calculmental.ui.options;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.platydev.calculmental.R;
import com.platydev.calculmental.data.options.Mode;
import com.platydev.calculmental.data.options.Options;
import com.platydev.calculmental.databinding.FragmentOptionsBinding;
import com.platydev.calculmental.injection.datastore.OptionsDataStore;
import com.platydev.calculmental.injection.datastore.factory.OptionsDataStoreFactory;
import com.platydev.calculmental.ui.welcome.WelcomeFragment;


public class OptionsFragment extends Fragment {

    private FragmentOptionsBinding binding;

    public OptionsFragment() {
        // Required empty public constructor
    }


    public static OptionsFragment newInstance() {
        return new OptionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setComponents();
    }

    private void setComponents() {
        OptionsDataStore dataStore = (OptionsDataStore) OptionsDataStoreFactory.getInstance()
                .createDataStore(getActivity());
        Options options = dataStore.readOptions();
        binding.levelSeekBar.setMin(Options.NIVEAU_MIN);
        binding.levelSeekBar.setMax(Options.NIVEAU_MAX);
        binding.levelSeekBar.setProgress(options.getNiveau());
        binding.levelTextView.setText(String.valueOf(options.getNiveau()));
        binding.levelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.levelTextView.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        selectRadioButton(options.getMode()).setChecked(true);

        binding.zenButton.setOnClickListener(new TimeModeClickListener(Mode.Zen));
        binding.clmButton.setOnClickListener(new TimeModeClickListener(Mode.CLM));
        binding.infiniButton.setOnClickListener(new TimeModeClickListener(Mode.Infini));
        binding.arcadeButton.setOnClickListener(new TimeModeClickListener(Mode.Arcade));

        binding.timeSeekBar.setMin(Options.TEMPS_MIN/Options.TEMPS_PAS);
        binding.timeSeekBar.setMax(Options.TEMPS_MAX/Options.TEMPS_PAS);
        binding.timeSeekBar.setProgress(options.getTemps()/Options.TEMPS_PAS);
        binding.timeTextView.setText(String.valueOf(options.getTemps()));
        binding.timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.timeTextView.setText(String.valueOf(seekBar.getProgress()*Options.TEMPS_PAS));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.validationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int niveau = binding.levelSeekBar.getProgress();
                int temps = binding.timeSeekBar.getProgress()*Options.TEMPS_PAS;
                Mode mode = getModeSelected();
                dataStore.writeOptions(new Options(niveau, temps, mode));
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                WelcomeFragment welcomeFragment = WelcomeFragment.newInstance();
                fragmentTransaction.replace(R.id.container, welcomeFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private RadioButton selectRadioButton(Mode mode) {
        switch (mode) {
            case CLM:
                return binding.clmButton;
            case Infini:
                setTimeSeekBarEnabled(false);
                return binding.infiniButton;
            case Arcade:
                return binding.arcadeButton;
            default:
                setTimeSeekBarEnabled(false);
                return binding.zenButton;
        }
    }

    private Mode getModeSelected() {
        if (binding.zenButton.isChecked()) return Mode.Zen;
        else if (binding.clmButton.isChecked()) return Mode.CLM;
        else if (binding.infiniButton.isChecked()) return Mode.Infini;
        else return Mode.Arcade;
    }

    private void setTimeSeekBarEnabled(boolean enabled) {
        binding.timeSeekBar.setEnabled(enabled);
        binding.timeTextView.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
    }

    private class TimeModeClickListener implements View.OnClickListener {

        private final boolean isTimeLimitedMode;

        public TimeModeClickListener(Mode mode) {
            this.isTimeLimitedMode = mode.isTimeLimitedMode();
        }

        @Override
        public void onClick(View v) {
            setTimeSeekBarEnabled(isTimeLimitedMode);
        }
    }
}