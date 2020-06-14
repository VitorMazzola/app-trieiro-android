package br.com.hackccr.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxComposer {

    public static <T> Observable.Transformer<T, T> debounce(int timeout) {
        return observable ->  observable.debounce(timeout, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> debounce() {
        return debounce(400);
    }

    public static <T> Observable.Transformer<T, T> ioThread() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> newThread() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation());
    }

    public static <T> Observable.Transformer<T, T> immediate() {
        return observable -> observable.subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
