package com.platydev.calculmental.ui.scores;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.platydev.calculmental.R;
import com.platydev.calculmental.data.options.Options;
import com.platydev.calculmental.data.score.Score;
import com.platydev.calculmental.databinding.FragmentScoresBinding;
import com.platydev.calculmental.db.ScoreDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoresFragment extends Fragment {

    private static final String OPTIONS_PARAM = "options";

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
        setTableLines(ScoreDatabase.getInstance(getContext()).scoreDAO().getScores(options.getNiveau(), options.getMode().getNom(), options.getTemps()));
    }

    private void setTableLines(List<Score> scoreList) {
        for (int i=0; i < scoreList.size(); i++) {
            Score score = scoreList.get(i);
            TableRow line = new TableRow(getContext());
            line.addView(createTableHeaderTextView(String.valueOf(i+1), 1));
            line.addView(createTableHeaderTextView(score.getPseudo(), options.getMode().isTimeLimitedMode() ? 3 : 2));
            line.addView(createTableHeaderTextView(String.valueOf(score.getScore()), 2));
            if (!options.getMode().isTimeLimitedMode()) {
                line.addView(createTableHeaderTextView(String.valueOf(score.getTemps()), 2));
            }
            line.addView(createTableHeaderTextView(score.getDateString(), 3));
            binding.table.addView(line);
        }
    }

    private void setTableHeader() {
        TableRow header = new TableRow(getContext());
        header.setBackgroundColor(Color.GREEN);
        header.addView(createTableLineTextView("Rang", 1));
        header.addView(createTableLineTextView("Pseudo", options.getMode().isTimeLimitedMode() ? 3 : 2));
        header.addView(createTableLineTextView("Score", 2));
        if (!options.getMode().isTimeLimitedMode()) {
            header.addView(createTableLineTextView("Temps", 2));
        }
        header.addView(createTableLineTextView("Date", 3));
        binding.table.addView(header);
    }

    private TextView createTableHeaderTextView(String text, int weight) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(10);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight));
        return textView;
    }

    private TextView createTableLineTextView(String text, int weight) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(14);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight));
        return textView;
    }
}