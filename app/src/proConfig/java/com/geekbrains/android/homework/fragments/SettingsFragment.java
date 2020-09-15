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

public class SettingsFragment extends Fragment {
    private SharedPreferences settingsPrefs;

    private Switch themeSwitch;

    private final String settingsPrefsKey = "named_prefs";
    private final String themeKey = "theme_key";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsPrefs = getActivity().getSharedPreferences(settingsPrefsKey, Context.MODE_PRIVATE);

        initViews(view);

        readFromPreferences();

        setOnIsThemeSwitchChanged();
    }

    private void initViews(View view) {
        themeSwitch = view.findViewById(R.id.themeSwitch);
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
}