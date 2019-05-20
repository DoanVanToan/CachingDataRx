package com.example.rxjava.data;

import com.example.rxjava.data.disk.DiskDataSource;
import com.example.rxjava.data.memory.MemoryDataSource;
import com.example.rxjava.data.network.NetworkDataSource;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class DataRepository implements DataSource {
    private MemoryDataSource memoryDataSource;
    private DiskDataSource diskDataSource;
    private NetworkDataSource networkDataSource;

    public DataRepository(MemoryDataSource memoryDataSource,
                          DiskDataSource diskDataSource,
                          NetworkDataSource networkDataSource) {
        this.networkDataSource = networkDataSource;
        this.diskDataSource = diskDataSource;
        this.memoryDataSource = memoryDataSource;
    }

    @Override
    public Observable<String> getData() {
        Observable<String> memory = getDataFromMemory();
        Observable<String> disk = getDataFromDiskCached();
        Observable<String> network = getDataFromNetwork();
        return Observable
                .concat(memory, disk, network)
                .firstElement()
                .toObservable();
    }

    /**
     * Get data from network and save to memory and disk cached
     *
     * @return
     */
    private Observable<String> getDataFromNetwork() {
        return networkDataSource.getData()
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String data) {
                        diskDataSource.saveData(data);
                        memoryDataSource.saveData(data);
                    }
                });
    }

    /**
     * Get data from disk cached and save to memory
     *
     * @return
     */
    private Observable<String> getDataFromDiskCached() {
        return diskDataSource.getData()
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String data) {
                        memoryDataSource.saveData(data);
                    }
                });
    }

    /**
     * Just get data from memory
     *
     * @return
     */
    private Observable<String> getDataFromMemory() {
        return memoryDataSource.getData();
    }
}
