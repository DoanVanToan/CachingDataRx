package com.example.rxjava.data.network

import android.util.Log
import com.example.rxjava.data.DataSource
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

const val DATA = "DATA"

class NetworkDataSource : DataSource {
    // try to make a network api
    override fun getData(): Observable<String> =
        Observable.create<String> { emiter ->
            emiter.onNext(DATA)
            emiter.onComplete()
        }
            .delay(5, TimeUnit.SECONDS)
            .doOnNext {
                Log.d("DATA SOURCE", "Load from network")
            }

}
