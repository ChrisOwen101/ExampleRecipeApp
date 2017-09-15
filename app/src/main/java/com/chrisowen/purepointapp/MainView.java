package com.chrisowen.purepointapp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.chrisowen.purepointapp.bus.TextEnteredBus;
import com.chrisowen.purepointapp.pojo.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class MainView {

    @BindView(R.id.searchView) EditText searchView;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progress) ProgressBar progressBar;

    public MainView(MainActivity activity){
        activity.setContentView(R.layout.activity_main);
        ButterKnife.bind(this, activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    public void setRecipes(List<Recipe> recipes){
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(recipes);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @OnTextChanged(R.id.searchView)
    public void onSearchTextChanged(CharSequence s, int start, int before, int count){
        TextEnteredBus.setSearch(s.toString());
    }
}
