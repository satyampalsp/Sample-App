package sample.daffodil.sample2.Model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.List;

import sample.daffodil.sample2.Database.User;
import sample.daffodil.sample2.Database.UserDao;
import sample.daffodil.sample2.Database.UserDatabase;

/**
 * Created by DAFFODIL-29 on 4/5/2018.
 */

public class LoginBackgroundImpl implements LoginBackground {
    UserDatabase userdb;

    @Override
    public void SocialLogin(String email, String FirstName, String LastName, String PhoneNo, String pass, OnFinished onFinished, Context context) {
        onFinished.getLoginStatus(socialLogin(email,FirstName,LastName,PhoneNo,pass,context));
    }
    boolean socialLogin(String email, String FirstName, String LastName, String PhoneNo, String Pass, Context context) {
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
        //verification for email doesn't exist
        if (e.size() == 1) {
            return true;
        } else {
            userdb.getUserDao().insert(user);
            List<User> verify_email = userdb.getUserDao().getUserDetails(email.trim());
            if (verify_email.size() == 1) {
                Toast.makeText(context, "Registration Successful. Please Log in to continue!", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                Toast.makeText(context, "Error Occurred. Please try again.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
    boolean login(String username, String password, Context context){
        userdb = Room.databaseBuilder(context,UserDatabase.class, "userDatabase").allowMainThreadQueries().build();
        List<User> u=userdb.getUserDao().getUserDetails(username.trim());
        //Checking for email exists
        if(u.size()==0){
            Toast.makeText(context, "Your Email is not registered with us", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            // password encryption for password verification
            byte[] base64=password.getBytes(StandardCharsets.UTF_8)   ;
            String pass = Base64.encodeToString(base64, Base64.DEFAULT);
            //Successfull Login
            if (u.get(0).email.equals(username) && u.get(0).password.equals(pass)) {
                SharedPreferences sp=context.getSharedPreferences("Mypref",0);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("email",username.trim());
                editor.putString("pass",pass);
                editor.putBoolean("flag",true);
                editor.apply();
                Toast.makeText(context,sp.getString("pass",null),Toast.LENGTH_LONG).show();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void LoginPerform(String username, String password, OnFinished onFinished, Context context) {
        onFinished.getLoginStatus(login(username,password,context));
    }
}
