package com.example.rxjava.data.memory

import android.util.Log
import com.example.rxjava.data.DataSource
import io.reactivex.Observable

class MemoryDataSource : DataSource {
    private var data: String? = null

    override fun getData(): Observable<String> = Observable.create<String> { emitter ->
        if (data != null) {
            emitter.onNext(data!!)
        }
        emitter.onComplete()
    }
        .doOnNext {
            Log.d("DATA SOURCE", "Load from Memory")
        }

    fun saveData(data: String) {
        this.data = data
    }
}
