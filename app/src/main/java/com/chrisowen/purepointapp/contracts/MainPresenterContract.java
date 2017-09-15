package com.chrisowen.purepointapp.contracts;

public interface MainPresenterContract {

    void onResume();

    void onItemClicked(int position);

    void onDestroy();
}
