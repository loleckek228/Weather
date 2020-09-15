package com.geekbrains.android.homework.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geekbrains.android.homework.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ErrorActivity extends AppCompatActivity {

    @BindView(R.id.errorTextView)
    TextView errorTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        ButterKnife.bind(this);

        setErrorTextView();
    }

    private void setErrorTextView() {
        String error = getResources().getString(R.string.error);
        String city = getResources().getString(R.string.city);
        String cityName = getIntent().getStringExtra("City");
        String notFound = getResources().getString(R.string.not_found);

        String errorText = error + "\n" + city + ": " + cityName + "\n" + notFound;
        errorTextView.setText(errorText);
    }
}