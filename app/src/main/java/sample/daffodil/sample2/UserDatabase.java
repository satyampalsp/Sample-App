package sample.daffodil.sample2;

/**
 * Created by DAFFODIL-29 on 3/12/2018.
 */
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by DAFFODIL-29 on 3/9/2018.
 */
@Database(entities = { User.class }, version = 1,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();



}