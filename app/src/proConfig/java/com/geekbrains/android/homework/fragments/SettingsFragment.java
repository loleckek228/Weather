package com.geekbrains.android.homework.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.geekbrains.android.homework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsFragment extends Fragment {
    private SharedPreferences settingsPrefs;
    private Unbinder unbinder;

    private final String settingsPrefsKey = "named_prefs";
    private final String themeKey = "theme_key";

    @BindView(R.id.themeSwitch)
    Switch themeSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        settingsPrefs = getActivity().getSharedPreferences(settingsPrefsKey, Context.MODE_PRIVATE);

        readFromPreferences();

        setOnIsThemeSwitchChanged();
    }

    private void readFromPreferences() {
        boolean isTheme = settingsPrefs.getBoolean(themeKey, false);

        themeSwitch.setChecked(isTheme);
    }

    private void setOnIsThemeSwitchChanged() {
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = settingsPrefs.edit();

            editor.putBoolean(themeKey, isChecked);
            editor.apply();

            getActivity().recreate();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}