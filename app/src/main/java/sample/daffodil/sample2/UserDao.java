package sample.daffodil.sample2;

/**
 * Created by DAFFODIL-29 on 3/12/2018.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.provider.SyncStateContract;
import android.widget.Toast;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by DAFFODIL-29 on 3/9/2018.
 */
@Dao
public interface UserDao {
    //checking for entry having email id
    @Query("SELECT * FROM user WHERE email_id LIKE :email")
    List<User> getUserDetails(String email);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
    //Verificatin Query for checking duplicate entry for mobile
    @Query("SELECT * FROM user WHERE mobile LIKE :mob")
    List<User> getPhoneDetails(String mob);
}
