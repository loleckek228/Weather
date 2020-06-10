package com.geekbrains.android.homework;

import com.geekbrains.android.homework.fragments.SearchCityFragment;

public class CurrentFragment {
    private static CurrentFragment instance;

    private CurrentFragment() {}

    public static CurrentFragment getInstance() {
        if (instance == null) {
            instance = new CurrentFragment();
        }

        return instance;
    }

    private SearchCityFragment fragment;

    public SearchCityFragment getFragment() {
        return fragment;
    }

    public void setFragment(SearchCityFragment fragment) {
        this.fragment = fragment;
    }
}
