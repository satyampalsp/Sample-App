package sample.daffodil.sample2.Model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.List;

import sample.daffodil.sample2.Database.User;
import sample.daffodil.sample2.Database.UserDao;
import sample.daffodil.sample2.Database.UserDatabase;

/**
 * Created by DAFFODIL-29 on 4/6/2018.
 */

public class SignUpBackgroundImpl implements SignUpBackground {
    @Override
    public void SignUp(String email, String FirstName, String LastName, String PhoneNo, String pass, OnFinished onFinished, Context context) {
        onFinished.getStatus(performSignup(email,FirstName,LastName,PhoneNo,pass,context));
    }

    boolean performSignup(String email, String FirstName, String LastName, String PhoneNo, String Pass, Context context) {
        User user;
        UserDatabase userdb;
        UserDao userDao;
        userdb = Room.databaseBuilder(context,
                UserDatabase.class, "userDatabase").allowMainThreadQueries().build();

        //encryption for password
        byte[] base64 = Pass.getBytes(StandardCharsets.UTF_8);
        String pass = Base64.encodeToString(base64, Base64.DEFAULT);
        user = new User(email, FirstName, LastName, PhoneNo, pass);
        List<User> e = userdb.getUserDao().getUserDetails(email.trim());
        List<User> m = userdb.getUserDao().getPhoneDetails(PhoneNo.trim());
        //verification for mobile no doesn't exist
        if (m.size() == 1) {
            Toast.makeText(context, "Phone no already exist", Toast.LENGTH_SHORT).show();
            return false;
        }
        //verification for email doesn't exist
        if (e.size() == 1) {
            Toast.makeText(context, "Email already exist", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            userdb.getUserDao().insert(user);
            List<User> verify_email = userdb.getUserDao().getUserDetails(email.trim());
            if (verify_email.size() == 1) {
                Toast.makeText(context, "Registration Successfull. Please Log in to continue!", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                Toast.makeText(context, "Error Occurred. Please try again.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
}