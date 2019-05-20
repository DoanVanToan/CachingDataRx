package com.example.rxjava.data

import io.reactivex.Observable

interface DataSource {
    fun getData(): Observable<String>
}
