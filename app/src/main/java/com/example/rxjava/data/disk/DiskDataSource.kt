package com.example.rxjava.data.disk

import android.content.SharedPreferences
import android.util.Log
import com.example.rxjava.data.DataSource
import io.reactivex.Observable

const val PREF_DATA = "PREF_DATA"

class DiskDataSource(private val preferences: SharedPreferences) : DataSource {

    override fun getData(): Observable<String> = Observable.create<String> { emitter ->
        // You can use database instead of SharePreference
        val data = preferences.getString(PREF_DATA, null)
        if (data != null) {
            emitter.onNext(data)
        }
        emitter.onComplete()
    }
        .doOnNext {
            Log.d("DATA SOURCE", "Load from disk")
        }


    /**
     * Save data when get response from network
     */
    fun saveData(data: String?) {
        if (data != null) {
            preferences.edit().putString(PREF_DATA, data).commit()
        }
    }
}
