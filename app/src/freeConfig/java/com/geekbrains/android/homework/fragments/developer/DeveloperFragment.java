package com.geekbrains.android.homework.fragments.developer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.geekbrains.android.homework.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DeveloperFragment extends Fragment {
    private DeveloperViewModel developerViewModel;
    private Unbinder unbinder;

    @BindView(R.id.developerTextView)
    TextView developerName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        developerViewModel = new ViewModelProvider(this).get(DeveloperViewModel.class);

        View root = inflater.inflate(R.layout.fragment_developer, container, false);

        unbinder = ButterKnife.bind(this, root);

        developerViewModel.getText().observe(getViewLifecycleOwner(), text -> {
            developerName.setText(text);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}