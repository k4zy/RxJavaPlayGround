package com.kazy.rxjavaplayground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
    }
}
