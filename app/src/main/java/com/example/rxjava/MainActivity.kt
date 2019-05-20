package com.example.rxjava

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rxjava.data.DataRepository
import com.example.rxjava.data.disk.DiskDataSource
import com.example.rxjava.data.memory.MemoryDataSource
import com.example.rxjava.data.network.NetworkDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

const val DEFAULT_PREF = "DEFAULT_PREF"

class MainActivity : AppCompatActivity() {
    private lateinit var repository: DataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val memoryDataSource = MemoryDataSource()
        val diskDataSource = DiskDataSource(getSharedPreferences(DEFAULT_PREF, Context.MODE_PRIVATE));
        val networkDataSource = NetworkDataSource()
        repository = DataRepository(memoryDataSource, diskDataSource, networkDataSource)

        button.setOnClickListener({
            repository
                .getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }, {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                })
        })
    }
}
