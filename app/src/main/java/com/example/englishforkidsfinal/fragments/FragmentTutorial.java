package com.example.englishforkidsfinal.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.englishforkidsfinal.R;
import com.example.englishforkidsfinal.activities.MainActivity;
import com.example.englishforkidsfinal.models.Tools;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.concurrent.Callable;

import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.ALPHABET;
import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.GAME;
import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.HOME;
import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.LEARNING;
import static com.example.englishforkidsfinal.models.contractions.ArgumentsContractions.TUTORIAL_ARGUMENT;
import static com.example.englishforkidsfinal.models.contractions.CacheContractions.CACHE_CACHE;
import static com.example.englishforkidsfinal.models.contractions.CacheContractions.CACHE_HAD_TUTORIAL;

public class FragmentTutorial extends Fragment {

    private LinearLayout ll;
    private MaterialTextView tv;
    private MaterialButton btn;
    private Bundle args;
    private ImageView home, alphabet, games, learning;
    private Animation animation;
    private AnimatorSet animatorSet = new AnimatorSet();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.cheer_scale);
        ll = view.findViewById(R.id.ll);
        tv = view.findViewById(R.id.tv);
        btn = view.findViewById(R.id.btn);
        home = view.findViewById(R.id.arrow_home);
        alphabet = view.findViewById(R.id.arrow_alphabet);
        games = view.findViewById(R.id.arrow_games);
        learning = view.findViewById(R.id.arrow_learning);

        restart();

        args = getArguments();

        Drawable drawable = ll.getBackground();
        drawable.setAlpha(150);
        ll.setBackground(drawable);

        tv.setTypeface(MainActivity.typeface);
        btn.setTypeface(MainActivity.typeface);

        if (args == null) {
            // Welcome state
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.welcome);
            btn.setVisibility(View.VISIBLE);
            btn.startAnimation(animation);
            Callable<Void> callable = () -> {
                btn.setOnClickListener(v -> {
                    tv.setText(R.string.next);
                    home.setVisibility(View.VISIBLE);
                    btn.setVisibility(View.INVISIBLE);
                    ObjectAnimator animateUpDown = ObjectAnimator.ofFloat(home, "translationY", 0, 30f);
                    ObjectAnimator animateDownUp = ObjectAnimator.ofFloat(home, "translationY", 30f, 0);
                    animatorSet.playSequentially(animateUpDown, animateDownUp);
                    animatorSet.start();
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animatorSet.start();
                        }
                    });
                    ((MainActivity) getActivity()).loadTheOnlyNavigation(R.id.home, () -> {
                        home.setVisibility(View.INVISIBLE);
                        animatorSet.end();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment, new HomeFragment())
                                .commit();
                        ((MainActivity) getActivity()).removeBesidesLast();
                        FragmentTutorial ft = new FragmentTutorial();
                        args = new Bundle();
                        args.putString(TUTORIAL_ARGUMENT, HOME);
                        ft.setArguments(args);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment, ft)
                                .commit();
                        return null;
                    });
                });
                return null;
            };
            new Tools.CountDown(callable).execute(1000);
        } else if (args.getString(TUTORIAL_ARGUMENT).equals(HOME)) {
            // Home state
            ((MainActivity) getActivity()).clearNavigation();
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.home_tutorial);
            btn.setVisibility(View.VISIBLE);
            btn.startAnimation(animation);
            Callable<Void> callable = () -> {
                btn.setOnClickListener(view1 -> {
                    tv.setText(R.string.next);
                    alphabet.setVisibility(View.VISIBLE);
                    btn.setVisibility(View.INVISIBLE);
                    ObjectAnimator animateUpDown = ObjectAnimator.ofFloat(alphabet, "translationY", 0, 30f);
                    ObjectAnimator animateDownUp = ObjectAnimator.ofFloat(alphabet, "translationY", 30f, 0);
                    animatorSet.playSequentially(animateUpDown, animateDownUp);
                    animatorSet.start();
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animatorSet.start();
                        }
                    });
                    ((MainActivity) getActivity()).loadTheOnlyNavigation(R.id.alphabet, () -> {
                        alphabet.setVisibility(View.INVISIBLE);
                        animatorSet.end();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment, new AlphabetFragment())
                                .commit();
                        ((MainActivity) getActivity()).removeBesidesLast();
                        FragmentTutorial ft = new FragmentTutorial();
                        args = new Bundle();
                        args.putString(TUTORIAL_ARGUMENT, ALPHABET);
                        ft.setArguments(args);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment, ft)
                                .commit();
                        return null;
                    });
                });
                return null;
            };
            new Tools.CountDown(callable).execute(1000);
        } else if (args.getString(TUTORIAL_ARGUMENT).equals(ALPHABET)) {
            // Alphabet state
            ((MainActivity) getActivity()).clearNavigation();
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.alphabet_tutorial);
            btn.setVisibility(View.VISIBLE);
            btn.startAnimation(animation);
            Callable<Void> callable = () -> {
                btn.setOnClickListener(view1 -> {
                    tv.setText(R.string.next);
                    games.setVisibility(View.VISIBLE);
                    btn.setVisibility(View.INVISIBLE);
                    ObjectAnimator animateUpDown = ObjectAnimator.ofFloat(games, "translationY", 0, 30f);
                    ObjectAnimator animateDownUp = ObjectAnimator.ofFloat(games, "translationY", 30f, 0);
                    animatorSet.playSequentially(animateUpDown, animateDownUp);
                    animatorSet.start();
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animatorSet.start();
                        }
                    });
                    ((MainActivity) getActivity()).loadTheOnlyNavigation(R.id.games, () -> {
                        games.setVisibility(View.INVISIBLE);
                        animatorSet.end();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment, new GamesFragment())
                                .commit();
                        ((MainActivity) getActivity()).removeBesidesLast();
                        FragmentTutorial ft = new FragmentTutorial();
                        args = new Bundle();
                        args.putString(TUTORIAL_ARGUMENT, GAME);
                        ft.setArguments(args);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment, ft)
                                .commit();
                        return null;
                    });
                });
                return null;
            };
            new Tools.CountDown(callable).execute(1000);
        } else if (args.getString(TUTORIAL_ARGUMENT).equals(GAME)) {
            // Game state
            ((MainActivity) getActivity()).clearNavigation();
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.games_tutorial);
            btn.setVisibility(View.VISIBLE);
            btn.startAnimation(animation);
            Callable<Void> callable = () -> {
                btn.setOnClickListener(view1 -> {
                    tv.setText(R.string.next);
                    learning.setVisibility(View.VISIBLE);
                    btn.setVisibility(View.INVISIBLE);
                    ObjectAnimator animateUpDown = ObjectAnimator.ofFloat(learning, "translationY", 0, 30f);
                    ObjectAnimator animateDownUp = ObjectAnimator.ofFloat(learning, "translationY", 30f, 0);
                    animatorSet.playSequentially(animateUpDown, animateDownUp);
                    animatorSet.start();
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animatorSet.start();
                        }
                    });
                    ((MainActivity) getActivity()).loadTheOnlyNavigation(R.id.learning, () -> {
                        learning.setVisibility(View.INVISIBLE);
                        animatorSet.end();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment, new LearningFragment())
                                .commit();
                        ((MainActivity) getActivity()).removeBesidesLast();
                        FragmentTutorial ft = new FragmentTutorial();
                        args = new Bundle();
                        args.putString(TUTORIAL_ARGUMENT, LEARNING);
                        ft.setArguments(args);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fragment, ft)
                                .commit();
                        return null;
                    });
                });
                return null;
            };
            new Tools.CountDown(callable).execute(1000);
        } else if (args.getString(TUTORIAL_ARGUMENT).equals(LEARNING)) {
            // Learning state
            ((MainActivity) getActivity()).clearNavigation();
            tv.setVisibility(View.VISIBLE);
            tv.setText(R.string.learning_tutorial);
            btn.setVisibility(View.VISIBLE);
            btn.startAnimation(animation);
            Callable<Void> callable = () -> {
                btn.setOnClickListener(view1 -> {
                    tv.setVisibility(View.INVISIBLE);
                    btn.setText(R.string.finish);
                    btn.setOnClickListener(view2 -> {
                        SharedPreferences sp = getActivity().getSharedPreferences(CACHE_CACHE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean(CACHE_HAD_TUTORIAL, true);
                        editor.apply();
                        ((MainActivity) getActivity()).restart();
                    });
                });
                return null;
            };
            new Tools.CountDown(callable).execute(1000);
        }

//        home.setVisibility(View.INVISIBLE);
//        animatorSet.end();
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.fragment, new HomeFragment())
//                .commit();
//        ((MainActivity) getActivity()).removeBesidesLast();
//        FragmentTutorial ft = new FragmentTutorial();
//        args = new Bundle();
//        args.putString(TUTORIAL_ARGUMENT, HOME);
//        ft.setArguments(args);
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.fragment, ft)
//                .commit();
//        return true;

        return view;
    }

    public void restart() {
        tv.setText("");
        tv.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);
        home.setVisibility(View.INVISIBLE);
        alphabet.setVisibility(View.INVISIBLE);
        games.setVisibility(View.INVISIBLE);
        learning.setVisibility(View.INVISIBLE);
        animatorSet.end();
    }
}