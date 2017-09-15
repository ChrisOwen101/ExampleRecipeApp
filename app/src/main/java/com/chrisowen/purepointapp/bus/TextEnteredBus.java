package com.chrisowen.purepointapp.bus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class TextEnteredBus {
    private static PublishSubject<String> subject = PublishSubject.create();

    public static void setSearch(String search) {
        subject.onNext(search);
    }

    public static Observable<String> getSearch() {
        return subject;
    }
}
