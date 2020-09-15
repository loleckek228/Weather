package com.geekbrains.android.homework.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android.homework.R;
import com.geekbrains.android.homework.ThermometerView;
import com.geekbrains.android.homework.Weather;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerWeatherAdapter extends RecyclerView.Adapter<RecyclerWeatherAdapter.ViewHolder> {
    private Weather[] weathers;

    public RecyclerWeatherAdapter(Weather[] weathers) {
        this.weathers = weathers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lilst_temperature_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setText(holder, position);
    }

    @Override
    public int getItemCount() {
        return weathers == null ? 0 : weathers.length;
    }

    private void setText(@NonNull ViewHolder holder, int position) {
        holder.dataListItemTextView.setText(weathers[position].getDate());
        holder.temperatureListItemTextView.setText(weathers[position].getTemperature());
        holder.thermometerView.setCurrentTemp(weathers[position].getFloatTemp());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thermometerView)
        ThermometerView thermometerView;

        @BindView(R.id.dataListItemTextView)
        TextView dataListItemTextView;

        @BindView(R.id.temperatureListItemTextView)
        TextView temperatureListItemTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}