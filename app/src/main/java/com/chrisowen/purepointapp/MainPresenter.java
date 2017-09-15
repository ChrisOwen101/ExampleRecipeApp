package com.chrisowen.purepointapp;

import android.support.annotation.NonNull;

import com.chrisowen.purepointapp.bus.TextEnteredBus;
import com.chrisowen.purepointapp.contracts.MainPresenterContract;
import com.chrisowen.purepointapp.pojo.Recipe;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainPresenter implements MainPresenterContract {

    private MainView mainView;
    private DataSource dataSource;

    public MainPresenter(@NonNull MainView mainView){
        this.mainView = mainView;
        this.dataSource = new DataSource();
        setupSearch();
    }

    private void setupSearch(){
        TextEnteredBus
                .getSearch()
                .filter(s -> !s.isEmpty())
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(s -> {
                    mainView.showProgress();
                    return dataSource.searchRecipe(s);
                })
                .subscribe(new Observer<List<Recipe>>() {
                    @Override public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(List<Recipe> recipes) {
                        mainView.setRecipes(recipes);
                        mainView.hideProgress();
                    }

                    @Override public void onError(Throwable e) {e.printStackTrace();}
                    @Override public void onComplete() {}
                });
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onDestroy() {
        mainView = null;
    }
}
