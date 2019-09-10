package com.example.yanziyue.productsearch;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.os.AsyncTask;

import java.util.List;

public class WishRepository {

    private WishDao wishDao;
    private LiveData<List<Item>> allWishes;

    public WishRepository(Application application) {
        WishDatabase database = WishDatabase.getInstance(application);
        wishDao = database.wishDao();
        allWishes = wishDao.getAllWish();
    }

    public void insert(Item item){
        new InsertWishAsyncTask(wishDao).execute(item);
    }

    public void update(Item item){
        new UpdateWishAsyncTask(wishDao).execute(item);
    }

    public void delete(Item item) {
        new DeleteWishAsyncTask(wishDao).execute(item);
    }

    public void deleteAllWishes(){
        new DeleteAllWishAsyncTask(wishDao).execute();
    }

    public LiveData<List<Item>> getAllWishes(){
        return allWishes;
    }

    //static: not have the reference to the Repository class, otherwise it may cause memory leak
    private static class InsertWishAsyncTask extends AsyncTask<Item, Void, Void>{

        private WishDao wishDao;

        private InsertWishAsyncTask(WishDao wishDao){
            this.wishDao = wishDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            wishDao.insert(items[0]);
            return null;
        }
    }

    private static class UpdateWishAsyncTask extends AsyncTask<Item, Void, Void>{

        private WishDao wishDao;

        private UpdateWishAsyncTask(WishDao wishDao){
            this.wishDao = wishDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            wishDao.update(items[0]);
            return null;
        }
    }

    private static class DeleteWishAsyncTask extends AsyncTask<Item, Void, Void>{

        private WishDao wishDao;

        private DeleteWishAsyncTask(WishDao wishDao){
            this.wishDao = wishDao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            wishDao.delete(items[0]);
            return null;
        }
    }

    private static class DeleteAllWishAsyncTask extends AsyncTask<Void, Void, Void>{

        private WishDao wishDao;

        private DeleteAllWishAsyncTask(WishDao wishDao){
            this.wishDao = wishDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wishDao.deleteAllWish();
            return null;
        }
    }
}
