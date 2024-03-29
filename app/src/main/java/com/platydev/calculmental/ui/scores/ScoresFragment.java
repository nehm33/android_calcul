package com.platydev.calculmental.ui.scores;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.platydev.calculmental.data.options.Options;
import com.platydev.calculmental.data.score.Score;
import com.platydev.calculmental.data.utils.Utils;
import com.platydev.calculmental.databinding.FragmentScoresBinding;
import com.platydev.calculmental.db.ScoreDatabase;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoresFragment extends Fragment {

    private static final String OPTIONS_PARAM = "options";
    private static final int RANG_WEIGHT = 1;
    private static final int PSEUDO_WEIGHT = 3;
    private static final int SCORE_WEIGHT = 1;
    private static final int TEMPS_WEIGHT = 2;
    private static final int DATE_WEIGHT = 3;
    private static final int HEADER_TEXT_SIZE = 13;
    private static final int LINE_TEXT_SIZE = 10;

    private FragmentScoresBinding binding;
    private Options options;

    public ScoresFragment() {
        // Required empty public constructor
    }


    public static ScoresFragment newInstance(Options options) {
        ScoresFragment fragment = new ScoresFragment();
        Bundle args = new Bundle();
        args.putSerializable(OPTIONS_PARAM, options);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            options = (Options) getArguments().getSerializable(OPTIONS_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScoresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTableHeader();

        Observer<List<Score>> observer = new Observer<List<Score>>() {
            @Override
            public void onChanged(List<Score> scores) {
                if (scores != null) {
                    Collections.sort(scores);
                    Collections.reverse(scores);
                    setTableLines(scores);
                }
            }
        };

        ScoreDatabase database = ScoreDatabase.getInstance(getContext());
        if (options.getMode().isTimeLimitedMode()) {
            database.scoreDAO()
                    .getScores(options.getNiveau(), options.getMode().getNom(), options.getTemps())
                    .observe(getViewLifecycleOwner(), observer);
        } else {
            database.scoreDAO()
                    .getNotFixedTimeModeScores(options.getNiveau(), options.getMode().getNom())
                    .observe(getViewLifecycleOwner(), observer);
        }
    }

    private void setTableLines(List<Score> scoreList) {
        for (int i=0; i < scoreList.size(); i++) {
            Score score = scoreList.get(i);
            TableRow line = new TableRow(getContext());
            line.addView(createTableLineTextView(String.valueOf(i+1), RANG_WEIGHT));
            line.addView(createTableLineTextView(score.getPseudo(), PSEUDO_WEIGHT));
            line.addView(createTableLineTextView(String.valueOf(score.getScore()), SCORE_WEIGHT));
            if (!options.getMode().isTimeLimitedMode()) {
                line.addView(createTableLineTextView(Utils.formatTime(score.getTemps()), TEMPS_WEIGHT));
            }
            line.addView(createTableLineTextView(score.getDate(), DATE_WEIGHT));
            binding.table.addView(line);
        }
    }

    private void setTableHeader() {
        TableRow header = new TableRow(getContext());
        header.setBackgroundColor(Color.GREEN);
        header.addView(createTableHeaderTextView("Rang", RANG_WEIGHT));
        header.addView(createTableHeaderTextView("Pseudo", PSEUDO_WEIGHT));
        header.addView(createTableHeaderTextView("Score", SCORE_WEIGHT));
        if (!options.getMode().isTimeLimitedMode()) {
            header.addView(createTableHeaderTextView("Temps", TEMPS_WEIGHT));
        }
        header.addView(createTableHeaderTextView("Date", DATE_WEIGHT));
        binding.table.addView(header);
    }

    private TextView createTableHeaderTextView(String text, int weight) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(HEADER_TEXT_SIZE);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight));
        return textView;
    }

    private TextView createTableLineTextView(String text, int weight) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(LINE_TEXT_SIZE);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight));
        return textView;
    }
}