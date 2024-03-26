package com.platydev.calculmental.ui.game;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.platydev.calculmental.R;
import com.platydev.calculmental.data.gamelogic.EquationCheckResult;
import com.platydev.calculmental.data.gamelogic.GameLogicUpdate;
import com.platydev.calculmental.data.options.Options;
import com.platydev.calculmental.data.utils.Utils;
import com.platydev.calculmental.databinding.FragmentGameBinding;
import com.platydev.calculmental.injection.datastore.OptionsDataStore;
import com.platydev.calculmental.injection.datastore.factory.OptionsDataStoreFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    private static final int END_PAUSE_DURATION = 3000;
    private static final int[] countDownSound = {R.raw.un, R.raw.deux, R.raw.trois};

    private FragmentGameBinding binding;
    private GameViewModel gameViewModel;
    private CountDownTimer timer;

    public GameFragment() {
        // Required empty public constructor
    }


    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OptionsDataStore dataStore = (OptionsDataStore) OptionsDataStoreFactory.getInstance()
                .createDataStore(requireActivity());
        Options options = dataStore.readOptions();
        gameViewModel.init(options);
        setComponentsListener();
        setObservers();
        initTimer();
        launchCountDownAnimation();
    }

    public void startGame() {
        binding.waitTextView.setVisibility(View.INVISIBLE);
        binding.gameLayout.setVisibility(View.VISIBLE);
        gameViewModel.startGame();
        if (timer != null) {
            timer.start();
        }
    }

    private void stopGame() {
        if (timer != null) {
            timer.cancel();
        }
        MediaPlayer.create(getContext(), R.raw.fin).start();
        setComponentsDisabled();
        new CountDownTimer(END_PAUSE_DURATION, END_PAUSE_DURATION) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EndGameFragment endGameFragment = EndGameFragment.newInstance(gameViewModel.score.getValue(), gameViewModel.getTimerTime());
                fragmentTransaction.replace(R.id.container, endGameFragment);
                fragmentTransaction.commit();
            }
        }.start();
    }

    private void checkEquation(int result) {
        EquationCheckResult equationCheckResult = gameViewModel.checkEquation(result);
        GameLogicUpdate gameLogicUpdate = equationCheckResult.getGameLogicUpdate();
        binding.resultTextView.setText("");
        if (equationCheckResult.isGoodAnswer()) {
            MediaPlayer.create(getContext(), R.raw.vrai).start();
        } else {
            MediaPlayer.create(getContext(), R.raw.faux).start();
        }
        launchBonusVariationAnimation(binding.bonusScoreTextView, gameLogicUpdate.getScoreVariation());
        launchBonusVariationAnimation(binding.bonusTimeTextView, gameLogicUpdate.getTimeVariation());
    }

    private void launchBonusVariationAnimation(TextView textView, int variation) {
        if (variation != 0) {
            ObjectAnimator animation = ObjectAnimator.ofFloat(textView, "y", textView.getY(), textView.getY()-120);
            animation.setDuration(300);
            animation.addListener(new BonusAnimationListener(textView, variation));
            animation.start();
        }
    }

    private void launchCountDownAnimation() {
        ObjectAnimator countDownAnimation = ObjectAnimator.ofFloat(binding.waitTextView, "textSize", 0, 100);
        countDownAnimation.setDuration(1500);
        countDownAnimation.setRepeatCount(2);
        countDownAnimation.addListener(new CountDownAnimationListener());
        countDownAnimation.start();
    }

    private void initTimer() {
        if (gameViewModel.isTimerNeeded()) {
            long gameViewModelTimerTime = gameViewModel.getTimerTime();
            binding.timerTextView.setText(Utils.formatTime(gameViewModelTimerTime));
            long timerTime = gameViewModel.isTimelessTimer() ? Long.MAX_VALUE : gameViewModelTimerTime*1000;
            timer = new CountDownTimer(timerTime,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (gameViewModel.isChronometer()) {
                        gameViewModel.incrementTimerTime();
                    } else {
                        gameViewModel.decrementTimerTime();
                    }
                    binding.timerTextView.setText(Utils.formatTime(gameViewModel.getTimerTime()));
                }

                @Override
                public void onFinish() {
                    binding.timerTextView.setText("00:00");
                    gameViewModel.decrementTimerTime();
                }
            };
        }
    }

    private void setObservers() {
        gameViewModel.score.observe(getViewLifecycleOwner(), integer -> binding.scoreTextView.setText("Score: "+integer));
        gameViewModel.currentOperation.observe(getViewLifecycleOwner(), operation -> binding.equationTextView.setText(operation.toString()));
        gameViewModel.isFinished.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                stopGame();
            }
        });
    }

    private void setComponentsListener() {
        binding.btn0.setOnClickListener(new ClavierClickListener(0));
        binding.btn1.setOnClickListener(new ClavierClickListener(1));
        binding.btn2.setOnClickListener(new ClavierClickListener(2));
        binding.btn3.setOnClickListener(new ClavierClickListener(3));
        binding.btn4.setOnClickListener(new ClavierClickListener(4));
        binding.btn5.setOnClickListener(new ClavierClickListener(5));
        binding.btn6.setOnClickListener(new ClavierClickListener(6));
        binding.btn7.setOnClickListener(new ClavierClickListener(7));
        binding.btn8.setOnClickListener(new ClavierClickListener(8));
        binding.btn9.setOnClickListener(new ClavierClickListener(9));
        binding.btnEnter.setOnClickListener(v -> {
            String resultText = binding.resultTextView.getText().toString();
            if (!resultText.isEmpty()) {
                int result = Integer.parseInt(binding.resultTextView.getText().toString());
                checkEquation(result);
            }
        });
        binding.btnReturn.setOnClickListener(v -> {
            String result = binding.resultTextView.getText().toString();
            if (!result.isEmpty()) {
                StringBuilder newResult = new StringBuilder(result);
                newResult.deleteCharAt(newResult.length()-1);
                binding.resultTextView.setText(newResult.toString());
            }
        });
    }

    private void setComponentsDisabled() {
        binding.pauseButton.setEnabled(false);
        binding.btn0.setEnabled(false);
        binding.btn1.setEnabled(false);
        binding.btn2.setEnabled(false);
        binding.btn3.setEnabled(false);
        binding.btn4.setEnabled(false);
        binding.btn5.setEnabled(false);
        binding.btn6.setEnabled(false);
        binding.btn7.setEnabled(false);
        binding.btn8.setEnabled(false);
        binding.btn9.setEnabled(false);
        binding.btnReturn.setEnabled(false);
        binding.btnEnter.setEnabled(false);
    }

    private class ClavierClickListener implements View.OnClickListener {

        private final int n;

        public ClavierClickListener(int n) {
            this.n = n;
        }

        @Override
        public void onClick(View v) {
            StringBuilder newResult = new StringBuilder(binding.resultTextView.getText());
            newResult.append(n);
            binding.resultTextView.setText(newResult.toString());
        }
    }

    private class CountDownAnimationListener implements Animator.AnimatorListener {

        private int numberToDisplay = 3;
        @Override
        public void onAnimationStart(@NonNull Animator animation) {
            MediaPlayer.create(getContext(), countDownSound[numberToDisplay-1]).start();
            binding.waitTextView.setText(""+numberToDisplay);
        }

        @Override
        public void onAnimationEnd(@NonNull Animator animation) {
            startGame();
        }

        @Override
        public void onAnimationCancel(@NonNull Animator animation) {

        }

        @Override
        public void onAnimationRepeat(@NonNull Animator animation) {
            numberToDisplay--;
            MediaPlayer.create(getContext(), countDownSound[numberToDisplay-1]).start();
            binding.waitTextView.setText(""+numberToDisplay);
        }
    }

    private static class BonusAnimationListener implements Animator.AnimatorListener {

        private final TextView textView;
        private final int variation;
        private final float initialY;

        public BonusAnimationListener(TextView textView, int variation) {
            this.textView = textView;
            this.variation = variation;
            this.initialY = textView.getY();
        }

        @Override
        public void onAnimationStart(@NonNull Animator animation) {
            if (variation > 0) {
                textView.setTextColor(Color.GREEN);
                textView.setText("+"+variation);
            } else {
                textView.setTextColor(Color.RED);
                textView.setText(""+variation);
            }
        }

        @Override
        public void onAnimationEnd(@NonNull Animator animation) {
            textView.setText("");
            textView.setY(initialY);
        }

        @Override
        public void onAnimationCancel(@NonNull Animator animation) {

        }

        @Override
        public void onAnimationRepeat(@NonNull Animator animation) {

        }
    }
}