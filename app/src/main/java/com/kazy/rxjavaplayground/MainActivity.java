package com.kazy.rxjavaplayground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final Long MAX_POLLING_COUNT = 10L;

    private PublishSubject<Long> clickEvent = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(it -> {
            clickEvent.onNext(1L);
        });
//        Observable
//                .timer(5, TimeUnit.SECONDS)
//                .flatMap(it ->
//                        Observable.merge(Observable.interval(10, TimeUnit.SECONDS).map(id -> id + 1), clickEvent)
//                                .takeUntil(times -> times >= MAX_POLLING_COUNT)
//                                .flatMap(this::fetchStatus)
//                )
//                .subscribe(
//                it -> {
////                    Log.d(TAG, "onSuccess");
//                },
//                throwable -> {
//                    Log.d(TAG, "onError");
//                },
//                () -> {
//                    Log.d(TAG, "onFinished");
//                }
//        );
//        Observable.just(1, 2, 3, 4, 5)
//                .concatMap(it -> {
//                    return Observable.just(it)
//                            .takeUntil(num -> num == 2);
//                })
//        Observable.concat(
//                Observable.interval(1, TimeUnit.SECONDS).take(45).map(id -> id + 1),
//                Observable.interval(5, TimeUnit.SECONDS).take(15).map(id -> id + 1 + 45)
//        )
//        Observable.error(new IllegalStateException("test"))
//                .compose(observable -> {
//                    return observable;
//                })
//        Observable.just(1)
        Observable.error(new IllegalStateException())
                .doOnError(throwable -> Log.d(TAG, "doOnError"))
//                .onErrorResumeNext(throwable -> {
//                    return Observable.just(1);
//                })
                .doOnError(throwable -> Log.d(TAG, "doOnError2"))
                .flatMap(it -> Observable.just(1).doOnError(throwable1 -> Log.d(TAG, "doOnError3")))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        it -> {
                            Log.d(TAG, "onNext" + it);
                        },
                        throwable -> {
                            Log.d(TAG, "onError");
                        },
                        () -> {
                            Log.d(TAG, "onFinished");
                        }
                );
    }

    private Observable<Integer> fetchStatus(long id) {
        return Observable.just(1).doOnNext(it -> Log.d(TAG, "fetched: " + id));
    }
}
