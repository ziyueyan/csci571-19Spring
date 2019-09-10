package com.example.yanziyue.productsearch;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = Item.class, version = 1, exportSchema = false)
public abstract class WishDatabase extends RoomDatabase {

    private static WishDatabase instance;

    public abstract WishDao wishDao();

    public static synchronized WishDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WishDatabase.class, "wish_database")
                    .fallbackToDestructiveMigration()
                    //.addCallback(roomCallback) after database created it would run this call back to initialize database data
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private WishDao wishDao;

        private PopulateDbAsyncTask(WishDatabase db){
            wishDao = db.wishDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            //wishDao.insert(new Item());
            return null;
        }
    }
}
