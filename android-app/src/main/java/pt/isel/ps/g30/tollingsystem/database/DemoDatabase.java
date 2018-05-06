package pt.isel.ps.g30.tollingsystem.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import pt.isel.ps.g30.tollingsystem.database.model.User;
import pt.isel.ps.g30.tollingsystem.database.model.UserDao;

@Database(entities = {User.class}, version = DemoDatabase.VERSION)
public abstract class DemoDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract UserDao UserDao();

}
