package com.example.yanziyue.productsearch;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class WishViewModel extends AndroidViewModel {
    private WishRepository repository;
    private LiveData<List<Item>> allWishes;

    //don't store or make reference of context in view model it may cause memory leak
    public WishViewModel(@NonNull Application application) {
        super(application);
        repository = new WishRepository(application);
        allWishes = repository.getAllWishes();
    }

    public void insert(Item item) {
        repository.insert(item);
    }

    public void update(Item item) {
        repository.update(item);
    }

    public void delete(Item item) {
        repository.delete(item);
    }

    public void deleteAllWishes() {
        repository.deleteAllWishes();
    }

    public LiveData<List<Item>> getAllWishes() {
        return allWishes;
    }
}
