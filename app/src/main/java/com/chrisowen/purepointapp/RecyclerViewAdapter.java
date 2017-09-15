package com.chrisowen.purepointapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrisowen.purepointapp.pojo.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {
    private List<Recipe> recipeList;

    public RecyclerViewAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Recipe recipe = recipeList.get(i);
        customViewHolder.textView.setText(recipe.getTitle());
    }

    @Override
    public int getItemCount() {
        return (null != recipeList ? recipeList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView) TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}